package mantenimiento.gestorTareas.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.ActivoDao;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.servicio.ActivoService;
import mantenimiento.gestorTareas.servicio.TareaService;
import mantenimiento.gestorTareas.servicio.Servicio;
import mantenimiento.gestorTareas.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class Controlador {
    
    @Autowired
    Servicio servicio;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    RolDao rolDao;
    @Autowired
    ActivoDao activo;
    @Autowired
    ActivoService activoService;
     @Autowired
    TareaService tareaService;
    
    @GetMapping("/")
    public String inicio(Model model) {
        
        
        return layout(model);
    }
    
    @GetMapping("/tareas")
    public String tareas(Model model) {
        var tareas = tareaService.traerNoCerradas();
        model.addAttribute("tareas", tareas);

        
        return "tareas";
    }
    
    @GetMapping("/layout")
    public String layout(Model model) {
        
        model.addAttribute("fallaAplicadoresDeAdhesivo", activoService.findByName("Aplicadores de adhesivo").getEstado().equals("detenida"));
        model.addAttribute("fallaAspiracion", activoService.findByName("Aspiración").getEstado().equals("detenida"));
        model.addAttribute("fallaCambioDeFormato", activoService.findByName("Cambio de formato").getEstado().equals("detenida"));
        model.addAttribute("fallaBandasDeTransporte", activoService.findByName("Bandas de transporte").getEstado().equals("detenida"));
        model.addAttribute("fallaCompactador", activoService.findByName("Compactador").getEstado().equals("detenida"));
        model.addAttribute("fallaCorte", activoService.findByName("Corte").getEstado().equals("detenida"));
        model.addAttribute("fallaMolino", activoService.findByName("Molino").getEstado().equals("detenida"));
        
        
        return "layoutImaginado";
    }
    
    
   
    @PostMapping("/filtrar")
    public String filtro(Model model, @Param("palabraClave")String palabraClave){
        var tareas = servicio.filtrar(palabraClave);
        model.addAttribute("tareas", tareas);
        model.addAttribute("palabraClave", palabraClave);
        
        
        return "tareas";
    }
    
    @GetMapping("/crearTarea")
    public String modificar(Model model,Tarea tarea) {
        model.addAttribute("activos",activo.findAll());
        model.addAttribute("estados",Arrays.asList("detenida","operativa","disponible para preventivo"));
        
        return "crearTarea";
    }
    
    @PostMapping("/guardar")
    public String guardar(Model model,@Valid Tarea tarea, Errors errores, @RequestParam("file") MultipartFile imagen) {
        
        if (errores.hasErrors()) {
            log.info("hay errores" + errores.getAllErrors());
            return "crearTarea";
        }
        if (!imagen.isEmpty()) {
            // Path directorioImagenes = Paths.get("src//main//resources//static//imagenes");
            // String ruta = directorioImagenes.toFile().getAbsolutePath();
            //voy a usar un directorio no relativo para evitar la necesidad de actualizar
            //cada vez que se agrega una imagen nueva
            String ruta = "c://AppTareas//recursos";
            try {
                byte[] bytes = imagen.getBytes();
                Path rutaCompleta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                log.info("ruta: "+rutaCompleta);
                Files.write(rutaCompleta, bytes);
                tarea.setImagen(imagen.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        tarea.setSolicita(aut.getName());
        //al solicitar la tarea pasa a estado abierto automaticamente
        tarea.setEstado("abierto");
        //se guarda el momento de la solicitud para calcular el tiempo de parada
        tarea.getActivo().setMomentoDetencion(LocalDateTime.now());
        tarea.setMomentoDetencion(LocalDateTime.now());
        
        
        
        servicio.guardar(tarea);
         model.addAttribute("tareas",tareaService.traerNoCerradas());
        return "tareas";
    }
    
    @GetMapping("/editar/{idTarea}")
    public String editar(Tarea tarea, Model model) {
        model.addAttribute("activos",activo.findAll());
        model.addAttribute("estados",Arrays.asList("detenida","operativa","disponible para preventivo"));
        model.addAttribute("tarea", servicio.encontrar(tarea));
        return "modificar";
    }
    
    @PostMapping("/guardarEdicion")
    public String guardarEdicion(Model model,@Valid Tarea tarea, Errors errores) {
        log.info("tarea: "+tarea);
        if (errores.hasErrors()) {
            log.info("error de validacion!!"+errores.getAllErrors());
            return "modificar";
        }
        
        servicio.guardar(tarea);
        
         model.addAttribute("tareas", tareaService.traerNoCerradas());
        
        return "tareas";
    }
    
    @GetMapping("/eliminar/{idTarea}")
    public String eliminar(Model model,Tarea tarea) {
        
        servicio.eliminar(tarea);
          model.addAttribute("tareas", tareaService.traerNoCerradas());
        return "tareas";
    }
    
    @GetMapping("/liberarSolicitud/{idTarea}")
    public String liberar(Model model,Tarea tarea) {
        Tarea t= servicio.encontrar(tarea);
        t.setEstado("liberada");
        t.setMomentoLiberacion(LocalDateTime.now());
        servicio.guardar(t);
          model.addAttribute("tareas", tareaService.traerNoCerradas());
        return "tareas";
    }
    @GetMapping("/CerrarSolicitud/{idTarea}")
    public String CerrarSolicitud(@Param("evaluacion")String evaluacion, Model model, Tarea tarea) {
        log.info("evaluacion: "+evaluacion+" id tarea: "+tarea.getIdTarea());
        Tarea t= servicio.encontrar(tarea);
        t.setEstado("cerrada");
        t.getActivo().setEstado("operativa");
         t.setMomentoCierre(LocalDateTime.now());
//        t.getActivo().setMomentoDetencion(null);
        servicio.guardar(t);
        
          model.addAttribute("tareas",tareaService.traerNoCerradas());
        return "redirect:/tareas";
    }
    
    
    @GetMapping("/generar/{idTarea}")
    public String generar(Tarea tarea, Model model) {
        model.addAttribute("tarea", servicio.encontrar(tarea));
        return "ot";
    }
    
    @GetMapping("/registro")
    public String registroHistorico( Model model) {
        
        List<Tarea> tareas=tareaService.traerCerradas();
        
        
        
        
        
        model.addAttribute("tareas", tareaService.traerCerradas());
        return "registro";
    }
    
//    
//    // Método para calcular la diferencia de tiempo entre dos LocalDateTime
//    public String calcularDiferenciaTiempo(LocalDateTime momentoDetencion, LocalDateTime momentoLiberacion) {
//        // Calcular la diferencia de tiempo
//        Duration duracion = Duration.between(momentoDetencion, momentoLiberacion);
//
//        // Formatear la duración en un formato legible (días, horas, minutos, segundos)
//        long dias = duracion.toDays();
//        long horas = duracion.toHours() % 24;
//        long minutos = duracion.toMinutes() % 60;
//        long segundos = duracion.getSeconds() % 60;
//
//        // Construir la cadena de texto
//        String diferenciaTiempo = dias + " días, " + horas + " horas, " + minutos + " minutos, " + segundos + " segundos";
//        
//        return diferenciaTiempo;
//    }
//    
}

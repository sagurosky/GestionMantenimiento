package mantenimiento.gestorTareas.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
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
import mantenimiento.gestorTareas.dominio.Tecnico;
import mantenimiento.gestorTareas.dominio.Usuario;
import mantenimiento.gestorTareas.servicio.ActivoService;
import mantenimiento.gestorTareas.servicio.TareaService;
import mantenimiento.gestorTareas.servicio.Servicio;
import mantenimiento.gestorTareas.servicio.TecnicoService;
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
import mantenimiento.gestorTareas.util.Convertidor;
import org.springframework.web.bind.annotation.PathVariable;

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
    @Autowired
    TecnicoService tecnicoService;

    @GetMapping("/")
    public String inicio(Model model) {
        //si el usuario logueado es un técnico y el apellido es null significa que fue recien creado
        //por lo tanto lo redirijo a tecnicoDatosPersonales para que cargue su informacion;
        Usuario usuario=usuarioDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        
        if(usuario.getRoles().get(0).getNombre().equals("ROLE_TECNICO"))
        {
           Tecnico tecnico=tecnicoService.traerPorUsuario(usuario);
           if(tecnico.getApellido()==null)
           {
               model.addAttribute("tecnico",tecnico);
               return "tecnicoDatosPersonales";
           }
        }
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

        //traigo todos los activos y mando a la vista variables de falla cuando estan detenidos o de cierre cuando estan liberadas y faltan cerrar
        List<Activo> activos = activo.findAll();
        String aux = "";
        Boolean fallaPlanta2=false; 
        Boolean cierrePlanta2=false; 
        
        
        for (Activo activo : activos) {
                aux = Convertidor.aCamelCase(activo.getNombre());
                aux = aux.toUpperCase().charAt(0) + aux.substring(1);
                model.addAttribute("falla" + aux, activo.getEstado().equals("detenida"));
                model.addAttribute("cierre" + aux, activo.getEstado().equals("liberada"));
                //detecto si algun activo de polanta2 esta detenido o aguardando cierre
                if((activo.getNombre().contains("adulto 4")||activo.getNombre().contains("adulto 5")||activo.getNombre().contains("planta 2"))&&activo.getEstado().equals("detenida"))fallaPlanta2=true;
                if((activo.getNombre().contains("adulto 4")||activo.getNombre().contains("adulto 5")||activo.getNombre().contains("planta 2"))&&activo.getEstado().equals("liberada"))cierrePlanta2=true;
        }
         model.addAttribute("fallaPlanta2",fallaPlanta2);
         model.addAttribute("cierrePlanta2",cierrePlanta2);
         
         
        return "layoutPlanta3";
    }
    
    
    
    @GetMapping("/layoutPlanta2")
    public String layoutPlanta2(Model model) {

        //traigo todos los activos y mando a la vista variables de falla cuando estan detenidos o de cierre cuando estan liberadas y faltan cerrar
        List<Activo> activos = activo.findAll();
        String aux = "";
        Boolean fallaPlanta3=false; 
        Boolean cierrePlanta3=false; 
        for (Activo activo : activos) {
                aux = Convertidor.aCamelCase(activo.getNombre());
                aux = aux.toUpperCase().charAt(0) + aux.substring(1);
                model.addAttribute("falla" + aux, activo.getEstado().equals("detenida"));
                model.addAttribute("cierre" + aux, activo.getEstado().equals("liberada"));
                  if((activo.getNombre().contains("adulto 3")||activo.getNombre().contains("adulto 2")||activo.getNombre().contains("aposito"))&&activo.getEstado().equals("detenida"))fallaPlanta3=true;
                if((activo.getNombre().contains("adulto 3")||activo.getNombre().contains("adulto 2")||activo.getNombre().contains("aposito"))&&activo.getEstado().equals("liberada"))cierrePlanta3=true;

        }
        model.addAttribute("fallaPlanta3",fallaPlanta3);
        model.addAttribute("cierrePlanta3",cierrePlanta3);

        return "layoutPlanta2";
    }
    

    @PostMapping("/filtrar")
    public String filtro(Model model, @Param("palabraClave") String palabraClave) {
        var tareas = servicio.filtrar(palabraClave);
        model.addAttribute("tareas", tareas);
        model.addAttribute("palabraClave", palabraClave);

        return "redirect:/tareas";
    }

    @GetMapping("/crearTarea/{id}")
    public String modificar(Model model, Tarea tarea,  Activo activoRequest) {
       
        
        Activo activoSeleccionado = activo.findById(activoRequest.getId()).orElse(null); 
        tarea.setActivo(activoSeleccionado);
        model.addAttribute("tarea", tarea);
        model.addAttribute("activos", activo.findAll());
        
//        model.addAttribute("estados",Arrays.asList("detenida","operativa","disponible para preventivo"));
        return "crearTarea";
    }
    
    
    
    @PostMapping("/guardar")
    public String guardar(Model model, @Valid Tarea tarea, Errors errores, @RequestParam("file") MultipartFile imagen) {

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
                log.info("ruta: " + rutaCompleta);
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
        tarea.getActivo().setEstado("detenida");
        activo.save(tarea.getActivo());

        servicio.guardar(tarea);
        model.addAttribute("tareas", tareaService.traerNoCerradas());
        return "redirect:/tareas";
    }

    @GetMapping("/editar/{id}")
    public String editar(Tarea tarea, Model model) {
        model.addAttribute("activos", activo.findAll());
        model.addAttribute("estados", Arrays.asList("detenida", "operativa", "disponible para preventivo"));
        model.addAttribute("tarea", servicio.encontrar(tarea));
        return "modificar";
    }

    @PostMapping("/guardarEdicion")
    public String guardarEdicion(Model model, @Valid Tarea tarea, Errors errores) {
        log.info("tarea: " + tarea);
        if (errores.hasErrors()) {
            log.info("error de validacion!!" + errores.getAllErrors());
            return "modificar";
        }

        servicio.guardar(tarea);

        model.addAttribute("tareas", tareaService.traerNoCerradas());

        return "redirect:/tareas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(Model model, Tarea tarea) {

        servicio.eliminar(tarea);
        model.addAttribute("tareas", tareaService.traerNoCerradas());
        return "redirect:/tareas";
    }

    @GetMapping("/liberarSolicitud/{id}")
    public String liberar(Model model, Tarea tarea) {
        Tarea t = servicio.encontrar(tarea);
        t.setEstado("liberada");
        t.getActivo().setEstado("liberada");
        t.setMomentoLiberacion(LocalDateTime.now());
        servicio.guardar(t);
        model.addAttribute("tareas", tareaService.traerNoCerradas());
        return "redirect:/tareas";
    }

    @GetMapping("/asignarSolicitud/{id}")
    public String asignar(@Param("motivoDemoraAsignacion") String motivoDemoraAsignacion, Model model, Tarea tarea) {
        log.info(motivoDemoraAsignacion + " pepe");
        Tarea t = servicio.encontrar(tarea);
        t.setEstado("enProceso");
        t.setMomentoAsignacion(LocalDateTime.now());
        t.setMotivoDemoraAsignacion(motivoDemoraAsignacion);
        servicio.guardar(t);
        model.addAttribute("tareas", tareaService.traerNoCerradas());
        return "redirect:/tareas";
    }

    @GetMapping("/CerrarSolicitud/{id}")
    public String CerrarSolicitud(@Param("evaluacion") String evaluacion, @Param("motivoDemoraCierre") String motivoDemoraCierre, Model model, Tarea tarea) {
        log.info("evaluacion: " + evaluacion + " id tarea: " + tarea.getId());

        Tarea t = servicio.encontrar(tarea);
        t.getActivo().setEstado("operativa");
        t.setEstado("cerrada");
        t.setMotivoDemoraCierre(motivoDemoraCierre);
        t.setEvaluacion(evaluacion);

        t.setMomentoCierre(LocalDateTime.now());
//        t.getActivo().setMomentoDetencion(null);
        servicio.guardar(t);

        model.addAttribute("tareas", tareaService.traerNoCerradas());
        return "redirect:/tareas";
    }

    

    @GetMapping("/registro/{url}")
    public String registroHistorico(@PathVariable("url") String url, Model model) {


        model.addAttribute("tareas", tareaService.traerCerradas());
        model.addAttribute("url", url);
        
        
        return "registro";
    }

    @GetMapping("/registroActivo/{id}")
    public String registroHistoricoActivo(Model model, Activo activo) {

        List<Tarea> tareas = tareaService.traerCerradas();
        model.addAttribute("url", Convertidor.aCamelCase(activoService.findById(activo.getId()).orElse(null).getNombre()));
        model.addAttribute("tareas", tareaService.traerCerradasPorActivo(activo));

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

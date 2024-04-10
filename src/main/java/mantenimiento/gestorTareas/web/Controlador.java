package mantenimiento.gestorTareas.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Tarea;
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
    
    @GetMapping("/")
    public String inicio(Model model) {
        
        
        return "index";
    }
    
    @GetMapping("/tareas")
    public String tareas(Model model) {
        var tareas = servicio.listar();
        model.addAttribute("tareas", tareas);
        
        return "tareas";
    }
    
    @GetMapping("/layout")
    public String layout(Model model) {
//        var tareas = servicio.listar();
//        model.addAttribute("tareas", tareas);
        
        return "layout";
    }
    
    
    @PostMapping("/")
    public String filtro(Model model, @Param("palabraClave")String palabraClave){
        log.info("palabraClave: "+palabraClave);
        var tareas = servicio.filtrar(palabraClave);
        model.addAttribute("tareas", tareas);
        model.addAttribute("palabraClave", palabraClave);
        
        
        return "index";
    }
    
    @GetMapping("/crearTarea")
    public String modificar(Tarea tarea) {
        return "crearTarea";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid Tarea tarea, Errors errores, @RequestParam("file") MultipartFile imagen) {
        
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
        log.info("img: "+imagen.getOriginalFilename());
        servicio.guardar(tarea);
        return "redirect:/";
    }
    
    @GetMapping("/editar/{idTarea}")
    public String editar(Tarea tarea, Model model) {
        
        model.addAttribute("tarea", servicio.encontrar(tarea));
        return "modificar";
    }
    
    @PostMapping("/guardarEdicion")
    public String guardarEdicion(@Valid Tarea tarea, Errors errores) {
        log.info("tarea: "+tarea);
        if (errores.hasErrors()) {
            log.info("error de validacion!!"+errores.getAllErrors());
            return "modificar";
        }
        
        servicio.guardar(tarea);
        return "redirect:/";
    }
    
    @GetMapping("/eliminar/{idTarea}")
    public String eliminar(Tarea tarea) {
        
        servicio.eliminar(tarea);
        return "redirect:/";
    }
    
    @GetMapping("/generar/{idTarea}")
    public String generar(Tarea tarea, Model model) {
        model.addAttribute("tarea", servicio.encontrar(tarea));
        return "ot";
    }
}

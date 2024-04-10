package mantenimiento.gestorTareas.web.equipos;

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
public class ControladorEquipos {
    
    @Autowired
    Servicio servicio;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    RolDao rolDao;
    
    
    
    
    @GetMapping("/torreAguaFria")
    public String torreAguaFria(Model model) {
//        var tareas = servicio.listar();
//        model.addAttribute("tareas", tareas);
        
        return "equipos/torreAguaFria";
    }
    @GetMapping("/zonaReparacion")
    public String zonaReparacion(Model model) {
//        var tareas = servicio.listar();
//        model.addAttribute("tareas", tareas);
        
        return "equipos/zonaReparacion";
    }
    
}

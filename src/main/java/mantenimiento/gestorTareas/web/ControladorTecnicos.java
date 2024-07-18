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
public class ControladorTecnicos {

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

    

    @GetMapping("/perfil")
    public String perfil(Model model) {
//        var tareas = tareaService.traerNoCerradas();
//        model.addAttribute("tareas", tareas);

        return "perfilTecnico";
    }

    
    @PostMapping("/guardarTecnicoEmpresa")
    public String guardarTecnicoEmpresa(Model model, Tecnico tecnico) {
        
        tecnicoService.save(tecnico);
        return "redirect:/gestionUsuarios";
    }
    @PostMapping("/guardarTecnicoPersonal")
    public String guardarTecnicoPersonal(Model model, Tecnico tecnico) {
        //tenfo que levantar de nuevo el usuario porque no lo podia poner como hidden en el html, daba error de recirculacion
        Usuario usuario=usuarioDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        
        tecnico.setUsuario(usuario);
        tecnicoService.save(tecnico);
        return "redirect:/layout";
    }
    
}

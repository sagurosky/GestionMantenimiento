package mantenimiento.gestorTareas.web;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.ActivoDao;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Informe;
import mantenimiento.gestorTareas.dominio.Preventivo;
import mantenimiento.gestorTareas.dominio.Produccion;
import mantenimiento.gestorTareas.dominio.Producto;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.dominio.Tecnico;
import mantenimiento.gestorTareas.dominio.Usuario;
import mantenimiento.gestorTareas.servicio.ActivoService;
import mantenimiento.gestorTareas.servicio.AsignacionService;
import mantenimiento.gestorTareas.servicio.ProduccionService;
import mantenimiento.gestorTareas.servicio.ProductoService;
import mantenimiento.gestorTareas.servicio.TareaService;
import mantenimiento.gestorTareas.servicio.Servicio;
import mantenimiento.gestorTareas.servicio.TecnicoService;
import mantenimiento.gestorTareas.servicio.UsuarioService;
import mantenimiento.gestorTareas.util.Convertidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ControladorInformes {

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
    @Autowired
    AsignacionService asignacionService;
    @Autowired
    ProduccionService produccionService;
    @Autowired
    ProductoService productoService;

    
    @GetMapping("/informes/{url}")
    public String informes(Model model,@PathVariable("url") String url)
    {
            Usuario usuario = usuarioDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (usuario.getRoles().get(0).getNombre().equals("ROLE_TECNICO")) {
            Tecnico tecnico = tecnicoService.traerPorUsuario(usuario);
            List<Tarea> tareas=tareaService.traerPorInformePendiente(tecnico);
        model.addAttribute("tareas",tareas);
         model.addAttribute("url",url);
            for (Tarea tarea : tareas) {
            }
        }else
        {
            model.addAttribute("tareas",null);
        }
        return "/informes";
    } 
    @GetMapping("/generarInforme/{id}")
    public String generarInforme(Model model,@PathVariable("id") Long id,@RequestParam("url") String url)
    {
           Tarea tareaBD =tareaService.findById(id).orElse(null);
           model.addAttribute("tarea",tareaBD);
           model.addAttribute("url",url);
        return "/informe";
    } 
    
    
    
}

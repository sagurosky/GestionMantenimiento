package mantenimiento.gestorTareas.web;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.ActivoDao;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Informe;
import mantenimiento.gestorTareas.dominio.Preventivo;
import mantenimiento.gestorTareas.dominio.Produccion;
import mantenimiento.gestorTareas.dominio.Producto;
import mantenimiento.gestorTareas.dominio.Rol;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.dominio.Tecnico;
import mantenimiento.gestorTareas.dominio.Usuario;
import mantenimiento.gestorTareas.servicio.ActivoService;
import mantenimiento.gestorTareas.servicio.AsignacionService;
import mantenimiento.gestorTareas.servicio.InformeService;
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
    @Autowired
    InformeService informeService;

    
    @GetMapping("/informes/{url}")
    public String informes(Model model,@PathVariable("url") String url)
    {
            Usuario usuario = usuarioDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            String rol="";
            for (Rol role : usuario.getRoles()) {
            
                if (role.getNombre().equals("ROLE_TECNICO"))rol="tecnico";
                if (role.getNombre().equals("ROLE_MANT"))rol="mant";
                if (role.getNombre().equals("ROLE_PROD"))rol="prod";
                if (role.getNombre().equals("ROLE_MONITOR"))rol="monitor";
                if (role.getNombre().equals("ROLE_ADMIN"))rol="admin";
        }
            
            
            //al cerrarse la intervencion se genera el objeto informe con estado noEvaluado,
            //primero debe seleccionarse la intervencion que merezca tener un informe ligado, esto lo puede hacer el técnico o el supervisor, el técnico no puede descartarla
            //al ser seleccionada la intervencion su informe pasa a tener estado "pendiente" y se mostrará en el listado para generar informes,
            //al terminar el informe pasa a estado "noAprobado" para que el supervisor de el visto bueno y lo valide
            //al al validar el informe pasa a estado aprobado y pasa a formar parte de los registros para consulta
      
            if (rol.equals("tecnico"))
        {
            
            Tecnico tecnico = tecnicoService.traerPorUsuario(usuario);
            List<Tarea> tareasNoEvaluadas=tareaService.traerPorTecnicoYEstadoInforme(tecnico,"noEvaluado");
            model.addAttribute("tareasNoEvaluadas",tareasNoEvaluadas);
             List<Tarea> tareasInformePendienteTecnico=tareaService.traerPorTecnicoYEstadoInforme(tecnico,"pendiente");
            model.addAttribute("tareasInformePendienteTecnico",tareasInformePendienteTecnico);
         
//            for (Tarea tareasNoEvaluada : tareasNoEvaluadas) {
//                log.info("############ "+tareasNoEvaluada.getId());
//            }
            
        }
        else 
        if (rol.equals("mant")||rol.equals("admin"))
        {
//            List<Tarea> tareasNoEvaluadas=tareaService.traerPorEstadoInforme("noEvaluado");
//            model.addAttribute("tareasNoEvaluadas",tareasNoEvaluadas);
//            List<Tarea> tareasNoValidadas=tareaService.traerPorEstadoInforme("noValidado");
//            model.addAttribute("tareasNoValidadas",tareasNoValidadas);
            List<Tarea> tareasNoEvaluadas=tareaService.traerPorEstadoInforme("noEvaluado");
            model.addAttribute("tareasNoEvaluadas",tareasNoEvaluadas);
            List<Tarea> tareasNoAprobadas=tareaService.traerPorEstadoInforme("noAprobado");
            model.addAttribute("tareasNoAprobadas",tareasNoAprobadas);
            List<Tarea> tareasAprobados=tareaService.traerPorEstadoInforme("aprobado");
            model.addAttribute("tareasAprobados",tareasAprobados);
            List<Tarea> tareasInformePendiente=tareaService.traerPorEstadoInforme("pendiente");
            model.addAttribute("tareasInformePendiente",tareasInformePendiente);
        }
       
        else
        {
          List<Tarea> tareasAprobados=tareaService.traerPorEstadoInforme("aprobado");
            model.addAttribute("tareasAprobados",tareasAprobados);
        }
        
         model.addAttribute("url",url);
         model.addAttribute("todosLosTecnicos",tecnicoService.findAll());
        return "/informes";
    } 
    @GetMapping("/generarInforme/{id}")
    public String generarInforme(Model model,@PathVariable("id") Long id,@RequestParam("url") String url)
    {
           Tarea tareaBD =tareaService.findById(id).orElse(null);
           model.addAttribute("tarea",tareaBD);
           model.addAttribute("todosLosTecnicos",tecnicoService.findAll());
           
           model.addAttribute("url",url);
           
           // Calcular la diferencia
        Duration duracion = Duration.between(tareaBD.getMomentoDetencion(), tareaBD.getMomentoLiberacion());

        // Formatear la diferencia en horas, minutos y segundos
        String diferenciaFormateada = String.format("%02d:%02d:%02d",
            duracion.toHours(),
            duracion.toMinutesPart(),
            duracion.toSecondsPart()
        );
           model.addAttribute("tiempoDetenido",diferenciaFormateada);
           
           
        return "/informe";
    } 
    @GetMapping("/seleccionarTareaParaInforme/{id}")
    public String seleccionarTareaParaInforme(Model model,@PathVariable("id") Long id,@RequestParam("url") String url)
    {
           Tarea tareaBD =tareaService.findById(id).orElse(null);
           tareaBD.getInforme().setEstadoInforme("pendiente");
           informeService.save(tareaBD.getInforme());
           
           model.addAttribute("tarea",tareaBD);
           model.addAttribute("url",url);
        return informes(model,url);
    } 
    @GetMapping("/descartarTareaParaInforme/{id}")
    public String descartarTareaParaInforme(Model model,@PathVariable("id") Long id,@RequestParam("url") String url)
    {
           Tarea tareaBD =tareaService.findById(id).orElse(null);
           tareaBD.getInforme().setEstadoInforme("descartado");
           informeService.save(tareaBD.getInforme());
           
           model.addAttribute("tarea",tareaBD);
           model.addAttribute("url",url);
        return informes(model,url);
    } 
    
    
    
}

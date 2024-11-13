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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.ActivoDao;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Asignacion;
import mantenimiento.gestorTareas.dominio.Evaluacion;
import mantenimiento.gestorTareas.dominio.Preventivo;
import mantenimiento.gestorTareas.dominio.Produccion;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.dominio.Tecnico;
import mantenimiento.gestorTareas.dominio.Usuario;
import mantenimiento.gestorTareas.servicio.ActivoService;
import mantenimiento.gestorTareas.servicio.AsignacionService;
import mantenimiento.gestorTareas.servicio.PreventivoService;
import mantenimiento.gestorTareas.servicio.ProduccionService;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ControladorAjax {

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
    PreventivoService preventivoService;
    
    
    
    @GetMapping("/traerPreventivosAlLayout")
    @ResponseBody//con esta anotacion springboot no va a intentar abrir un htrml con el nombre de lo que pongo en return
    public Object traerPreventivosAlLayout(@RequestParam String nombre,@RequestParam String estado) {
        
        return preventivoService.traerActivosValidadosPorNombreActivo(Convertidor.deCamelCase(nombre));
    }
    
    
    
    @GetMapping("/actualizarEstados")
    @ResponseBody//con esta anotacion springboot no va a intentar abrir un htrml con el nombre de lo que pongo en return
    public Object actualizarEstados() {
         //traigo todos los activos y mando a la vista variables de falla cuando estan detenidos o de cierre cuando estan liberadas y faltan cerrar
        List<Activo> activos = activo.findAll();
        String aux = "";
        String fallaPlanta2 = "";
        String cierrePlanta2 = "";
        
        Map<String,String> model=new HashMap<>(); 
        
        for (Activo activo : activos) {
            aux = Convertidor.aCamelCase(activo.getNombre());
//            aux = aux.toUpperCase().charAt(0) + aux.substring(1);
            
//le paso la variable disponible si la hora cargada de la disponibilidad es mayor a la hora actual  
            if(activo.getDisponibilidadHasta()!=null&&activo.getEstado().equals("disponible"))
            if(LocalDateTime.now().isBefore(activo.getDisponibilidadHasta()))
            {
                model.put("tiempo" + aux, activo.getDisponibilidadHasta().toString());
            }else
            {
                activo.setEstado("operativa");
                activoService.save(activo);
            }

            model.put(aux, activo.getEstado());
            
            
            //detecto si algun activo de polanta2 esta detenido o aguardando cierre
            if ((activo.getNombre().contains("adulto 4") || activo.getNombre().contains("adulto 5") || activo.getNombre().contains("planta 2")) && activo.getEstado().equals("detenida")) {
                fallaPlanta2 = "true";
            }
            if ((activo.getNombre().contains("adulto 4") || activo.getNombre().contains("adulto 5") || activo.getNombre().contains("planta 2")) && activo.getEstado().equals("liberada")) {
                cierrePlanta2 = "true";
            }
        }
        model.put("fallaPlanta2", fallaPlanta2);
        model.put("cierrePlanta2", cierrePlanta2);
        return model;
    }
    
    
    @GetMapping("/traerDatos/{id}")
    @ResponseBody//con esta anotacion springboot no va a intentar abrir un htrml con el nombre de lo que pongo en return
    public Object traerDatos(Produccion produccion) {
        Map<String,Object> datos=new HashMap<>();
        
        Produccion prod=produccionService.findById(produccion.getId()).orElse(null);
        
        datos.put("prod", prod);
        
        log.info("linea: "+Convertidor.deCamelCase(prod.getLinea()));
        log.info("inicio: "+ prod.getInicio().toString());
        log.info("fin: "+prod.getFin().toString());
        
        List<Tarea> tareasEnRango=new ArrayList<>();
        tareasEnRango=tareaService.traerPorLineaEnRangoDeFecha(Convertidor.deCamelCase(prod.getLinea()), prod.getInicio().toString(), prod.getFin().toString());
        
        
        log.info("tamaño: "+tareasEnRango.size());
        
        Long minutosInactiva=0L;
            for (Tarea tarea : tareasEnRango) {
                minutosInactiva+=Duration.between(tarea.getMomentoDetencion(), (tarea.getMomentoLiberacion().isAfter(prod.getFin()))?prod.getFin():tarea.getMomentoLiberacion()).toMinutes();
                log.info("minutos : "+minutosInactiva);
           
            }
        
        
        datos.put("minutosInactiva",minutosInactiva);
        
//        
//        for (Tarea tarea : tareasEnRango) {
//          log.info("########## "+tarea.getId());  
//        }
            
        
        
        
        return datos;
    }
    
    
}

package mantenimiento.gestorTareas.web;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.ActivoDao;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Preventivo;
import mantenimiento.gestorTareas.dominio.Produccion;
import mantenimiento.gestorTareas.servicio.ActivoService;
import mantenimiento.gestorTareas.servicio.AsignacionService;
import mantenimiento.gestorTareas.servicio.ProduccionService;
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
public class ControladorProduccion {

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

    @GetMapping("/produccion/{url}")
    public String cargaProduccion(@PathVariable("url") String url, Model model) {
        model.addAttribute("produccion", new Produccion());
        List<String> productos = Arrays.asList(Produccion.PRODUCTO_1,
                                                      Produccion.PRODUCTO_2,
                                                      Produccion.PRODUCTO_3,
                                                      Produccion.PRODUCTO_4);
        model.addAttribute("productos",productos );
//        log.info("Productos: " + productos);
        List<String> lineas=Arrays.asList(Produccion.LINEA_1,
                                                  Produccion.LINEA_2,
                                                  Produccion.LINEA_3,
                                                  Produccion.LINEA_4,
                                                  Produccion.LINEA_5);
        model.addAttribute("lineas",lineas);
//        log.info("lineas: " + lineas);
        model.addAttribute("ordenesAbiertas", produccionService.traerAbiertas());
        model.addAttribute("ordenesCerradas", produccionService.traerCerradas());
        
        
        model.addAttribute("url", url);
        return "produccion";
    }
    
    
     @PostMapping("/cargaOrdenDeTrabajo")
    public String cargaOrdenDeTrabajo(@RequestParam("url")String url, Model model,  Produccion produccion) {
        
        produccion.setEstado("abierta");
        produccion.setInicio(LocalDateTime.now());
        produccionService.save(produccion);
         model.addAttribute("ordenesAbiertas", produccionService.traerAbiertas());
        model.addAttribute("url",url);
        return "produccion";
    }
    
     @GetMapping("/cerrarOrdenDeTrabajo/{id}")
    public String cerrarOrdenDeTrabajo(  @PathVariable("id") Long id,@RequestParam("url")String url, Model model,  Produccion produccion) {
        
        Produccion prod=produccionService.findById(produccion.getId()).orElse(null);
        prod.setFin(LocalDateTime.now());
        prod.setEstado("cerrada");
        produccionService.save(prod);
         model.addAttribute("ordenesAbiertas", produccionService.traerAbiertas());
         
//        log.info("id: "+produccion.getId());
        
        model.addAttribute("url",url);
        return "produccion";
    }
    
     @GetMapping("/historialOrdenes/{url}")
    public String cerrarOrdenDeTrabajo(  @PathVariable("url") String url, Model model) {
        
         model.addAttribute("ordenesCerradas", produccionService.traerCerradas());
        
        
//        produccionService.save(produccion);
        model.addAttribute("url",url);
        return "historialProduccion";
    }
     @GetMapping("/eliminarOrden/{id}")
    public String eliminarOrden(  @PathVariable("id") Long id,@RequestParam("url")String url, Model model ) {
        model.addAttribute("produccion", new Produccion());
        Produccion prod=produccionService.findById(id).orElse(null);
        produccionService.delete(prod);
         model.addAttribute("ordenesAbiertas", produccionService.traerAbiertas());
        
        
//        produccionService.save(produccion);
        model.addAttribute("url",url);
        return "produccion";
    }
    
    

}

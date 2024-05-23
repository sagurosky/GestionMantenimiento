package mantenimiento.gestorTareas.web.equipos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.servicio.ActivoService;
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
    @Autowired
    ActivoService activoService;
    
    
    
//    @GetMapping("/torreAguaFria")
//    public String torreAguaFria(Model model) {
//        Activo activo=activoService.findByName("torre agua fria");
//        model.addAttribute("activo",activo);
//        model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
//        return "equipos/torreAguaFria";
//    }
//    
//    
//    @GetMapping("/reparaciones")
//    public String zonaReparacion(Model model) {
//        Activo activo=activoService.findByName("reparaciones");
//         model.addAttribute("activo",activo);
//          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
//        return "equipos/torreAguaFria";
//    }
//    
//    @GetMapping("/linea1")
//    public String linea1(Model model) {
//       Activo activo=activoService.findByName("linea 1");
//         model.addAttribute("activo",activo);
//          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
//        return "equipos/torreAguaFria";
//    }
    
     @GetMapping("/aplicadoresDeAdhesivo")
    public String aplicadoresDeAdhesivo(Model model) {
       Activo activo=activoService.findByName("aplicadores de adhesivo");
         model.addAttribute("activo",activo);
         
         List<Tarea> tareas=servicio.listar();
         Integer cantidadMecanicas=0;
         Integer cantidadElectronicas=0;
         Integer cantidadNeumaticas=0;
         Integer cantidadHidraulicas=0;
         Integer cantidadProgramacion=0;
        
         for (Tarea tarea : tareas) {
            if(tarea.getCategoriaTecnica().equals("mecánica"))cantidadMecanicas++;
            if(tarea.getCategoriaTecnica().equals("hidráulica"))cantidadHidraulicas++;
            if(tarea.getCategoriaTecnica().equals("neumática"))cantidadNeumaticas++;
            if(tarea.getCategoriaTecnica().equals("electrónica"))cantidadElectronicas++;
            if(tarea.getCategoriaTecnica().equals("programación"))cantidadProgramacion++;
         }
         model.addAttribute("cantidadMecanicas",cantidadMecanicas);
         model.addAttribute("cantidadHidraulicas",cantidadHidraulicas);
         model.addAttribute("cantidadNeumaticas",cantidadNeumaticas);
         model.addAttribute("cantidadElectronicas",cantidadElectronicas);
         model.addAttribute("cantidadProgramacion",cantidadProgramacion);
         
         model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
        return "equipos/activo";
    }
     @GetMapping("/aspiracion")
    public String aspiracion(Model model) {
       Activo activo=activoService.findByName("aspiracion");
         model.addAttribute("activo",activo);
          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
        return "equipos/activo";
    }
     @GetMapping("/cambioDeFormato")
    public String cambioDeFormato(Model model) {
       Activo activo=activoService.findByName("cambio de formato");
         model.addAttribute("activo",activo);
          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
        return "equipos/activo";
    }
     @GetMapping("/bandasDeTransporte")
    public String bandasDeTransporte(Model model) {
       Activo activo=activoService.findByName("bandas de transporte");
         model.addAttribute("activo",activo);
          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
        return "equipos/activo";
    }
     @GetMapping("/compactador")
    public String compactador(Model model) {
       Activo activo=activoService.findByName("compactador");
         model.addAttribute("activo",activo);
          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
        return "equipos/activo";
    }
     @GetMapping("/corte")
    public String corte(Model model) {
       Activo activo=activoService.findByName("corte");
         model.addAttribute("activo",activo);
          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
        return "equipos/activo";
    }
     @GetMapping("/molino")
    public String molino(Model model) {
       Activo activo=activoService.findByName("molino");
         model.addAttribute("activo",activo);
          model.addAttribute("linkFoto","/recursos/"+activo.getNombre().replace(" ", "")+".jpg");
        return "equipos/activo";
    }
    
}

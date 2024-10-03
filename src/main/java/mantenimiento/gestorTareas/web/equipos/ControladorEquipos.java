package mantenimiento.gestorTareas.web.equipos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.ActivoDao;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.dominio.Tecnico;
import mantenimiento.gestorTareas.servicio.ActivoService;
import mantenimiento.gestorTareas.servicio.Servicio;
import mantenimiento.gestorTareas.servicio.TareaService;
import mantenimiento.gestorTareas.servicio.TecnicoService;
import mantenimiento.gestorTareas.servicio.UsuarioService;
import mantenimiento.gestorTareas.util.Convertidor;
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
    @Autowired
    TareaService tareaService;
    @Autowired
    TecnicoService tecnicoService;
    @Autowired
    ActivoDao activo;
//    dejo ejemplo paravolver a armar plantilla cuando sea necesario
//    @GetMapping("/aplicadoresDeAdhesivo")
//    public String aplicadoresDeAdhesivo(Model model) {
//        Activo activo = activoService.findByName("aplicadores de adhesivo");
//
//        cargarModel(model, activo);
//
//        return "equipos/activo";
//    }

//planta 3
    @GetMapping("/molinoAdulto3")
    public String aspiracion(Model model) {
        Activo activo = activoService.findByName("Molino adulto 3");
       
        cargarModel(model, activo);
        
        return "equipos/activo";
    }

    @GetMapping("/formacionAdulto3")
    public String formacionAdulto3(Model model) {
        Activo activo = activoService.findByName("Formacion adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/aspiracionAdulto3")
    public String aspiracionAdulto3(Model model) {
        Activo activo = activoService.findByName("Aspiracion adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transporteDeMantaYGrabadorAdulto3")
    public String transporteDeMantaYGrabadorAdulto3(Model model) {
        Activo activo = activoService.findByName("Transporte de manta y grabador adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteDePadAdulto3")
    public String corteDePadAdulto3(Model model) {
        Activo activo = activoService.findByName("Corte de pad adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/saldanteYAplicacionDeAdhesivoAdulto3")
    public String saldanteYAplicacionDeAdhesivoAdulto3(Model model) {
        Activo activo = activoService.findByName("Saldante y aplicacion de adhesivo adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/moduloDeEtiquetasYTransporteDeProductoAdulto3")
    public String moduloDeEtiquetasYTransporteDeProductoAdulto3(Model model) {
        Activo activo = activoService.findByName("Modulo de etiquetas y transporte de producto adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/plegadoYTransporteAdulto3")
    public String plegadoYTransporteAdulto3(Model model) {
        Activo activo = activoService.findByName("Plegado y transporte adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteFinalAdulto3")
    public String corteFinalAdulto3(Model model) {
        Activo activo = activoService.findByName("Corte final adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/dobladoAdulto3")
    public String dobladoAdulto3(Model model) {
        Activo activo = activoService.findByName("Doblado adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/compactadorAdulto3")
    public String compactadorAdulto3(Model model) {
        Activo activo = activoService.findByName("Compactador adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/selladoraAdulto3")
    public String selladoraAdulto3(Model model) {
        Activo activo = activoService.findByName("Selladora adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transmisionAdulto3")
    public String transmisionAdulto3(Model model) {
        Activo activo = activoService.findByName("Transmision adulto 3");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transmisionAdulto2")
    public String transmisionAdulto2(Model model) {
        Activo activo = activoService.findByName("Transmision adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/selladoraAdulto2")
    public String selladoraAdulto2(Model model) {
        Activo activo = activoService.findByName("Selladora adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/stackerAdulto2")
    public String stackerAdulto2(Model model) {
        Activo activo = activoService.findByName("Stacker adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/dobladoAdulto2")
    public String dobladoAdulto2(Model model) {
        Activo activo = activoService.findByName("Doblado adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteFinalAdulto2")
    public String corteFinalAdulto2(Model model) {
        Activo activo = activoService.findByName("Corte final adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/saldanteAdulto2")
    public String saldanteAdulto2(Model model) {
        Activo activo = activoService.findByName("Saldante adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/plegadoYTransporteAdulto2")
    public String plegadoYTransporteAdulto2(Model model) {
        Activo activo = activoService.findByName("Plegado y transporte adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteTangencialAdulto2")
    public String corteTangencialAdulto2(Model model) {
        Activo activo = activoService.findByName("Corte tangencial adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/primerYSegundoModuloDeEtiquetasAdulto2")
    public String primerYSegundoModuloDeEtiquetasAdulto2(Model model) {
        Activo activo = activoService.findByName("Primer y segundo modulo de etiquetas adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/saldanteYAplicacionDeAdhesivoAdulto2")
    public String saldanteYAplicacionDeAdhesivoAdulto2(Model model) {
        Activo activo = activoService.findByName("Saldante y aplicacion de adhesivo adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteDePadAdulto2")
    public String corteDePadAdulto2(Model model) {
        Activo activo = activoService.findByName("Corte de pad adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transporteDeMantaAdulto2")
    public String transporteDeMantaAdulto2(Model model) {
        Activo activo = activoService.findByName("Transporte de manta adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/aspiracionAdulto2")
    public String aspiracionAdulto2(Model model) {
        Activo activo = activoService.findByName("Aspiracion adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/formacionAdulto2")
    public String formacionAdulto2(Model model) {
        Activo activo = activoService.findByName("Formacion adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/molinoAdulto2")
    public String molinoAdulto2(Model model) {
        Activo activo = activoService.findByName("Molino adulto 2");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transmisionAposito")
    public String transmisionAposito(Model model) {
        Activo activo = activoService.findByName("Transmision aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/selladora2Aposito")
    public String selladora2Aposito(Model model) {
        Activo activo = activoService.findByName("Selladora 2 aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/selladoraAposito")
    public String selladoraAposito(Model model) {
        Activo activo = activoService.findByName("Selladora aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/compactadorAposito")
    public String compactadorAposito(Model model) {
        Activo activo = activoService.findByName("Compactador aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/moduloPlegadoYCompactadoAposito")
    public String moduloPlegadoYCompactadoAposito(Model model) {
        Activo activo = activoService.findByName("Modulo plegado y compactado aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/saldanteAposito")
    public String saldanteAposito(Model model) {
        Activo activo = activoService.findByName("Saldante aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/saldanteYAplicacionDeAdhesivoAposito")
    public String saldanteYAplicacionDeAdhesivoAposito(Model model) {
        Activo activo = activoService.findByName("Saldante y aplicacion de adhesivo aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/cortePadAposito")
    public String cortePadAposito(Model model) {
        Activo activo = activoService.findByName("Corte pad aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transporteDeMantaYGrabadorAposito")
    public String transporteDeMantaYGrabadorAposito(Model model) {
        Activo activo = activoService.findByName("Transporte de manta y grabador aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/molinoAposito")
    public String molinoAposito(Model model) {
        Activo activo = activoService.findByName("Molino aposito");
        cargarModel(model, activo);
        return "equipos/activo";
    }

//planta 2
    @GetMapping("/transmisionAdulto4")
    public String TransmisionAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("TransmisionAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/selladoraAdulto4")
    public String SelladoraAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("SelladoraAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/stackerAdulto4")
    public String StackerAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("StackerAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/dobladoAdulto4")
    public String DobladoAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("DobladoAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteFinalAdulto4")
    public String CorteFinalAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("CorteFinalAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/plegadoYTransporteAdulto4")
    public String PlegadoYTransporteAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("PlegadoYTransporteAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/cortePorRodaduraYBarreraAdulto4")
    public String CortePorRodaduraYBarreraAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("CortePorRodaduraYBarreraAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/traccionDeTresRodillosAdulto4")
    public String TraccionDeTresRodillosAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("TraccionDeTresRodillosAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/primerYSegundoModuloDeEtiquetasAdulto4")
    public String PrimerYSegundoModuloDeEtiquetasAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("PrimerYSegundoModuloDeEtiquetasAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transporteDeLineasAdulto4")
    public String TransporteDeLineasAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("TransporteDeLineasAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/aplicacionDeAdhesivoAdulto4")
    public String AplicacionDeAdhesivoAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("AplicacionDeAdhesivoAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transporteDeMantaAdulto4")
    public String TransporteDeMantaAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("TransporteDeMantaAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/aspiracionAdulto4")
    public String AspiracionAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("AspiracionAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/molinoChicoAdulto4")
    public String MolinoChicoAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("MolinoChicoAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/molinoGrandeAdulto4")
    public String MolinoGrandeAdulto4(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("MolinoGrandeAdulto4"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/transmisionAdulto5")
    public String TransmisionAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("TransmisionAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/selladoraAdulto5")
    public String SelladoraAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("SelladoraAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/stackerAdulto5")
    public String StackerAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("StackerAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/dobladoAdulto5")
    public String DobladoAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("DobladoAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteFinalAdulto5")
    public String CorteFinalAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("CorteFinalAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/plegadoYTransporteAdulto5")
    public String PlegadoYTransporteAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("PlegadoYTransporteAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/etiquetaYTransporteDeProductoAdulto5")
    public String EtiquetaYTransporteDeProductoAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("EtiquetaYTransporteDeProductoAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/saldanteYAplicacionDeAdhesivoAdulto5")
    public String SaldanteYAplicacionDeAdhesivoAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("SaldanteYAplicacionDeAdhesivoAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/corteDePadAdulto5")
    public String CorteDePadAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("CorteDePadAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/formacionAdulto5")
    public String FormacionAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("FormacionAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/molinoAdulto5")
    public String MolinoAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("MolinoAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/aspiracionAdulto5")
    public String AspiracionAdulto5(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("AspiracionAdulto5"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    @GetMapping("/salaDeCompresoresPlanta2")
    public String SalaDeCompresoresPlanta2(Model model) {
        Activo activo = activoService.findByName(Convertidor.deCamelCase("SalaDeCompresoresPlanta2"));
        cargarModel(model, activo);
        return "equipos/activo";
    }

    
    //endpoint usado para volver a la pagina del activo desde crear tarea
    @GetMapping("/activo/{id}")
    public String activo(Model model, Tarea tarea, Activo activoRequest) {

        Activo activoSeleccionado = activo.findById(activoRequest.getId()).orElse(null);
        String url = Convertidor.aCamelCase(activoSeleccionado.getNombre());
        
        //me aseguro que el primer caracter sea minuscula sino falla
        char primerCaracterMinuscula = Character.toLowerCase(url.charAt(0));
        url=url.substring(1);
        url=primerCaracterMinuscula+url;
        
        
        
        
        
        
        log.info("url: " + url);
        return "redirect:/" + url;
    }

    @PostMapping("/cambiarEstado/{id}")
    public String cambiarEstado( Model model,  Activo activoRequest) {
        log.info("######## "+activoRequest.getEstado());
        Activo activoSeleccionado = activo.findById(activoRequest.getId()).orElse(null);
        activoSeleccionado.setEstado(activoRequest.getEstado());
        activo.save(activoSeleccionado);
        
        String url = Convertidor.aCamelCase(activoSeleccionado.getNombre());
        
        //me aseguro que el primer caracter sea minuscula sino falla
        char primerCaracterMinuscula = Character.toLowerCase(url.charAt(0));
        url=url.substring(1);
        url=primerCaracterMinuscula+url;
        
        
        
        
        
        
        log.info("url: " + url);
        return "redirect:/" + url;
    }
    
    
    
    private void cargarModel(Model model, Activo activo) {
        model.addAttribute("activo", activo);
        List<Tecnico> tecnicos=tecnicoService.traerPorTareaEnActivo(activo);
        model.addAttribute("tecnicos", tecnicos);
        List<Tarea> tareas = servicio.listar();
        Integer cantidadMecanicas = 0;
        Integer cantidadElectronicas = 0;
        Integer cantidadNeumaticas = 0;
        Integer cantidadHidraulicas = 0;
        Integer cantidadProgramacion = 0;

        for (Tarea tarea : tareas) {
            if (tarea.getCategoriaTecnica().equals("mecánica")) {
                cantidadMecanicas++;
            }
            if (tarea.getCategoriaTecnica().equals("hidráulica")) {
                cantidadHidraulicas++;
            }
            if (tarea.getCategoriaTecnica().equals("neumática")) {
                cantidadNeumaticas++;
            }
            if (tarea.getCategoriaTecnica().equals("electrónica")) {
                cantidadElectronicas++;
            }
            if (tarea.getCategoriaTecnica().equals("programación")) {
                cantidadProgramacion++;
            }

        }
        model.addAttribute("cantidadMecanicas", cantidadMecanicas);
        model.addAttribute("cantidadHidraulicas", cantidadHidraulicas);
        model.addAttribute("cantidadNeumaticas", cantidadNeumaticas);
        model.addAttribute("cantidadElectronicas", cantidadElectronicas);
        model.addAttribute("cantidadProgramacion", cantidadProgramacion);

        model.addAttribute("linkFoto", "/recursos/" + activo.getNombre().replace(" ", "") + ".jpg");

        //indicadores
        //mtbf = promedio de minutos en funcionamiento entre fallas, 
        //mttr= promedio de minutos de parada
        Double auxCalculoMtbf = 0.0;
        Double auxCalculoMttr = 0.0;
        Double auxCalculoDisponibilidad = 0.0;
        Double auxCalculoEficiancia = 0.0;

        //aca es donde tengo que determinar el tipo de calculo, si es promedio movil por dias o detenciones, si es de acuerdo al ultimo periodo....
        //una vez definido traigo las tareas correspondientes, hay que programarlo en los Dao.
        List<Tarea> tareasIndicadores = tareaService.traerCerradasPorActivo(activo);
        for (int i = 0; i < (tareasIndicadores.size() - 1); i++) {

            Duration duration = Duration.between(tareasIndicadores.get(i).getMomentoLiberacion(), tareasIndicadores.get(i + 1).getMomentoDetencion());

            log.info(" liberacion: " + tareasIndicadores.get(i).getMomentoLiberacion().getHour() + " " + tareasIndicadores.get(i).getMomentoLiberacion().getMinute()
                    + " detencion: " + tareasIndicadores.get(i + 1).getMomentoDetencion().getHour() + " " + tareasIndicadores.get(i + 1).getMomentoDetencion().getMinute()
                    + " en minutos: " + duration.toMinutes());

            auxCalculoMtbf += duration.toMinutes();

            duration = Duration.between(tareasIndicadores.get(i).getMomentoDetencion(), tareasIndicadores.get(i).getMomentoLiberacion());

            auxCalculoMttr += duration.toMinutes();

            log.info(" detencion: " + tareasIndicadores.get(i).getMomentoDetencion().getHour() + " " + tareasIndicadores.get(i).getMomentoDetencion().getMinute()
                    + " liberacion: " + tareasIndicadores.get(i).getMomentoLiberacion().getHour() + " " + tareasIndicadores.get(i).getMomentoLiberacion().getMinute()
                    + " en minutos: " + duration.toMinutes() + " id: " + tareasIndicadores.get(i).getId());
            log.info(" ");

        }

        log.info("cantidad: " + tareasIndicadores.size());

        Double mtbf = auxCalculoMtbf / (tareasIndicadores.size() - 1);
        String mtbfFormateado = String.format("%.2f", mtbf);
        model.addAttribute("mtbf", mtbfFormateado);

        Double mttr = auxCalculoMttr / tareasIndicadores.size();
        String mttrFormateado = String.format("%.2f", mttr);
        model.addAttribute("mttr", mttrFormateado);

        //disponibilidad: (mtbf/(mtbf+mttr))x100 [%]
        Double disponibilidad = 0.0;
        if (mtbf + mttr > 0.0) {
            disponibilidad = (mtbf / (mtbf + mttr)) * 100.0;
        }
        String disponibilidadFormateado = String.format("%.2f", disponibilidad);
        model.addAttribute("disponibilidad", disponibilidadFormateado);

        // confiabilidad:  e elevado a (-t/mtbf) resultado multiplicado por 100 para tenerlo en porcentaje
        //voy a usar t=43200 que son los minutos en 30 dias
        //la linea de abajo lo resolvería, considerar si mtbf es 0 para evitar error
        //       Math.exp(-t / MTBF)
        Double confiabilidad = 0.0;
        if (mtbf > 0.0) {
            confiabilidad = Math.exp(-1440.0 / mtbf) * 100.0;
            log.info("confiabilidad: " + confiabilidad);
        }
        String confiabilidadFormateado = String.format("%.2f", confiabilidad);
        model.addAttribute("confiabilidad", confiabilidadFormateado);
        model.addAttribute("estados", Arrays.asList("detenida", "operativa", "disponible para preventivo"));

    }

}

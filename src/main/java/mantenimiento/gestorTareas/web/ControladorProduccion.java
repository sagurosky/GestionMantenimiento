package mantenimiento.gestorTareas.web;

import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.ActivoDao;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/cargaProduccion")
    public String cargaProduccion(Model model,
            @Param( "producto1") String producto1,
            @Param( "producto2") String producto2,
            @Param( "producto3") String producto3,
            @Param( "producto4") String producto4,
            @Param( "url") String url) {

        LocalDateTime fechaActual = LocalDateTime.now();
        String turno = Convertidor.obtenerTurno(fechaActual);
        LocalDateTime inicioRango = null;
        LocalDateTime finRango = null;
        if (turno.equals("turno 1")) {
            inicioRango = fechaActual.withHour(6).withMinute(0).withSecond(0).withNano(0);
            finRango = fechaActual.withHour(14).withMinute(0).withSecond(0).withNano(0);
        }
        if (turno.equals("turno 2")) {
            inicioRango = fechaActual.withHour(14).withMinute(0).withSecond(0).withNano(0);
            finRango = fechaActual.withHour(22).withMinute(0).withSecond(0).withNano(0);
        }
        if (turno.equals("turno 3")) {
            inicioRango = fechaActual.withHour(22).withMinute(0).withSecond(0).withNano(0);
            finRango = fechaActual.withHour(6).withMinute(0).withSecond(0).withNano(0);
        }

        Produccion produccion = produccionService.traerPorRangoHorario(inicioRango, finRango);
        if (produccion != null) {
            produccion.setCantidadProducto1(producto1);
            produccion.setCantidadProducto2(producto2);
            produccion.setCantidadProducto3(producto3);
            produccion.setCantidadProducto4(producto4);
            produccion.setMomentoDeCarga(fechaActual);
        } else {
            produccion = new Produccion();
            produccion.setCantidadProducto1(producto1);
            produccion.setCantidadProducto2(producto2);
            produccion.setCantidadProducto3(producto3);
            produccion.setCantidadProducto4(producto4);
            produccion.setMomentoDeCarga(fechaActual);
        }

        produccionService.save(produccion);
        model.addAttribute("produccion", produccion);
        model.addAttribute("turno", turno);
        return "/" + url;
    }

}

package mantenimiento.gestorTareas.servicio;

import java.time.LocalDateTime;
import java.util.List;
import mantenimiento.gestorTareas.dominio.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduccionService extends JpaRepository<Produccion,Long> {
    
     @Query("SELECT p FROM Produccion p WHERE p.momentoDeCarga BETWEEN :start AND :end ORDER BY p.id DESC")
    Produccion traerPorRangoHorario(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}

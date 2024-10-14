package mantenimiento.gestorTareas.servicio;

import java.util.List;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Preventivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PreventivoService extends JpaRepository<Preventivo,Long> {
    
    
    @Query("SELECT t FROM Preventivo t  WHERE "
            + "t.activo=?1")
    public List<Preventivo> traerPorActivo(Activo activo );
    
    @Query("SELECT t FROM Preventivo t  WHERE "
            + "t.activo.nombre=?1 and t.estado='validado'")
    public List<Preventivo> traerActivosValidadosPorNombreActivo(String activo );
    
    
}

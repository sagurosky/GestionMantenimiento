package mantenimiento.gestorTareas.servicio;

import java.util.List;
import mantenimiento.gestorTareas.dominio.Activo;
import mantenimiento.gestorTareas.dominio.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TareaService extends JpaRepository<Tarea,Long> {
    
 
    
    @Query("SELECT t FROM Tarea t  WHERE "
        + "t.estado !='cerrada' ")
    public List<Tarea> traerNoCerradas( );
    
    @Query("SELECT t FROM Tarea t  WHERE "
        + "t.estado ='cerrada' ")
    public List<Tarea> traerCerradas( );
    
    @Query("SELECT t FROM Tarea t  WHERE "
        + "t.estado ='cerrada' and "
            + "t.activo=?1")
    public List<Tarea> traerCerradasPorActivo(Activo activo );
  
    
    
    
    
}

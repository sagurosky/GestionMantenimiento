package mantenimiento.gestorTareas.servicio;

import java.util.List;
import mantenimiento.gestorTareas.dominio.Tecnico;
import mantenimiento.gestorTareas.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TecnicoService extends JpaRepository<Tecnico,Long> {
    
 
    
    @Query("SELECT t FROM Tecnico t  WHERE "
        + "t.usuario =?1 ")
    public Tecnico traerPorUsuario(Usuario usuario );
//    
//    @Query("SELECT t FROM Tarea t  WHERE "
//        + "t.estado ='cerrada' ")
//    public List<Tarea> traerCerradas( );
    
//    @Query("SELECT t FROM Tarea t  WHERE "
//        + "t.estado ='cerrada' and "
//            + "t.activo=?1")
//    public List<Tarea> traerCerradasPorActivo(Activo activo );
  
    
    
    
    
}

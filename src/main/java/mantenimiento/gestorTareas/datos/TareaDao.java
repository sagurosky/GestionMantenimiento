package mantenimiento.gestorTareas.datos;

import java.util.List;
import mantenimiento.gestorTareas.dominio.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TareaDao extends JpaRepository<Tarea,Long>{

    @Query("select t from Tarea t where t.maquina like %?1% "
            + "or t.descripcion like %?1%"
            + " or t.solicita like %?1%")
    public List<Tarea> filtrar(String palabraClave);
    
    
    
}

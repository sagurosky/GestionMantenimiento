package mantenimiento.gestorTareas.datos;

import mantenimiento.gestorTareas.dominio.Rol;
import mantenimiento.gestorTareas.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RolDao extends JpaRepository<Rol,Long>{

}


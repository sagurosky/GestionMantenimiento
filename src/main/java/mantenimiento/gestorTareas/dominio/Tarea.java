package mantenimiento.gestorTareas.dominio;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "tareas")
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarea")
    private Long idTarea;
    @NotEmpty
    private String maquina;
    @NotEmpty
    private String descripcion;
    private String prioridad = "baja";
    @Column(name = "tiempo")

    private String duracion=null;

    private String disponibilidad;

    private String solicita;

    private String estado;
    
    private String imagen;

}

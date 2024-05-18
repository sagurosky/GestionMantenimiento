package mantenimiento.gestorTareas.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activo")
    private Activo activo;
    
    private String descripcion;
    
    private String solicita;

    private String estado;
    
    private String imagen;
    
    private LocalDateTime momentoDetencion;
    private LocalDateTime momentoAsignacion;
    private LocalDateTime momentoLiberacion;
    private LocalDateTime momentoCierre;
    
    

}

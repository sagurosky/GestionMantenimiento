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
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activo")
    private Activo activo;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "informe")
    private Informe informe;
    
    private String categoriaTecnica;
    private String motivoDemoraAsignacion;
    private String motivoDemoraCierre;
    private String evaluacion;
    
    private String descripcion;
    
    private String solicita;

    private String estado;
    
    private String imagen;
    
    private LocalDateTime momentoDetencion;
    private LocalDateTime momentoAsignacion;
    private LocalDateTime momentoLiberacion;
    private LocalDateTime momentoCierre;
    
    

}

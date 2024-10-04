package mantenimiento.gestorTareas.dominio;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "activo")


public class Activo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
//    @NotEmpty
    private String nombre;//como es conocida
//    @NotEmpty
    private String descripcion;//tarea que realiza
//    @NotEmpty
    private String codigo;//o numero segun este identificada en planta
    
//    private String criticidad; //que tan critico es en el proceso, por ejemplo si es cuello de botella
    
    private String estado;// detenida, operativa, disponible para preventivo o mejora. 
    
    private LocalDateTime momentoDetencion;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime disponibilidadHasta;
    

}
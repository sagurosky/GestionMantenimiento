package mantenimiento.gestorTareas.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "produccion")


public class Produccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    
    private LocalDateTime momentoDeCarga;
    
    private String cantidadProducto1;
    private String cantidadProducto2;
    private String cantidadProducto3;
    private String cantidadProducto4;
    
    
    
    
    

}
package mantenimiento.gestorTareas.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "produccion")


public class Produccion implements Serializable {


    public static final String LINEA_1="adulto2";
    public static final String LINEA_2="adulto3";
    public static final String LINEA_3="adulto4";
    public static final String LINEA_4="adulto5";
    public static final String LINEA_5="aposito";
    
    
    
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    private LocalDateTime inicio;
    private LocalDateTime fin;
    
    private String ordenDeTrabajo;
    private String estado;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto")
    private Producto producto;
    private String cantidad;
    private String linea;
    
    
    

}
package mantenimiento.gestorTareas.dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "rol")

public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @NotEmpty
    private String nombre;

    //@ManyToOne(fetch = FetchType.LAZY)
    
    @Column(name = "id_usuario")
    
    private Long idUsuario;

}

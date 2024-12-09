/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mantenimiento.gestorTareas.dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author daniel
 */
@Data
@Entity
@Table(name = "informe")
public class Informe implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    private String descripcion;
    private String acciones;
    private String materiales;
    private String estadoFinal;
    private String pruebas;
    private String causaRaiz;
    private String categoriaCausaRaiz;
    private String pregunta1;
    private String pregunta2;
    private String pregunta3;
    private String pregunta4;
    private String pregunta5;
    private String seguimiento;
    private String recomendaciones;
    private String imagen;
    private String estadoInforme;
    
    
    
}

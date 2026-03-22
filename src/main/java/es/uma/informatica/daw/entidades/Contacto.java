package es.uma.informatica.daw.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="agenda")
public class Contacto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(name = "apellido")
    private String apellidos;
    private String email;
    private String telefono;
    @ManyToMany
    private List<Grupo> grupo;
}

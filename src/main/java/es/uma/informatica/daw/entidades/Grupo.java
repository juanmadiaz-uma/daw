package es.uma.informatica.daw.entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToMany(mappedBy = "grupo")
    private List<Contacto> contactos;
}

package es.uma.informatica.daw.dtos;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class ContactoDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
}

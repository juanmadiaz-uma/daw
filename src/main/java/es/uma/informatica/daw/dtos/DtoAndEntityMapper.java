package es.uma.informatica.daw.dtos;

import es.uma.informatica.daw.entidades.Contacto;

public class DtoAndEntityMapper {
    public static ContactoDTO toDto(Contacto contacto) {
        if (contacto == null) {
            return null;
        }
        return new ContactoDTO(
                contacto.getId(),
                contacto.getNombre(),
                contacto.getApellidos(),
                contacto.getEmail(),
                contacto.getTelefono()
        );
    }

    public static Contacto toEntity(ContactoDTO contactoDTO) {
        if (contactoDTO == null) {
            return null;
        }
        Contacto contacto = new Contacto();
        contacto.setId(contactoDTO.getId());
        contacto.setNombre(contactoDTO.getNombre());
        contacto.setApellidos(contactoDTO.getApellidos());
        contacto.setEmail(contactoDTO.getEmail());
        contacto.setTelefono(contactoDTO.getTelefono());
        return contacto;
    }
}

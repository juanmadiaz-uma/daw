package es.uma.informatica.daw.servicios;

import es.uma.informatica.daw.dtos.ContactoDTO;
import es.uma.informatica.daw.excepciones.ContactoNoEncontrado;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@Service
public class ContactoServicio {
    private long nextId = 0L;

    private List<ContactoDTO> contactos = new ArrayList<>();

    public List<ContactoDTO> obtenerTodosContactos(){
        return contactos;
    }

    public ContactoDTO obtenerContactoPorId(Long id){
        return contactos.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(ContactoNoEncontrado :: new);
    }

    public ContactoDTO aniadirContacto(ContactoDTO contacto){
        contacto.setId(++nextId);
        contactos.add(contacto);
        return contacto;
    }

    public void eliminarContacto(Long id){
        ContactoDTO contacto = obtenerContactoPorId(id);
        contactos.remove(contacto);
    }

    public ContactoDTO modificarContacto(Long id, ContactoDTO contactoNew){
        ContactoDTO existente = obtenerContactoPorId(id);
        existente.setNombre(contactoNew.getNombre());
        existente.setApellidos(contactoNew.getApellidos());
        existente.setEmail(contactoNew.getEmail());
        existente.setTelefono(contactoNew.getTelefono());
        return existente;
    }
}

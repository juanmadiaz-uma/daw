package es.uma.informatica.daw.servicios;

import es.uma.informatica.daw.dtos.ContactoDTO;
import es.uma.informatica.daw.entidades.Contacto;
import es.uma.informatica.daw.excepciones.ContactoNoEncontrado;
import es.uma.informatica.daw.repositorios.ContactoRepositorio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactoServicio {
    private ContactoRepositorio repositorio;

    public ContactoServicio(ContactoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Contacto> obtenerTodosContactos(){
        return repositorio.findAll();
    }

    public Contacto obtenerContactoPorId(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ContactoNoEncontrado());
    }

    public List<Contacto> obtenerContactosPorNombre(String nombre) {
        List<Contacto> contactos = repositorio.findByNombre(nombre);
        if (contactos.isEmpty()) {
            throw new ContactoNoEncontrado();
        }
        return contactos;
    }

    public Contacto aniadirContacto(Contacto contacto){
        contacto.setId(null);
        return repositorio.save(contacto);
    }

    public void eliminarContacto(Long id){
        Contacto contacto = obtenerContactoPorId(id);
        repositorio.deleteById(id);
    }

    public Contacto modificarContacto(Long id, Contacto contactoNew){
        Contacto existente = obtenerContactoPorId(id);
        existente.setNombre(contactoNew.getNombre());
        existente.setApellidos(contactoNew.getApellidos());
        existente.setEmail(contactoNew.getEmail());
        existente.setTelefono(contactoNew.getTelefono());
        repositorio.save(existente);
        return existente;
    }
}

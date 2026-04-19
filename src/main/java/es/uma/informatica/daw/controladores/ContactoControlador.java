package es.uma.informatica.daw.controladores;

import es.uma.informatica.daw.dtos.ContactoDTO;
import es.uma.informatica.daw.dtos.DtoAndEntityMapper;
import es.uma.informatica.daw.entidades.Contacto;
import es.uma.informatica.daw.excepciones.ContactoNoEncontrado;
import es.uma.informatica.daw.servicios.ContactoServicio;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contactos")
public class ContactoControlador {

    private ContactoServicio servicio;

    public ContactoControlador(ContactoServicio servicio){
        this.servicio = servicio;
    }

    @GetMapping("")
    @Operation(summary= "Obtiene todos los contactos",
            description = "Este endpoint devuelve una lista " +
                    "con todos los contactos almacenados en la base de datos."
    )
    public List<ContactoDTO> obtenerTodosContactos() {
        return servicio.obtenerTodosContactos()
                .stream()
                .map(DtoAndEntityMapper ::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactoDTO> obtenerUnContacto(@PathVariable Long id) {
        Contacto contacto = servicio.obtenerContactoPorId(id);
        return ResponseEntity.ok(DtoAndEntityMapper.toDto(contacto));
    }

    @GetMapping("/search/{nombre}")
    public ResponseEntity<List<ContactoDTO>> obtenerUnContacto(@PathVariable String nombre) {
        List<Contacto> contactos = servicio.obtenerContactosPorNombre(nombre);
        List<ContactoDTO> dtos= contactos.stream()
                .map(DtoAndEntityMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("")
    public ResponseEntity<ContactoDTO> aniadirContacto(@RequestBody ContactoDTO contacto,
                                                      UriComponentsBuilder uriBuilder) {
        Contacto entidad = DtoAndEntityMapper.toEntity(contacto);
        Contacto aniadido = servicio.aniadirContacto(entidad);
        URI location = uriBuilder.path("/contactos/{id}").buildAndExpand(aniadido.getId()).toUri();

        return ResponseEntity.created(location).body(DtoAndEntityMapper.toDto(aniadido));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarContacto(@PathVariable Long id) {
        servicio.eliminarContacto(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactoDTO> modificarContacto(@PathVariable Long id, @RequestBody ContactoDTO contacto) {
        Contacto entidad = servicio.modificarContacto(id, DtoAndEntityMapper.toEntity(contacto));
        return ResponseEntity.ok(DtoAndEntityMapper.toDto(entidad));
    }

    @ExceptionHandler(ContactoNoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void noEncontrado(){

    }

}

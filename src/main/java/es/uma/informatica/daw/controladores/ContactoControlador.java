package es.uma.informatica.daw.controladores;

import es.uma.informatica.daw.dtos.ContactoDTO;
import es.uma.informatica.daw.dtos.Mensaje;
import es.uma.informatica.daw.excepciones.ContactoNoEncontrado;
import es.uma.informatica.daw.servicios.ContactoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/contactos")
public class ContactoControlador {

    private ContactoServicio servicio;

    public ContactoControlador(ContactoServicio servicio){
        this.servicio = servicio;
    }

    @GetMapping("")
    public List<ContactoDTO> obtenerTodosContactos() {
        return servicio.obtenerTodosContactos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactoDTO> obtenerUnContacto(@PathVariable Long id) {
        return ResponseEntity.ofNullable(servicio.obtenerContactoPorId(id));
    }

    @PostMapping("")
    public ResponseEntity<ContactoDTO> aniadirContacto(@RequestBody ContactoDTO contacto,
                                                      UriComponentsBuilder uriBuilder) throws Exception {
        ContactoDTO aniadido = servicio.aniadirContacto(contacto);
        URI location = uriBuilder.path("/contactos/{id}").buildAndExpand(aniadido.getId()).toUri();
        return ResponseEntity.created(location).body(aniadido);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarContacto(@PathVariable Long id) {
        servicio.eliminarContacto(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactoDTO> modificarContacto(@PathVariable Long id, @RequestBody ContactoDTO contacto) throws Exception {
        return ResponseEntity.ofNullable(servicio.modificarContacto(id, contacto));
    }

    @PostMapping("/chat")
    public ResponseEntity<String> contactLLM(@RequestBody Mensaje mensaje) {
        String response = servicio.contactLLM(mensaje.getPrompt());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ContactoNoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void noEncontrado(){

    }

}

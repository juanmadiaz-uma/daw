package es.uma.informatica.daw.servicios;

import es.uma.informatica.daw.dtos.ContactoDTO;
import es.uma.informatica.daw.excepciones.ContactoNoEncontrado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactoServicio {
    private long nextId = 0L;

    private List<ContactoDTO> contactos = new ArrayList<>();

    ChatClient chatClient;
    Logger log = LoggerFactory.getLogger(ContactoServicio.class);

    public ContactoServicio(ChatModel geminiChatModel) {
        chatClient = ChatClient.create(geminiChatModel);
    }

    @Tool(name="getAllContactos",description = "Obtiene la lista de todos los contactos")
    public List<ContactoDTO> obtenerTodosContactos(){
        return contactos;
    }

    @Tool(name="getContactById", description="Obtiene un contacto a partir de su ID (da error si no existe)")
    public ContactoDTO obtenerContactoPorId(Long id){
        return contactos.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(ContactoNoEncontrado :: new);
    }

    @Tool(name="createContacto", description="Añade un contacto a la base de datos de contactos")
    public ContactoDTO aniadirContacto(ContactoDTO contacto){
        contacto.setId(++nextId);
        contactos.add(contacto);
        return contacto;
    }

    @Tool(name="deleteContacto", description="Elimina un contacto a partir de su ID (da error si no existe)")
    public void eliminarContacto(Long id){
        ContactoDTO contacto = obtenerContactoPorId(id);
        contactos.remove(contacto);
    }

    @Tool(name="updateContacto", description="Modifica un contacto a partir de su ID y los nuevos datos del contacto (da error si no existe)")
    public ContactoDTO modificarContacto(Long id, ContactoDTO contactoNew){
        ContactoDTO existente = obtenerContactoPorId(id);
        existente.setNombre(contactoNew.getNombre());
        existente.setApellidos(contactoNew.getApellidos());
        existente.setEmail(contactoNew.getEmail());
        existente.setTelefono(contactoNew.getTelefono());
        return existente;
    }

    public String contactLLM(String prompt) {
        log.info ("LLM contactado con prompt: {}", prompt);
        return chatClient.prompt()
                .tools(this)
                .user(prompt)
                .call()
                .content();
    }
}

package es.uma.informatica.daw.repositorios;

import es.uma.informatica.daw.entidades.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepositorio extends JpaRepository<Contacto, Long> {

}

package br.com.zup.edu.biblioteca.repository;

import br.com.zup.edu.biblioteca.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
}

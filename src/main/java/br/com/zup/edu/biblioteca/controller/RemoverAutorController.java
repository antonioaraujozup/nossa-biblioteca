package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Autor;
import br.com.zup.edu.biblioteca.repository.AutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@RestController
public class RemoverAutorController {

    private final AutorRepository repository;

    public RemoverAutorController(AutorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @DeleteMapping("/autores/{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id) {
        Autor autor = repository.findById(id)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado");
                });

        repository.delete(autor);

        return ResponseEntity.noContent().build();
    }
}

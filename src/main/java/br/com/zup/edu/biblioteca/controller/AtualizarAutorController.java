package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Autor;
import br.com.zup.edu.biblioteca.repository.AutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class AtualizarAutorController {

    private final AutorRepository repository;

    public AtualizarAutorController(AutorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PutMapping("/autores/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaAutorRequest request) {
        Autor autor = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        autor.setNome(request.getNome());
        autor.setEmail(request.getEmail());
        autor.setDescricao(request.getDescricao());
        autor.setCpf(request.getCpf());

        repository.save(autor);

        return ResponseEntity.noContent().build();
    }
}

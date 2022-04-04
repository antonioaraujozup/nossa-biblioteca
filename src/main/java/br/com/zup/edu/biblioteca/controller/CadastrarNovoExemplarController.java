package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Exemplar;
import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.repository.ExemplarRepository;
import br.com.zup.edu.biblioteca.repository.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/livros/{isbn}/exemplares")
public class CadastrarNovoExemplarController {

    private final LivroRepository livroRepository;
    private final ExemplarRepository exemplarRepository;

    public CadastrarNovoExemplarController(LivroRepository livroRepository, ExemplarRepository exemplarRepository) {
        this.livroRepository = livroRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@PathVariable("isbn") String ISBN, UriComponentsBuilder uriComponentsBuilder) {

        Livro livro = livroRepository.findByISBN(ISBN)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe livro cadastrado para o ISBN informado."));

        Exemplar novoExemplar = new Exemplar(livro);

        exemplarRepository.save(novoExemplar);

        URI location = uriComponentsBuilder.path("/livros/{isbn}/exemplares/{idExemplar}")
                .buildAndExpand(ISBN, novoExemplar.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}

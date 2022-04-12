package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.model.TipoCirculacao;
import br.com.zup.edu.biblioteca.repository.LivroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
public class RemoverLivroController {

    private final LivroRepository repository;

    public RemoverLivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @DeleteMapping("/livros/{isbn}")
    public ResponseEntity<?> remover(@PathVariable("isbn") String isbn) {
        Livro livro = repository.findByISBN(isbn).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe livro cadastrado para o isbn informado");
        });

        if (!livro.eDoTipo(TipoCirculacao.LIVRE)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Apenas livros do tipo de circulação livre podem ser removidos");
        }

        repository.delete(livro);

        return ResponseEntity.noContent().build();
    }
}

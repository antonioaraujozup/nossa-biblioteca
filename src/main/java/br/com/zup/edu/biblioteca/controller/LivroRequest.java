package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.model.TipoCirculacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class LivroRequest {
    @NotBlank
    @Length(max = 200)
    private String titulo;

    @NotBlank
    @Length(max = 4000)
    private String descricao;

    @NotBlank
    @ISBN(type = ISBN.Type.ANY)
    private String isbn;

    @Past
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPublicacao;

    @NotNull
    private TipoCirculacao circulacao;

    public LivroRequest(String titulo, String descricao, String isbn, LocalDate dataPublicacao, TipoCirculacao circulacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.circulacao = circulacao;
    }

    public LivroRequest() {
    }

    public Livro paraLivro(){
        return new Livro(titulo,descricao,isbn,dataPublicacao,circulacao);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public TipoCirculacao getCirculacao() {
        return circulacao;
    }
}

package br.com.zup.edu.biblioteca.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    @Lob
    private String descricao;

    @Column(nullable = false, unique = true)
    private String ISBN;

    @Enumerated(EnumType.STRING)
    private TipoCirculacao circulacao;

    @Column(nullable = false)
    private LocalDate dataPublicacao;

    public Livro(String titulo, String descricao, String ISBN, LocalDate dataPublicacao, TipoCirculacao circulacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.ISBN = ISBN;
        this.dataPublicacao = dataPublicacao;
        this.circulacao = circulacao;
    }

    /**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
    public Livro() {
    }

    public Boolean eDoTipo(TipoCirculacao tipoCirculacao) {
        return this.circulacao == tipoCirculacao;
    }

    public Long getId() {
        return id;
    }
}

package br.com.zup.edu.biblioteca.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraCadastro = LocalDateTime.now();

    @ManyToOne(optional = false)
    private Livro livro;

    public Exemplar(Livro livro) {
        this.livro = livro;
    }

    /**
     * @deprecated construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Exemplar() {
    }

    public Long getId() {
        return id;
    }
}

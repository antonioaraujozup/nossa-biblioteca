package br.com.zup.edu.biblioteca.controller;

import javax.validation.constraints.NotBlank;

public class AtualizaAutorRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String descricao;

    @NotBlank
    private String cpf;

    public AtualizaAutorRequest(String nome, String email, String descricao, String cpf) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.cpf = cpf;
    }

    public AtualizaAutorRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

package br.com.zup.edu.biblioteca.util;

public class MensagemDeErro {

    private String campo;
    private String mensagem;

    public MensagemDeErro(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}

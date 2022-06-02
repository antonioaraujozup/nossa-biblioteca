package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.repository.LivroRepository;
import br.com.zup.edu.biblioteca.util.MensagemDeErro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.nio.charset.StandardCharsets.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarNovoLivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        this.livroRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve cadastrar um livro com dados válidos")
    void deveCadastrarUmLivroComDadosValidos() throws Exception {

        // Cenário
        LivroRequest livroRequest = new LivroRequest(
                "O novo Fórmula 1",
                "Livro sobre o campeonato de Fórmula 1",
                "978-3-16-148410-0",
                LocalDate.of(2020, Month.JUNE, 12)
                );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        mockMvc.perform(request)
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        redirectedUrlPattern("http://localhost/livros/*")
                );

        // Asserts
        List<Livro> livros = livroRepository.findAll();
        assertEquals(1,livros.size());

    }

    @Test
    @DisplayName("Não deve cadastrar um livro com dados nulos")
    void naoDeveCadastrarUmLivroComDadosNulos() throws Exception {

        // Cenário
        LivroRequest livroRequest = new LivroRequest(
                "",
                "",
                null,
                null
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(4, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo descricao não deve estar em branco",
                "O campo isbn não deve estar em branco",
                "O campo dataPublicacao não deve ser nulo",
                "O campo titulo não deve estar em branco"
        ));

    }

    @Test
    @DisplayName("Não deve cadastrar um livro com título com mais de 200 caracteres")
    void naoDeveCadastrarUmLivroComTituloComMaisDe200Caracteres() throws Exception {

        // Cenário
        LivroRequest livroRequest = new LivroRequest(
                "O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1O novo Fórmula 1",
                "Livro sobre o campeonato de Fórmula 1",
                "978-3-16-148410-0",
                LocalDate.of(2020, Month.JUNE, 12)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo titulo o comprimento deve ser entre 0 e 200"
        ));

    }

    @Test
    @DisplayName("Não deve cadastrar um livro com descrição com mais de 2000 caracteres")
    void naoDeveCadastrarUmLivroComDescricaoComMaisDe2000Caracteres() throws Exception {

        // Cenário
        LivroRequest livroRequest = new LivroRequest(
                "O novo Fórmula 1",
                "Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1Livro sobre o campeonato de Fórmula 1",
                "978-3-16-148410-0",
                LocalDate.of(2020, Month.JUNE, 12)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo descricao o comprimento deve ser entre 0 e 2000"
        ));

    }

    @Test
    @DisplayName("Não deve cadastrar um livro com ISBN inválido")
    void naoDeveCadastrarUmLivroComISBNInvalido() throws Exception {

        // Cenário
        LivroRequest livroRequest = new LivroRequest(
                "O novo Fórmula 1",
                "Livro sobre o campeonato de Fórmula 1",
                "978-3-16-148410-00",
                LocalDate.of(2020, Month.JUNE, 12)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo isbn ISBN inválido"
        ));

    }

    @Test
    @DisplayName("Não deve cadastrar um livro com data de publicação no futuro")
    void naoDeveCadastrarUmLivroComDataDePublicacaoNoFuturo() throws Exception {

        // Cenário
        LivroRequest livroRequest = new LivroRequest(
                "O novo Fórmula 1",
                "Livro sobre o campeonato de Fórmula 1",
                "978-3-16-148410-0",
                LocalDate.of(2023, Month.JUNE, 12)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        /*assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo isbn ISBN inválido"
        ));*/

    }
}
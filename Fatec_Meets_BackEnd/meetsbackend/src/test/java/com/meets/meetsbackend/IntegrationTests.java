package com.meets.meetsbackend;

import com.meets.meetsbackend.model.Postagem;
import com.meets.meetsbackend.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

/**
 * Testes de integração básicos que inicializam a aplicação e testam
 * endpoints principais via TestRestTemplate.
 *
 * Execute localmente com: mvn test
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl(String path) {
        return "http://localhost:" + port + "/api" + path;
    }

    @Test
    public void testCreateAndListUsuarioAndPostagem() {
        // 1) Criar usuário
        Usuario u = new Usuario();
        u.setNome("Teste Integracao");
        u.setEmail("integ@test.local");
        u.setSenha("senha");

        ResponseEntity<Usuario> respCreate = restTemplate.postForEntity(baseUrl("/usuarios"), u, Usuario.class);
        Assertions.assertEquals(HttpStatus.CREATED, respCreate.getStatusCode(), "Criação de usuário deve retornar 201");
        Usuario criado = respCreate.getBody();
        Assertions.assertNotNull(criado);
        Assertions.assertNotNull(criado.getId());

        // 2) Listar usuários
        ResponseEntity<Usuario[]> listResp = restTemplate.getForEntity(baseUrl("/usuarios"), Usuario[].class);
        Assertions.assertEquals(HttpStatus.OK, listResp.getStatusCode());
        Assertions.assertTrue(listResp.getBody().length >= 1);

        // 3) Criar postagem referenciando usuário criado
        Postagem p = new Postagem();
        p.setTitulo("Post via Teste");
        p.setConteudo("Conteúdo de integração");
        Usuario ref = new Usuario(); ref.setId(criado.getId());
        p.setUsuario(ref);

        ResponseEntity<Postagem> postResp = restTemplate.postForEntity(baseUrl("/postagens"), p, Postagem.class);
        Assertions.assertEquals(HttpStatus.CREATED, postResp.getStatusCode());
        Postagem criadoPost = postResp.getBody();
        Assertions.assertNotNull(criadoPost);
        Assertions.assertNotNull(criadoPost.getId());
    }
}

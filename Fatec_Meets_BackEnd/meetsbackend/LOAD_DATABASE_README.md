# 📦 LoadDatabase - Dados de Teste

## Descrição

O `LoadDatabase` é uma classe de configuração que carrega automaticamente dados de teste no banco de dados quando a aplicação é iniciada. Os dados foram baseados no arquivo `mockPosts.json` do frontend.

## 📊 Dados Carregados

### Usuários (10)
- João Silva (joao.silva@fatec.sp.gov.br)
- Maria Santos (maria.santos@fatec.sp.gov.br)
- Pedro Lima (pedro.lima@fatec.sp.gov.br)
- Ana Costa (ana.costa@fatec.sp.gov.br)
- Carlos Mendes (carlos.mendes@fatec.sp.gov.br)
- Julia Fernandes (julia.fernandes@fatec.sp.gov.br)
- Lucas Almeida (lucas.almeida@fatec.sp.gov.br)
- Fernanda Rocha (fernanda.rocha@fatec.sp.gov.br)
- Rafael Santos (rafael.santos@fatec.sp.gov.br)
- Beatriz Lima (beatriz.lima@fatec.sp.gov.br)

**Senha para todos:** `password`

### Postagens (10)
- 6 postagens normais
- 4 postagens com eventos associados

### Eventos (4)
1. **Hackathon Fatec 2025** - 15-17 de Novembro
2. **Palestra sobre Cloud Computing** - 10/11/2025 às 19h
3. **Game Jam Fatec 2025** - 20-22/11/2025
4. **Workshop de Git e GitHub** - 08/11/2025 das 14h às 18h

### Comentários (27)
Distribuídos entre as 10 postagens

## 🚀 Como Usar

### 1. Ativar o LoadDatabase

O LoadDatabase está configurado para executar automaticamente quando o profile `dev` ou `test` está ativo.

No arquivo `application.properties`, a linha abaixo já está configurada:

```properties
spring.profiles.active=dev
```

### 2. Desativar o LoadDatabase

Se você **não** quiser carregar os dados de teste, existem duas opções:

**Opção 1:** Remover ou comentar a linha no `application.properties`:
```properties
# spring.profiles.active=dev
```

**Opção 2:** Alterar para um profile diferente:
```properties
spring.profiles.active=prod
```

### 3. Limpar dados existentes

O LoadDatabase está configurado para **limpar todos os dados** antes de carregar os novos. Se você quiser manter dados existentes, comente as linhas no método `initDatabase`:

```java
// comentarioRepository.deleteAll();
// eventoRepository.deleteAll();
// postagemRepository.deleteAll();
// usuarioRepository.deleteAll();
```

## 🔧 Personalização

### Adicionar mais dados

Edite o arquivo `LoadDatabase.java` e adicione mais usuários, postagens, eventos ou comentários seguindo o padrão existente.

### Alterar senhas

As senhas estão usando BCrypt. Para gerar uma nova senha criptografada:

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String senhaCriptografada = encoder.encode("suaSenha");
```

## 📝 Relacionamento Importante

**Todo evento DEVE ter um post associado**, mas **nem todo post precisa ter um evento**.

Exemplo:
```java
// 1. Primeiro cria a postagem
Postagem post = new Postagem("Título", "Conteúdo", usuario);
post = postagemRepository.save(post);

// 2. Depois cria o evento associado à postagem
Evento evento = new Evento(
    "Nome do Evento",
    dataEvento,
    "Local",
    criador,
    post  // <- Postagem é obrigatória!
);
eventoRepository.save(evento);
```

## 🐛 Troubleshooting

### Os dados não estão sendo carregados

1. Verifique se o profile está ativo:
   - Veja no log de inicialização: `The following profiles are active: dev`

2. Verifique se há erros no console durante a inicialização

3. Certifique-se de que `spring.jpa.hibernate.ddl-auto=create-drop` está configurado

### Console H2 para verificar dados

Acesse: http://localhost:8080/h2-console

- **JDBC URL:** `jdbc:h2:mem:meetsdb`
- **Username:** `sa`
- **Password:** `123`

## 📚 Queries úteis para testar

```sql
-- Ver todos os usuários
SELECT * FROM USUARIO;

-- Ver todas as postagens
SELECT * FROM POSTAGEM;

-- Ver todos os eventos
SELECT * FROM EVENTO;

-- Ver postagens COM eventos
SELECT p.*, e.* 
FROM POSTAGEM p 
INNER JOIN EVENTO e ON e.POSTAGEM_ID = p.ID;

-- Ver postagens SEM eventos
SELECT p.* 
FROM POSTAGEM p 
LEFT JOIN EVENTO e ON e.POSTAGEM_ID = p.ID 
WHERE e.ID IS NULL;

-- Ver comentários por postagem
SELECT p.TITULO, COUNT(c.ID) as total_comentarios
FROM POSTAGEM p
LEFT JOIN COMENTARIO c ON c.POSTAGEM_ID = p.ID
GROUP BY p.ID, p.TITULO;
```

## ✅ Checklist de Validação

- [ ] Profile `dev` está ativo
- [ ] Aplicação inicia sem erros
- [ ] Log mostra "🎉 Carregamento de dados de teste concluído!"
- [ ] 10 usuários foram criados
- [ ] 10 postagens foram criadas
- [ ] 4 eventos foram criados
- [ ] 27 comentários foram criados
- [ ] É possível acessar o H2 Console
- [ ] Dados aparecem nas tabelas do banco

---

**Criado em:** 08/11/2025
**Baseado em:** mockPosts.json do frontend

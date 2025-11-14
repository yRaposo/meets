# Fatec_Meets_BackEnd - API REST

Este projeto contém uma API REST completa em Spring Boot com modelo MVC + Services, baseada no modelo relacional fornecido.

## ✨ Funcionalidades Implementadas

- ✅ **Hash de Senha com BCrypt** - Senhas armazenadas com segurança
- ✅ **Autenticação JWT** - Tokens seguros para autenticação
- ✅ **Paginação** - Listagens com suporte a paginação
- ✅ **DTOs** - Separação clara entre modelo de dados e API
- ✅ **Validações** - Bean Validation para entrada de dados
- ✅ **CORS** - Configurado para desenvolvimento
- ✅ **H2 Console** - Para visualizar banco de dados

## 🚀 Como usar (Windows PowerShell)

### 1. Navegue até a pasta do backend:

```powershell
cd "c:/Users/Voltage/Documents/Projetos/randons/meets/Fatec_Meets_BackEnd/meetsbackend"
```

### 2. Rodar a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

Aguarde a mensagem: `Started MeetsbackendApplication in X seconds`

A API estará disponível em: **http://localhost:8080**

### 3. Testar a API:

Execute o script de testes automatizado:

```powershell
.\test-api.ps1
```

Ou teste manualmente os endpoints abaixo.

## 📋 Endpoints disponíveis

### Autenticação

- **POST** `/api/auth/login` - Login (retorna token JWT)

**Exemplo:**
```json
{
  "email": "usuario@fatec.sp.gov.br",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1Ni...",
  "tipo": "Bearer",
  "usuario": {
    "id": 1,
    "nome": "Usuario Teste",
    "email": "usuario@fatec.sp.gov.br"
  }
}
```

### Teste

- **GET** `/api/test` - Verificar se API está online
- **GET** `/api/test/ping` - Ping rápido

### Usuários
- **GET** `/api/usuarios` - Listar todos
- **GET** `/api/usuarios/{id}` - Buscar por ID
- **POST** `/api/usuarios` - Criar novo
- **PUT** `/api/usuarios/{id}` - Atualizar
- **DELETE** `/api/usuarios/{id}` - Excluir

### Postagens
- **GET** `/api/postagens` - Listar todas
- **GET** `/api/postagens/{id}` - Buscar por ID
- **POST** `/api/postagens` - Criar nova
- **PUT** `/api/postagens/{id}` - Atualizar
- **DELETE** `/api/postagens/{id}` - Excluir

### Comentários
- **GET** `/api/comentarios` - Listar todos
- **GET** `/api/comentarios/{id}` - Buscar por ID
- **POST** `/api/comentarios` - Criar novo
- **DELETE** `/api/comentarios/{id}` - Excluir

### Eventos
- **GET** `/api/eventos` - Listar todos
- **GET** `/api/eventos/{id}` - Buscar por ID
- **POST** `/api/eventos` - Criar novo
- **PUT** `/api/eventos/{id}` - Atualizar
- **DELETE** `/api/eventos/{id}` - Excluir

### Denúncias
- **GET** `/api/denuncias` - Listar todas
- **GET** `/api/denuncias/{id}` - Buscar por ID
- **POST** `/api/denuncias` - Criar nova
- **DELETE** `/api/denuncias/{id}` - Excluir

### Instituições
- **GET** `/api/instituicoes` - Listar todas
- **GET** `/api/instituicoes/{id}` - Buscar por ID
- **POST** `/api/instituicoes` - Criar nova
- **PUT** `/api/instituicoes/{id}` - Atualizar
- **DELETE** `/api/instituicoes/{id}` - Excluir

## 💡 Exemplos de uso (PowerShell)

### Criar usuário:
```powershell
$body = @{
    nome = "João Silva"
    email = "joao@fatec.sp.gov.br"
    senha = "senha123"
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/usuarios" -Body $body -ContentType 'application/json'
```

### Criar postagem:
```powershell
$body = @{
    titulo = "Minha postagem"
    conteudo = "Conteúdo da postagem"
    usuario = @{ id = 1 }
} | ConvertTo-Json -Depth 5

Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/postagens" -Body $body -ContentType 'application/json'
```

## 🏗️ Arquitetura

```
com.meets.meetsbackend
├── model/          # Entidades JPA (Usuario, Postagem, Comentario, etc)
├── repository/     # Interfaces JpaRepository
├── service/        # Lógica de negócio
├── controller/     # Endpoints REST
├── config/         # Configurações (Security, CORS, etc)
└── exception/      # Tratamento global de exceções
```

## ⚠️ Observações

- **Segurança desabilitada para desenvolvimento**: A autenticação está desabilitada (`SecurityConfig`). Em produção, implemente JWT/OAuth2.
- **Senha não criptografada**: Senhas são salvas em texto plano. Use BCrypt em produção.
- **Banco H2 em memória**: Dados são perdidos ao reiniciar. Configure MySQL/PostgreSQL para produção.
- **DTOs recomendados**: Use Data Transfer Objects para não expor entidades diretamente.

## 🧪 Testes

Execute os testes de integração:

```powershell
.\mvnw.cmd test
```

## 🔧 Tecnologias

- Spring Boot 3.5.7
- Spring Data JPA
- Spring Security (desabilitado para dev)
- H2 Database (em memória)
- Maven

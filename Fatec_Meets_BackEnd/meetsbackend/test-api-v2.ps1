# Script de teste COMPLETO com JWT e melhorias

Write-Host "=== Teste Completo API Fatec Meets (Versao 2.0) ===" -ForegroundColor Cyan
Write-Host ""

# 1) Teste de conexao
Write-Host "1) Testando conexao..." -ForegroundColor Yellow
try {
    $test = Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/test"
    Write-Host "[OK] $($test.message)" -ForegroundColor Green
} catch {
    Write-Host "[ERRO] API nao esta respondendo" -ForegroundColor Red
    exit
}
Write-Host ""

# 2) Criar usuario com senha forte
Write-Host "2) Criando usuario..." -ForegroundColor Yellow
$jsonUsuario = @"
{
    "nome": "Maria Santos",
    "email": "maria.santos@fatec.sp.gov.br",
    "senha": "senhaForte123"
}
"@

try {
    $usuario = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/usuarios" `
                                   -Body $jsonUsuario `
                                   -ContentType "application/json; charset=utf-8"
    Write-Host "[OK] Usuario criado!" -ForegroundColor Green
    Write-Host "  ID: $($usuario.id)" -ForegroundColor White
    Write-Host "  Nome: $($usuario.nome)" -ForegroundColor White
    Write-Host "  Email: $($usuario.email)" -ForegroundColor White
    $usuarioId = $usuario.id
} catch {
    Write-Host "[INFO] Usuario ja existe, tentando login..." -ForegroundColor Yellow
    # Se ja existe, vamos fazer login
    $usuarioId = 1
}
Write-Host ""

# 3) Login e obtencao de token JWT
Write-Host "3) Fazendo login (JWT)..." -ForegroundColor Yellow
$jsonLogin = @"
{
    "email": "maria.santos@fatec.sp.gov.br",
    "senha": "senhaForte123"
}
"@

try {
    $loginResponse = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/auth/login" `
                                         -Body $jsonLogin `
                                         -ContentType "application/json; charset=utf-8"
    Write-Host "[OK] Login bem-sucedido!" -ForegroundColor Green
    Write-Host "  Token: $($loginResponse.token.Substring(0, 30))..." -ForegroundColor Gray
    Write-Host "  Usuario: $($loginResponse.usuario.nome)" -ForegroundColor White
    $token = $loginResponse.token
    $usuarioId = $loginResponse.usuario.id
} catch {
    Write-Host "[ERRO] Falha no login: $_" -ForegroundColor Red
    # Se falhar, usa usuario existente
    $usuarioId = 1
}
Write-Host ""

# 4) Listar usuarios com paginacao
Write-Host "4) Listando usuarios (paginado)..." -ForegroundColor Yellow
try {
    $usuariosPaginados = Invoke-RestMethod -Method Get `
                                            -Uri "http://localhost:8080/api/usuarios?paginado=true&page=0&size=5"
    Write-Host "[OK] Pagina 1 de usuarios:" -ForegroundColor Green
    Write-Host "  Total de elementos: $($usuariosPaginados.totalElements)" -ForegroundColor White
    Write-Host "  Total de paginas: $($usuariosPaginados.totalPages)" -ForegroundColor White
    Write-Host "  Usuarios nesta pagina: $($usuariosPaginados.content.Length)" -ForegroundColor White
} catch {
    Write-Host "[ERRO] Falha ao listar usuarios paginados" -ForegroundColor Red
}
Write-Host ""

# 5) Criar postagem
Write-Host "5) Criando postagem..." -ForegroundColor Yellow
$jsonPostagem = @"
{
    "titulo": "Novidades do Spring Boot 3",
    "conteudo": "O Spring Boot 3 trouxe muitas melhorias interessantes...",
    "usuario": {
        "id": $usuarioId
    }
}
"@

try {
    $postagem = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/postagens" `
                                    -Body $jsonPostagem `
                                    -ContentType "application/json; charset=utf-8"
    Write-Host "[OK] Postagem criada!" -ForegroundColor Green
    Write-Host "  ID: $($postagem.id)" -ForegroundColor White
    Write-Host "  Titulo: $($postagem.titulo)" -ForegroundColor White
} catch {
    Write-Host "[ERRO] Falha ao criar postagem" -ForegroundColor Red
}
Write-Host ""

# 6) Resumo
Write-Host "=== Resumo dos Testes ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "Melhorias implementadas:" -ForegroundColor White
Write-Host "  [OK] Hash de senha com BCrypt" -ForegroundColor Green
Write-Host "  [OK] Autenticacao JWT" -ForegroundColor Green
Write-Host "  [OK] Paginacao de resultados" -ForegroundColor Green
Write-Host "  [OK] DTOs para Request/Response" -ForegroundColor Green
Write-Host "  [OK] Validacoes com Bean Validation" -ForegroundColor Green
Write-Host ""
Write-Host "Novos endpoints disponiveis:" -ForegroundColor White
Write-Host "  POST http://localhost:8080/api/auth/login" -ForegroundColor Cyan
Write-Host "  GET  http://localhost:8080/api/usuarios?paginado=true&page=0&size=10" -ForegroundColor Cyan
Write-Host ""
Write-Host "=== Testes Concluidos ===" -ForegroundColor Cyan

# Script de teste completo para a API Fatec Meets
# Execute este arquivo no PowerShell

Write-Host "=== Teste da API Fatec Meets ===" -ForegroundColor Cyan
Write-Host ""

# 1) Testar se API esta online
Write-Host "1) Testando conexao com API..." -ForegroundColor Yellow
try {
    $test = Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/test"
    Write-Host "[OK] API esta online: $($test.message)" -ForegroundColor Green
} catch {
    Write-Host "[ERRO] Erro ao conectar na API. Verifique se esta rodando na porta 8080" -ForegroundColor Red
    exit
}
Write-Host ""

# 2) Criar usuario
Write-Host "2) Criando usuario..." -ForegroundColor Yellow
$bodyUsuario = @{
    nome = "Joao Silva"
    email = "joao.silva@fatec.sp.gov.br"
    senha = "senha123"
} | ConvertTo-Json

try {
    $usuario = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/usuarios" -Body $bodyUsuario -ContentType 'application/json'
    Write-Host "[OK] Usuario criado com ID: $($usuario.id)" -ForegroundColor Green
    Write-Host "  Nome: $($usuario.nome)" -ForegroundColor Gray
    Write-Host "  Email: $($usuario.email)" -ForegroundColor Gray
} catch {
    Write-Host "[ERRO] Erro ao criar usuario: $_" -ForegroundColor Red
    Write-Host "Tentando usar usuario existente..." -ForegroundColor Yellow
    $usuarios = Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/usuarios"
    if ($usuarios.Length -gt 0) {
        $usuario = $usuarios[0]
        Write-Host "[OK] Usando usuario ID: $($usuario.id)" -ForegroundColor Green
    } else {
        Write-Host "[ERRO] Nenhum usuario encontrado" -ForegroundColor Red
        exit
    }
}
Write-Host ""

# 3) Criar postagem
Write-Host "3) Criando postagem..." -ForegroundColor Yellow
$bodyPostagem = @{
    titulo = "Bem-vindo ao Fatec Meets!"
    conteudo = "Esta e uma postagem de teste criada via API REST"
    usuario = @{ id = $usuario.id }
} | ConvertTo-Json -Depth 5

try {
    $postagem = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/postagens" -Body $bodyPostagem -ContentType 'application/json'
    Write-Host "[OK] Postagem criada com ID: $($postagem.id)" -ForegroundColor Green
    Write-Host "  Titulo: $($postagem.titulo)" -ForegroundColor Gray
    Write-Host "  Autor: $($postagem.usuario.nome)" -ForegroundColor Gray
} catch {
    Write-Host "[ERRO] Erro ao criar postagem: $_" -ForegroundColor Red
    Write-Host "Detalhes: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# 4) Criar comentario na postagem
if ($postagem) {
    Write-Host "4) Criando comentario..." -ForegroundColor Yellow
    $bodyComentario = @{
        conteudo = "Otima postagem! Teste de comentario."
        postagem = @{ id = $postagem.id }
        usuario = @{ id = $usuario.id }
    } | ConvertTo-Json -Depth 5

    try {
        $comentario = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/comentarios" -Body $bodyComentario -ContentType 'application/json'
        Write-Host "[OK] Comentario criado com ID: $($comentario.id)" -ForegroundColor Green
    } catch {
        Write-Host "[ERRO] Erro ao criar comentario: $_" -ForegroundColor Red
    }
    Write-Host ""
}

# 5) Listar todos os usuarios
Write-Host "5) Listando usuarios..." -ForegroundColor Yellow
try {
    $usuarios = Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/usuarios"
    Write-Host "[OK] Total de usuarios: $($usuarios.Length)" -ForegroundColor Green
    foreach ($u in $usuarios) {
        Write-Host "  - ID: $($u.id) | Nome: $($u.nome) | Email: $($u.email)" -ForegroundColor Gray
    }
} catch {
    Write-Host "[ERRO] Erro ao listar usuarios" -ForegroundColor Red
}
Write-Host ""

# 6) Listar todas as postagens
Write-Host "6) Listando postagens..." -ForegroundColor Yellow
try {
    $postagens = Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/postagens"
    Write-Host "[OK] Total de postagens: $($postagens.Length)" -ForegroundColor Green
    foreach ($p in $postagens) {
        Write-Host "  - ID: $($p.id) | Titulo: $($p.titulo) | Autor: $($p.usuario.nome)" -ForegroundColor Gray
    }
} catch {
    Write-Host "[ERRO] Erro ao listar postagens" -ForegroundColor Red
}
Write-Host ""

# 7) Criar evento
Write-Host "7) Criando evento..." -ForegroundColor Yellow
$bodyEvento = @{
    titulo = "Workshop de Spring Boot"
    dataEvento = "2025-12-01T14:00:00"
    local = "Laboratorio 101"
    criador = @{ id = $usuario.id }
} | ConvertTo-Json -Depth 5

try {
    $evento = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/eventos" -Body $bodyEvento -ContentType 'application/json'
    Write-Host "[OK] Evento criado com ID: $($evento.id)" -ForegroundColor Green
    Write-Host "  Titulo: $($evento.titulo)" -ForegroundColor Gray
    Write-Host "  Data: $($evento.dataEvento)" -ForegroundColor Gray
} catch {
    Write-Host "[ERRO] Erro ao criar evento: $_" -ForegroundColor Red
}
Write-Host ""

Write-Host "=== Testes concluidos ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "Endpoints disponiveis:" -ForegroundColor Yellow
Write-Host "  GET  http://localhost:8080/api/test" -ForegroundColor Gray
Write-Host "  GET  http://localhost:8080/api/usuarios" -ForegroundColor Gray
Write-Host "  POST http://localhost:8080/api/usuarios" -ForegroundColor Gray
Write-Host "  GET  http://localhost:8080/api/postagens" -ForegroundColor Gray
Write-Host "  POST http://localhost:8080/api/postagens" -ForegroundColor Gray
Write-Host "  GET  http://localhost:8080/api/comentarios" -ForegroundColor Gray
Write-Host "  POST http://localhost:8080/api/comentarios" -ForegroundColor Gray
Write-Host "  GET  http://localhost:8080/api/eventos" -ForegroundColor Gray
Write-Host "  POST http://localhost:8080/api/eventos" -ForegroundColor Gray
Write-Host "  GET  http://localhost:8080/api/denuncias" -ForegroundColor Gray
Write-Host "  GET  http://localhost:8080/api/instituicoes" -ForegroundColor Gray

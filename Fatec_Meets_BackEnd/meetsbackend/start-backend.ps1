# Script para iniciar o backend Spring Boot
Write-Host "Iniciando backend Meets..." -ForegroundColor Green
cd $PSScriptRoot
& "./mvnw.cmd" spring-boot:run

# Script para testar a documentação Swagger da API E-commerce

Write-Host "🚀 Testando Documentação Swagger da API E-commerce" -ForegroundColor Green
Write-Host "==================================================" -ForegroundColor Green

# Verificar se o Docker Compose está rodando
Write-Host "`n📋 Verificando status dos containers..." -ForegroundColor Yellow
docker-compose ps

Write-Host "`n🌐 URLs da Documentação:" -ForegroundColor Cyan
Write-Host "  • Swagger UI: http://localhost:8080/swagger-ui.html" -ForegroundColor White
Write-Host "  • API Docs JSON: http://localhost:8080/api-docs" -ForegroundColor White

# Verificar se a aplicação está respondendo
Write-Host "`n🔍 Testando conectividade..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api-docs" -UseBasicParsing -TimeoutSec 5
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ API está respondendo corretamente!" -ForegroundColor Green
        Write-Host "`n📖 Para acessar a documentação:" -ForegroundColor Cyan
        Write-Host "   1. Abra seu navegador" -ForegroundColor White
        Write-Host "   2. Acesse: http://localhost:8080/swagger-ui.html" -ForegroundColor White
        Write-Host "   3. Teste os endpoints diretamente na interface" -ForegroundColor White
        Write-Host "`n🔑 Para testar endpoints protegidos:" -ForegroundColor Cyan
        Write-Host "   1. Faça login em POST /auth/login" -ForegroundColor White
        Write-Host "   2. Copie o token retornado" -ForegroundColor White
        Write-Host "   3. Clique em 'Authorize' no Swagger UI" -ForegroundColor White
        Write-Host "   4. Cole o token no formato: Bearer SEU_TOKEN" -ForegroundColor White
    }
} catch {
    Write-Host "❌ API não está respondendo. Verifique se os containers estão rodando:" -ForegroundColor Red
    Write-Host "   docker-compose up -d" -ForegroundColor White
    Write-Host "   docker-compose logs app" -ForegroundColor White
}

Write-Host "`n📚 Documentação completa disponível em: SWAGGER_DOCUMENTATION.md" -ForegroundColor Cyan

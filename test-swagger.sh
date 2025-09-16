#!/bin/bash

echo "🚀 Testando Documentação Swagger da API E-commerce"
echo "=================================================="

# Verificar se o Docker Compose está rodando
echo "📋 Verificando status dos containers..."
docker-compose ps

echo ""
echo "🌐 URLs da Documentação:"
echo "  • Swagger UI: http://localhost:8080/swagger-ui.html"
echo "  • API Docs JSON: http://localhost:8080/api-docs"
echo ""

# Verificar se a aplicação está respondendo
echo "🔍 Testando conectividade..."
if curl -s http://localhost:8080/api-docs > /dev/null; then
    echo "✅ API está respondendo corretamente!"
    echo ""
    echo "📖 Para acessar a documentação:"
    echo "   1. Abra seu navegador"
    echo "   2. Acesse: http://localhost:8080/swagger-ui.html"
    echo "   3. Teste os endpoints diretamente na interface"
    echo ""
    echo "🔑 Para testar endpoints protegidos:"
    echo "   1. Faça login em POST /auth/login"
    echo "   2. Copie o token retornado"
    echo "   3. Clique em 'Authorize' no Swagger UI"
    echo "   4. Cole o token no formato: Bearer SEU_TOKEN"
else
    echo "❌ API não está respondendo. Verifique se os containers estão rodando:"
    echo "   docker-compose up -d"
    echo "   docker-compose logs app"
fi

echo ""
echo "📚 Documentação completa disponível em: SWAGGER_DOCUMENTATION.md"

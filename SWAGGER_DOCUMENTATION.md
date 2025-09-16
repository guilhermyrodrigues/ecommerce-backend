# Documentação da API - Swagger/OpenAPI

Este projeto possui documentação completa da API utilizando OpenAPI 3.0 (Swagger). A documentação é gerada automaticamente a partir das anotações nos controllers e DTOs.

## 🚀 Como Acessar a Documentação

### 1. Iniciar a Aplicação

```bash
# Iniciar todos os serviços via Docker Compose
docker-compose up -d

# Verificar se todos os containers estão rodando
docker-compose ps
```

### 2. Acessar a Interface Swagger

Após a aplicação estar rodando, acesse:

- **Interface Swagger UI**: http://localhost:8080/swagger-ui.html
- **Documentação JSON**: http://localhost:8080/api-docs

### 3. Usar a API

A documentação Swagger permite:
- ✅ Visualizar todos os endpoints disponíveis
- ✅ Testar requisições diretamente na interface
- ✅ Ver exemplos de requisições e respostas
- ✅ Autenticar usando JWT Bearer Token
- ✅ Baixar a especificação OpenAPI em JSON/YAML

## 📋 Endpoints Documentados

### 🔐 Autenticação
- `POST /auth/register` - Registrar novo usuário
- `POST /auth/login` - Fazer login e obter token JWT

### 📦 Produtos
- `GET /api/products` - Listar produtos (com filtros opcionais)
- `GET /api/products/{id}` - Buscar produto por ID
- `GET /api/products/available` - Listar produtos disponíveis
- `POST /api/products` - Criar novo produto
- `PUT /api/products/{id}` - Atualizar produto
- `DELETE /api/products/{id}` - Excluir produto

### 🏷️ Categorias
- `GET /api/categories` - Listar categorias
- `GET /api/categories/{id}` - Buscar categoria por ID
- `POST /api/categories` - Criar nova categoria
- `PUT /api/categories/{id}` - Atualizar categoria
- `DELETE /api/categories/{id}` - Excluir categoria

### 🛒 Pedidos
- `GET /api/orders` - Listar todos os pedidos (admin)
- `GET /api/orders/my-orders` - Listar meus pedidos
- `GET /api/orders/{id}` - Buscar pedido por ID
- `POST /api/orders` - Criar novo pedido
- `PATCH /api/orders/{id}/status` - Atualizar status do pedido
- `DELETE /api/orders/{id}` - Excluir pedido

## 🔑 Autenticação

A maioria dos endpoints requer autenticação via JWT Bearer Token:

1. Faça login em `POST /auth/login`
2. Copie o token retornado
3. Na interface Swagger, clique em "Authorize"
4. Cole o token no formato: `Bearer SEU_TOKEN_AQUI`

## 🛠️ Configuração Técnica

### Dependências Adicionadas
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```

### Configuração no application.yaml
```yaml
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: method
    tagsSorter: alpha
    tryItOutEnabled: true
  show-actuator: true
```

### Anotações Utilizadas
- `@Tag` - Agrupa endpoints por funcionalidade
- `@Operation` - Descreve operações individuais
- `@ApiResponses` - Documenta códigos de resposta
- `@Parameter` - Descreve parâmetros de entrada
- `@Schema` - Documenta DTOs e modelos
- `@SecurityRequirement` - Indica necessidade de autenticação

## 📝 Exemplos de Uso

### 1. Registrar Usuário
```json
POST /auth/register
{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123",
  "role": "USER"
}
```

### 2. Fazer Login
```json
POST /auth/login
{
  "email": "joao@example.com",
  "password": "senha123"
}
```

### 3. Criar Produto
```json
POST /api/products
Authorization: Bearer SEU_TOKEN
{
  "name": "Smartphone Samsung Galaxy",
  "description": "Smartphone com tela de 6.1 polegadas, 128GB de armazenamento",
  "price": 1299.99,
  "stock": 50,
  "categoryId": 1
}
```

### 4. Criar Pedido
```json
POST /api/orders
Authorization: Bearer SEU_TOKEN
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

## 🔧 Troubleshooting

### Problema: Swagger UI não carrega
- Verifique se a aplicação está rodando na porta 8080
- Acesse http://localhost:8080/swagger-ui.html
- Verifique os logs do container: `docker-compose logs app`

### Problema: Erro 401 Unauthorized
- Faça login primeiro em `/auth/login`
- Use o token retornado na autorização
- Verifique se o token não expirou

### Problema: Erro 404 Not Found
- Verifique se o endpoint existe na documentação
- Confirme se está usando o método HTTP correto
- Verifique se a aplicação está rodando corretamente

## 📚 Recursos Adicionais

- [Documentação SpringDoc OpenAPI](https://springdoc.org/)
- [Especificação OpenAPI 3.0](https://swagger.io/specification/)
- [Swagger UI](https://swagger.io/tools/swagger-ui/)

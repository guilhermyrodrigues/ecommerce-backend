# 📚 Documentação da API - Módulo 4

## 🚀 Documentação Interativa Swagger

A API agora possui documentação completa e interativa usando OpenAPI 3.0 (Swagger)!

### 🌐 Acessar a Documentação
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs JSON**: http://localhost:8080/api-docs

### ✨ Recursos da Documentação
- ✅ Interface interativa para testar endpoints
- ✅ Exemplos de requisições e respostas
- ✅ Autenticação JWT integrada
- ✅ Validações e códigos de erro documentados
- ✅ Download da especificação OpenAPI

### 🔧 Como Usar
1. Inicie a aplicação: `docker-compose up -d`
2. Acesse: http://localhost:8080/swagger-ui.html
3. Para endpoints protegidos, faça login e use o token JWT

---

## 🛍 CRUDs Principais (Product, Category, Order)

### 📋 Endpoints Disponíveis

#### 🔐 Autenticação
- `POST /auth/login` - Login do usuário
- `POST /auth/register` - Registro de novo usuário

#### 📦 Produtos (Products)
- `GET /api/products` - Listar todos os produtos
- `GET /api/products/{id}` - Buscar produto por ID
- `GET /api/products?categoryId={id}` - Filtrar por categoria
- `GET /api/products?name={nome}` - Filtrar por nome
- `GET /api/products?categoryId={id}&name={nome}` - Filtrar por categoria e nome
- `GET /api/products/available` - Listar produtos em estoque
- `POST /api/products` - Criar novo produto
- `PUT /api/products/{id}` - Atualizar produto
- `DELETE /api/products/{id}` - Deletar produto

#### 🏷️ Categorias (Categories)
- `GET /api/categories` - Listar todas as categorias
- `GET /api/categories/{id}` - Buscar categoria por ID
- `POST /api/categories` - Criar nova categoria
- `PUT /api/categories/{id}` - Atualizar categoria
- `DELETE /api/categories/{id}` - Deletar categoria

#### 🛒 Pedidos (Orders)
- `GET /api/orders` - Listar todos os pedidos (admin)
- `GET /api/orders/{id}` - Buscar pedido por ID
- `GET /api/orders/my-orders` - Listar meus pedidos
- `POST /api/orders` - Criar novo pedido (carrinho)
- `PATCH /api/orders/{id}/status` - Atualizar status do pedido
- `DELETE /api/orders/{id}` - Deletar pedido

### 📝 Exemplos de Uso

#### Criar Categoria
```json
POST /api/categories
{
    "name": "Eletrônicos"
}
```

#### Criar Produto
```json
POST /api/products
{
    "name": "Smartphone Galaxy S21",
    "description": "Smartphone Samsung Galaxy S21 128GB",
    "price": 2999.99,
    "stock": 50,
    "categoryId": 1
}
```

#### Filtrar Produtos
```
GET /api/products?categoryId=1&name=smartphone
```

#### Criar Pedido (Carrinho)
```json
POST /api/orders
{
    "items": [
        {
            "productId": 1,
            "quantity": 2
        },
        {
            "productId": 3,
            "quantity": 1
        }
    ]
}
```

#### Atualizar Status do Pedido
```
PATCH /api/orders/1/status?status=PAID
```

### 🔄 Status dos Pedidos
- `PENDING` - Pendente
- `PAID` - Pago
- `SHIPPED` - Enviado
- `DELIVERED` - Entregue
- `CANCELED` - Cancelado

### ✨ Funcionalidades Implementadas

#### ✅ DTOs + Mappers
- **Product**: RequestDTO e ResponseDTO com validações
- **Category**: RequestDTO e ResponseDTO
- **Order**: RequestDTO e ResponseDTO com cálculo automático de total
- **OrderItem**: RequestDTO e ResponseDTO
- **Mappers**: Conversão automática entre entidades e DTOs

#### ✅ Services e Controllers
- **ProductService**: CRUD completo com filtros por categoria e nome
- **CategoryService**: CRUD completo
- **OrderService**: Criação de pedidos com validação de estoque
- **Controllers**: Endpoints RESTful com validações

#### ✅ Filtros Implementados
- **Por Categoria**: `GET /api/products?categoryId=1`
- **Por Nome**: `GET /api/products?name=smartphone`
- **Combinados**: `GET /api/products?categoryId=1&name=smartphone`
- **Produtos em Estoque**: `GET /api/products/available`

#### ✅ Carrinho e Pedidos
- **Criação de Pedidos**: Com múltiplos itens
- **Validação de Estoque**: Verifica disponibilidade antes de criar pedido
- **Atualização Automática**: Estoque é reduzido automaticamente
- **Cálculo de Total**: Total calculado automaticamente baseado nos itens

#### ✅ Tratamento de Erros
- **ResourceNotFoundException**: Para recursos não encontrados
- **ValidationException**: Para erros de validação
- **GlobalExceptionHandler**: Tratamento centralizado de exceções

### 🔒 Segurança
- **Produtos e Categorias**: Acesso público (sem autenticação)
- **Pedidos**: Requer autenticação
- **JWT**: Autenticação baseada em tokens

### 🚀 Como Testar

1. **Criar Categoria**:
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name": "Eletrônicos"}'
```

2. **Criar Produto**:
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone Galaxy S21",
    "description": "Smartphone Samsung Galaxy S21 128GB",
    "price": 2999.99,
    "stock": 50,
    "categoryId": 1
  }'
```

3. **Filtrar Produtos**:
```bash
curl -X GET "http://localhost:8080/api/products?categoryId=1&name=smartphone"
```

4. **Criar Pedido** (requer autenticação):
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "items": [
      {
        "productId": 1,
        "quantity": 2
      }
    ]
  }'
```

### 📊 Estrutura do Banco de Dados

#### Tabelas Principais:
- **categories**: Categorias de produtos
- **products**: Produtos com preço, estoque e categoria
- **orders**: Pedidos com status e usuário
- **order_items**: Itens dos pedidos com quantidade
- **users**: Usuários do sistema
- **payments**: Pagamentos dos pedidos

### 🔧 Próximos Passos
- [ ] Implementar autenticação completa com JWT
- [ ] Adicionar paginação nos endpoints de listagem
- [ ] Implementar sistema de avaliações de produtos
- [ ] Adicionar upload de imagens para produtos
- [ ] Implementar sistema de cupons de desconto 
# 🛍️ Ecommerce Backend - 12 Factor App Compliant

> Uma API REST moderna para e-commerce construída com Spring Boot seguindo os princípios dos **12 Factor App**.

[![12 Factor App](https://img.shields.io/badge/12%20Factor%20App-Compliant-green.svg)](https://12factor.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [12 Factor App Compliance](#12-factor-app-compliance)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Configuração](#instalação-e-configuração)
- [Uso](#uso)
- [API Endpoints](#api-endpoints)
- [Monitoramento](#monitoramento)
- [Deploy](#deploy)
- [Desenvolvimento](#desenvolvimento)
- [Contribuição](#contribuição)

## 🎯 Sobre o Projeto

Este é um backend completo para e-commerce que implementa:

- ✅ **CRUD completo** para Produtos, Categorias e Pedidos
- ✅ **Autenticação JWT** com Spring Security
- ✅ **Sistema de carrinho** com validação de estoque
- ✅ **Arquitetura em camadas** (Controller → Service → Repository)
- ✅ **Containerização** com Docker e Docker Compose
- ✅ **Observabilidade** com métricas, logs estruturados e health checks
- ✅ **Conformidade total** com os 12 Factor App

## 🏗️ 12 Factor App Compliance

| Fator | Status | Implementação |
|-------|---------|---------------|
| **I. Codebase** | ✅ | Git repository único com múltiplos deploys |
| **II. Dependencies** | ✅ | Maven com dependências explícitas |
| **III. Config** | ✅ | Variáveis de ambiente + profiles |
| **IV. Backing Services** | ✅ | PostgreSQL, Redis, Kafka como recursos anexados |
| **V. Build, Release, Run** | ✅ | Separação clara com Docker multi-stage |
| **VI. Processes** | ✅ | Aplicação stateless + estado em backing services |
| **VII. Port Binding** | ✅ | Self-contained com Tomcat embarcado |
| **VIII. Concurrency** | ✅ | Scale horizontal + configuração de pools |
| **IX. Disposability** | ✅ | Graceful shutdown + health checks |
| **X. Dev/Prod Parity** | ✅ | Ambientes idênticos via Docker |
| **XI. Logs** | ✅ | Structured logging (JSON) para stdout |
| **XII. Admin Processes** | ✅ | Flyway migrations automatizadas |

## 🚀 Tecnologias

### Core
- **Java 21** - LTS version
- **Spring Boot 3.5.3** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Acesso a dados
- **JWT** - Tokens de autenticação

### Dados
- **PostgreSQL 16** - Banco principal
- **Redis 7** - Cache e sessões
- **Apache Kafka** - Mensageria
- **Flyway** - Migrations

### Observabilidade
- **Spring Actuator** - Health checks e métricas
- **Prometheus** - Coleta de métricas
- **Grafana** - Visualização (opcional)
- **Logback** - Structured logging

### DevOps
- **Docker & Docker Compose** - Containerização
- **Maven** - Build e gerenciamento de dependências

## 📋 Pré-requisitos

- **Java 21+**
- **Docker** e **Docker Compose**
- **Git**
- **Maven 3.9+** (ou use o wrapper incluído)

## ⚙️ Instalação e Configuração

### 1. Clone o repositório
```bash
git clone https://github.com/guilhermyrodrigues/ecommerce-backend.git
cd ecommerce-backend
```

### 2. Configure as variáveis de ambiente
```bash
# Copie o arquivo de exemplo
cp .env.example .env

# Edite as configurações conforme necessário
nano .env
```

### 3. Suba os serviços
```bash
# Desenvolvimento (sem monitoramento)
./scripts/deploy.sh --environment dev

# Desenvolvimento com monitoramento
./scripts/deploy.sh --environment dev --with-monitoring

# Produção
./scripts/deploy.sh --environment prod
```

### 4. Verifique a saúde dos serviços
```bash
./scripts/health-check.sh
```

## 🔧 Configuração de Ambiente

### Variáveis Principais

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `SPRING_PROFILES_ACTIVE` | Profile ativo | `dev`, `prod`, `test` |
| `DATABASE_URL` | URL do PostgreSQL | `jdbc:postgresql://postgres:5432/ecommerce` |
| `DATABASE_USER` | Usuário do banco | `postgres` |
| `DATABASE_PASSWORD` | Senha do banco | `sua-senha-segura` |
| `JWT_SECRET` | Chave secreta JWT | `sua-chave-super-secreta-256-bits` |
| `JWT_EXPIRATION` | Tempo de expiração JWT | `86400000` (24h) |
| `REDIS_HOST` | Host do Redis | `redis` |
| `KAFKA_SERVERS` | Servidores Kafka | `kafka:9092` |

### Profiles Disponíveis

- **`dev`** - Desenvolvimento local com logs detalhados
- **`prod`** - Produção com logging otimizado e segurança
- **`test`** - Testes com H2 in-memory

## 🌐 API Endpoints

### Autenticação
```http
POST /auth/register     # Registrar usuário
POST /auth/login        # Login
```

### Produtos (Público)
```http
GET    /api/products                    # Listar produtos
GET    /api/products/{id}               # Produto por ID
GET    /api/products?categoryId=1       # Filtrar por categoria
GET    /api/products?name=smartphone    # Filtrar por nome
GET    /api/products/available          # Produtos em estoque
POST   /api/products                    # Criar produto
PUT    /api/products/{id}               # Atualizar produto
DELETE /api/products/{id}               # Deletar produto
```

### Categorias (Público)
```http
GET    /api/categories        # Listar categorias
GET    /api/categories/{id}   # Categoria por ID
POST   /api/categories        # Criar categoria
PUT    /api/categories/{id}   # Atualizar categoria
DELETE /api/categories/{id}   # Deletar categoria
```

### Pedidos (Autenticado)
```http
GET    /api/orders                    # Listar pedidos (admin)
GET    /api/orders/{id}               # Pedido por ID
GET    /api/orders/my-orders          # Meus pedidos
POST   /api/orders                    # Criar pedido
PATCH  /api/orders/{id}/status        # Atualizar status
DELETE /api/orders/{id}               # Deletar pedido
```

### Monitoramento
```http
GET /actuator/health      # Health check
GET /actuator/info        # Informações da aplicação
GET /actuator/metrics     # Métricas
GET /actuator/prometheus  # Métricas no formato Prometheus
```

## 📊 Monitoramento

### Health Checks
A aplicação expõe diversos health checks:

```bash
# Verificação manual
curl http://localhost:8080/actuator/health

# Script automatizado
./scripts/health-check.sh
```

### Métricas
- **JVM**: Uso de memória, garbage collection, threads
- **HTTP**: Requests, response times, status codes
- **Database**: Pool de conexões, queries
- **Custom**: Métricas de negócio

### Logs Estruturados
```json
{
  "timestamp": "2024-01-15T10:30:00.123Z",
  "level": "INFO",
  "service": "ecommerce-backend",
  "logger": "com.guilhermy.ecommerce.service.ProductService",
  "message": "Product created successfully",
  "productId": 123,
  "userId": 456
}
```

## 🚀 Deploy

### Desenvolvimento Local
```bash
# Start completo
./scripts/deploy.sh --environment dev --with-monitoring

# Sem testes (mais rápido)
./scripts/deploy.sh --environment dev --skip-tests

# Apenas restart (sem rebuild)
./scripts/deploy.sh --environment dev --skip-build
```

### Produção
```bash
# Deploy de produção
./scripts/deploy.sh --environment prod

# Verificar saúde
./scripts/health-check.sh
```

### Docker Commands
```bash
# Ver logs
docker-compose logs -f app

# Parar serviços
docker-compose down

# Restart apenas a aplicação
docker-compose restart app

# Ver status
docker-compose ps
```

## 🛠️ Desenvolvimento

### Executar Localmente (sem Docker)
```bash
# Instalar dependências
./mvnw clean install

# Executar com profile dev
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Executar testes
./mvnw test
```

### Estrutura do Projeto
```
src/
├── main/
│   ├── java/com/guilhermy/ecommerce/
│   │   ├── config/          # Configurações
│   │   ├── controller/      # Controllers REST
│   │   ├── domain/          # Entidades
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── enums/           # Enums
│   │   ├── exception/       # Exception handlers
│   │   ├── jwt/             # JWT utilities
│   │   ├── mapper/          # Entity/DTO mappers
│   │   ├── repository/      # Repositories JPA
│   │   └── service/         # Business logic
│   └── resources/
│       ├── application.yaml # Configurações
│       ├── logback-spring.xml # Logging
│       └── db/migration/    # Flyway migrations
├── test/                    # Testes
scripts/                     # Scripts de deploy e utilitários
monitoring/                  # Configurações de monitoramento
```

### Adicionando Novas Features
1. Crie a entidade em `domain/`
2. Adicione o repository em `repository/`
3. Implemente o service em `service/`
4. Crie os DTOs em `dto/`
5. Adicione o mapper em `mapper/`
6. Implemente o controller em `controller/`
7. Adicione testes

## 📈 Performance e Escalabilidade

### Configurações de Produção
- **Pool de Conexões**: 50 conexões máximo
- **Timeout**: 30s para conexões
- **JVM**: G1GC com 75%
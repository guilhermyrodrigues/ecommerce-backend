# E-commerce API

Uma API REST robusta para sistema de e-commerce desenvolvida com Spring Boot 3, seguindo os princípios dos 12 fatores para aplicações modernas. A aplicação oferece funcionalidades completas de autenticação, gerenciamento de produtos, categorias e pedidos, com integração a Apache Kafka para processamento de eventos.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security** com JWT
- **PostgreSQL** como banco de dados principal
- **Apache Kafka** para mensageria
- **Flyway** para versionamento do banco
- **Docker** para containerização
- **Swagger/OpenAPI** para documentação
- **Lombok** para redução de boilerplate

## 📋 Funcionalidades

### Autenticação e Autorização
- Registro e login de usuários
- Autenticação JWT
- Controle de acesso baseado em roles (ADMIN/CUSTOMER)

### Gestão de Produtos
- CRUD completo de produtos
- Filtros por categoria e nome
- Controle de estoque
- Listagem de produtos disponíveis

### Gestão de Categorias
- CRUD completo de categorias
- Relacionamento com produtos

### Sistema de Pedidos
- Criação de pedidos com múltiplos itens
- Controle de status (PENDING, PAID, SHIPPED, DELIVERED, CANCELED)
- Verificação automática de estoque
- Histórico de pedidos por usuário

### Mensageria com Kafka
- Eventos de pedidos criados
- Processamento de pagamentos aprovados
- Atualização automática de status

## 🏗️ Arquitetura - 12 Fatores

Esta aplicação foi desenvolvida seguindo os **12 fatores** para aplicações modernas:

### ✅ 1. Base de Código (Codebase)
- Um repositório versionado com Git
- Deploy em múltiplos ambientes a partir da mesma base

### ✅ 2. Dependências (Dependencies)
- Todas as dependências declaradas no `pom.xml`
- Isolamento completo via Maven

### ✅ 3. Configurações (Config)
- Configurações externalizadas via `application.yaml`
- Variáveis de ambiente para diferentes ambientes

### ✅ 4. Serviços de Apoio (Backing Services)
- PostgreSQL, Kafka e Redis como recursos externos
- Conexões configuráveis via propriedades

### ✅ 5. Build, Release, Run (Build, release, run)
- Separação clara entre build (Maven), release e execução
- Artefatos imutáveis (JAR)

### ✅ 6. Processos (Processes)
- Aplicação stateless
- Estado compartilhado armazenado em serviços externos

### ✅ 7. Vinculação de Porta (Port binding)
- Aplicação auto-contida
- Exposição de serviços via porta HTTP

### ✅ 8. Concorrência (Concurrency)
- Escalonamento horizontal via múltiplas instâncias
- Processamento assíncrono com Kafka

### ✅ 9. Descartabilidade (Disposability)
- Inicialização rápida
- Shutdown gracioso

### ✅ 10. Paridade Dev/Prod (Dev/prod parity)
- Mesmos serviços em desenvolvimento e produção
- Docker para consistência de ambiente

### ✅ 11. Logs
- Logs enviados para stdout
- Tratamento de logs como fluxo de eventos

### ✅ 12. Processos Administrativos (Admin processes)
- Migrations via Flyway
- Scripts de administração isolados

## 🐳 Docker Compose

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/ecommerce
      - SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
    depends_on:
      - postgres
      - kafka

  postgres:
    image: postgres:15
    environment:
      - POSTGRES_DB=ecommerce
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data

  kafka:
    image: confluentinc/cp-kafka:latest
    environment:
      - KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092

  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      - ZOOKEEPER_CLIENT_PORT=2181
```

## 🚀 Como Executar

### Pré-requisitos
- Docker e Docker Compose
- Java 21 (para desenvolvimento local)
- Maven 3.6+ (para desenvolvimento local)

### Execução com Docker
```bash
# Clone o repositório
git clone <repository-url>
cd ecommerce

# Execute com Docker Compose
docker-compose up -d

# A aplicação estará disponível em http://localhost:8080
```

### Execução Local
```bash
# Instale as dependências
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

## 📚 Documentação da API

A documentação interativa da API está disponível via Swagger:
- **URL**: http://localhost:8080/swagger-ui.html

### Principais Endpoints

#### Autenticação
- `POST /auth/register` - Registrar novo usuário
- `POST /auth/login` - Fazer login

#### Produtos
- `GET /api/products` - Listar produtos
- `GET /api/products/paged?page=0&size=10` - Listar produtos paginados
- `POST /api/products` - Criar produto
- `PUT /api/products/{id}` - Atualizar produto
- `DELETE /api/products/{id}` - Excluir produto

#### Pedidos
- `GET /api/orders` - Listar pedidos
- `GET /api/orders/paged?page=0&size=10` - Listar pedidos paginados (ADMIN)
- `POST /api/orders` - Criar pedido
- `GET /api/orders/my-orders` - Meus pedidos

### 🔑 Como obter e usar o JWT no Swagger

1. **Crie um usuário** em `POST /auth/register` (role é definida como `CUSTOMER` no backend).

```json
{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123"
}
```

2. **Faça login** em `POST /auth/login`.

```json
{
  "email": "joao@example.com",
  "password": "senha123"
}
```

3. Copie o valor de `token` da resposta.
4. Clique em **Authorize** no Swagger e preencha no formato:

```text
Bearer SEU_TOKEN_AQUI
```

5. Agora você já pode testar endpoints protegidos (ex.: `/api/orders/my-orders`).

> Dica: se receber `403`, valide se a rota exige role `ADMIN`.

## 🔒 Segurança

- Autenticação JWT
- Endpoints protegidos por roles
- Validação de dados de entrada
- Tratamento global de exceções

## 📊 Monitoramento e Logs

- Logs estruturados
- Tratamento de eventos Kafka
- Health checks automáticos

## 🧪 Testes

```bash
# Executar testes
mvn test

# Executar testes com coverage
mvn test jacoco:report
```

## 📦 Build e Deploy

```bash
# Build da aplicação
mvn clean package

# Build da imagem Docker
docker build -t ecommerce-api .

# Deploy
docker run -p 8080:8080 ecommerce-api
```

## 🛠️ Configuração de Ambiente

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|---------|
| `SPRING_DATASOURCE_URL` | URL do PostgreSQL | `jdbc:postgresql://localhost:5432/ecommerce` |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Servidores Kafka | `localhost:9092` |
| `SPRING_KAFKA_CONSUMER_GROUP_ID` | Consumer group Kafka | `ecommerce-group` |
| `JWT_SECRET` | Chave secreta JWT | `super-secreta-para-jwt-nao-expirar` |
| `JWT_EXPIRATION` | Expiração do JWT | `86400000` |

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma feature branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 🎯 Próximos Passos

- [ ] Implementação de cache com Redis
- [ ] Métricas com Micrometer
- [ ] Circuit Breaker pattern
- [ ] Rate limiting
- [ ] Documentação de arquitetura
- [ ] Testes de integração
- [ ] Pipeline CI/CD

## 📞 Contato

**Guilhermy Rodrigues**
- Email: guilhermy@example.com
- LinkedIn: https://www.linkedin.com/in/guilhermy-rodrigues-7a5163244

---

> 💡 **Dica**: Esta aplicação demonstra na prática como implementar os 12 fatores em uma API Spring Boot moderna, servindo como referência para desenvolvimento de aplicações cloud-native.

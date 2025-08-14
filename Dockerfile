# Etapa de build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar apenas os arquivos de configuração primeiro (cache layer)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Download das dependências (layer cacheável)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN ./mvnw clean package -DskipTests -B


# Etapa final - runtime
FROM eclipse-temurin:21-jre-alpine

# Instalar curl para health checks
RUN apk add --no-cache curl

# Criar usuário não-root para segurança
RUN addgroup -g 1001 -S spring && \
    adduser -u 1001 -S spring -G spring

# Definir diretório de trabalho
WORKDIR /app

# Copiar JAR da etapa de build
COPY --from=build /app/target/ecommerce-*.jar app.jar

# Alterar ownership para usuário spring
RUN chown spring:spring app.jar

# Mudar para usuário não-root
USER spring:spring

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Configurações JVM otimizadas
ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0 \
               -XX:+UseG1GC \
               -XX:+UseStringDeduplication \
               -XX:+OptimizeStringConcat \
               -Djava.security.egd=file:/dev/./urandom"

# Comando de inicialização
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Labels para metadados
LABEL maintainer="guilhermyrodrigues" \
      version="1.0.0" \
      description="Ecommerce Backend - Spring Boot Application" \
      org.opencontainers.image.source="https://github.com/guilhermyrodrigues/ecommerce-backend"
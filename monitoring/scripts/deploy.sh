#!/bin/bash

# =================================
# ECOMMERCE BACKEND - DEPLOY SCRIPT
# =================================

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Default values
ENVIRONMENT="dev"
SKIP_TESTS="false"
SKIP_BUILD="false"
MONITORING="false"

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -e|--environment)
            ENVIRONMENT="$2"
            shift 2
            ;;
        --skip-tests)
            SKIP_TESTS="true"
            shift
            ;;
        --skip-build)
            SKIP_BUILD="true"
            shift
            ;;
        --with-monitoring)
            MONITORING="true"
            shift
            ;;
        -h|--help)
            echo "Usage: $0 [OPTIONS]"
            echo "Options:"
            echo "  -e, --environment ENV    Set environment (dev, prod, test)"
            echo "  --skip-tests            Skip running tests"
            echo "  --skip-build            Skip building the application"
            echo "  --with-monitoring       Include monitoring stack"
            echo "  -h, --help              Show this help message"
            exit 0
            ;;
        *)
            log_error "Unknown option: $1"
            exit 1
            ;;
    esac
done

log_info "Starting deployment for environment: $ENVIRONMENT"

# Validate environment
if [[ ! "$ENVIRONMENT" =~ ^(dev|prod|test)$ ]]; then
    log_error "Invalid environment: $ENVIRONMENT. Must be dev, prod, or test"
    exit 1
fi

# Check if .env file exists
if [[ ! -f ".env" ]]; then
    if [[ -f ".env.example" ]]; then
        log_warning ".env file not found. Copying from .env.example"
        cp .env.example .env
        log_warning "Please edit .env file with your configuration"
    else
        log_error ".env file not found and no .env.example available"
        exit 1
    fi
fi

# Load environment variables
if [[ -f ".env" ]]; then
    log_info "Loading environment variables from .env"
    export $(grep -v '^#' .env | xargs)
fi

# Set environment-specific variables
export SPRING_PROFILES_ACTIVE="$ENVIRONMENT"

if [[ "$ENVIRONMENT" == "prod" ]]; then
    # Production validations
    if [[ -z "$JWT_SECRET" || "$JWT_SECRET" == "your-super-secret-jwt-key"* ]]; then
        log_error "JWT_SECRET must be set for production deployment"
        exit 1
    fi

    if [[ -z "$DATABASE_PASSWORD" || "$DATABASE_PASSWORD" == "postgres" ]]; then
        log_error "DATABASE_PASSWORD must be changed for production deployment"
        exit 1
    fi

    export JWT_EXPIRATION="${JWT_EXPIRATION:-3600000}"  # 1 hour for prod
else
    export JWT_EXPIRATION="${JWT_EXPIRATION:-86400000}"  # 24 hours for dev/test
fi

# Run tests
if [[ "$SKIP_TESTS" == "false" ]]; then
    log_info "Running tests..."
    ./mvnw test -P$ENVIRONMENT
    log_success "Tests completed successfully"
else
    log_warning "Skipping tests"
fi

# Build application
if [[ "$SKIP_BUILD" == "false" ]]; then
    log_info "Building application..."
    ./mvnw clean package -DskipTests -P$ENVIRONMENT
    log_success "Build completed successfully"
else
    log_warning "Skipping build"
fi

# Stop existing containers
log_info "Stopping existing containers..."
docker-compose down --remove-orphans

# Build and start containers
log_info "Building and starting containers..."

if [[ "$MONITORING" == "true" ]]; then
    log_info "Starting with monitoring stack..."
    docker-compose --profile monitoring up --build -d
else
    docker-compose up --build -d
fi

# Wait for services to be healthy
log_info "Waiting for services to be healthy..."
sleep 30

# Check health status
check_health() {
    local service=$1
    local url=$2
    local max_retries=30
    local retry=0

    while [[ $retry -lt $max_retries ]]; do
        if curl -f -s "$url" > /dev/null; then
            log_success "$service is healthy"
            return 0
        fi

        retry=$((retry + 1))
        log_info "Waiting for $service... ($retry/$max_retries)"
        sleep 10
    done

    log_error "$service health check failed"
    return 1
}

# Health checks
check_health "Application" "http://localhost:${SERVER_PORT:-8080}/actuator/health"

if [[ "$MONITORING" == "true" ]]; then
    check_health "Prometheus" "http://localhost:9090/-/healthy"
    check_health "Grafana" "http://localhost:3000/api/health"
fi

# Show running services
log_info "Running services:"
docker-compose ps

# Show logs
log_info "Recent application logs:"
docker-compose logs --tail=20 app

# Show endpoints
log_success "Deployment completed successfully!"
echo ""
echo "Available endpoints:"
echo "  Application: http://localhost:${SERVER_PORT:-8080}"
echo "  Health Check: http://localhost:${SERVER_PORT:-8080}/actuator/health"
echo "  API Documentation: http://localhost:${SERVER_PORT:-8080}/swagger-ui.html"

if [[ "$MONITORING" == "true" ]]; then
    echo "  Prometheus: http://localhost:9090"
    echo "  Grafana: http://localhost:3000 (admin/admin)"
fi

echo ""
echo "Environment: $ENVIRONMENT"
echo "Profile: $SPRING_PROFILES_ACTIVE"
echo ""
echo "To stop services: docker-compose down"
echo "To view logs: docker-compose logs -f app"
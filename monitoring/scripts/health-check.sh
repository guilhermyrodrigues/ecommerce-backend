#!/bin/bash

# =================================
# ECOMMERCE BACKEND - HEALTH CHECK
# =================================

set -e

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Configuration
BASE_URL="${BASE_URL:-http://localhost:8080}"
TIMEOUT="${TIMEOUT:-10}"

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

check_endpoint() {
    local endpoint=$1
    local expected_status=${2:-200}
    local description=$3

    log_info "Checking $description..."

    local response=$(curl -s -w "%{http_code}" -o /tmp/response.txt --max-time $TIMEOUT "$BASE_URL$endpoint" || echo "000")

    if [[ "$response" == "$expected_status" ]]; then
        log_success "$description - OK (HTTP $response)"
        return 0
    else
        log_error "$description - FAILED (HTTP $response)"
        if [[ -f /tmp/response.txt ]]; then
            echo "Response: $(cat /tmp/response.txt)"
        fi
        return 1
    fi
}

check_json_endpoint() {
    local endpoint=$1
    local description=$2
    local json_path=$3
    local expected_value=$4

    log_info "Checking $description..."

    local response=$(curl -s --max-time $TIMEOUT "$BASE_URL$endpoint" || echo "{}")
    local actual_value=$(echo "$response" | jq -r "$json_path" 2>/dev/null || echo "null")

    if [[ "$actual_value" == "$expected_value" ]]; then
        log_success "$description - OK ($json_path: $actual_value)"
        return 0
    else
        log_error "$description - FAILED ($json_path: $actual_value, expected: $expected_value)"
        echo "Full response: $response"
        return 1
    fi
}

# Main health checks
log_info "Starting health checks for $BASE_URL"
echo "Timeout: ${TIMEOUT}s"
echo "=================================="

failed_checks=0

# Basic connectivity
if ! check_endpoint "/actuator/health" 200 "Application Health"; then
    ((failed_checks++))
fi

# Detailed health checks
if ! check_json_endpoint "/actuator/health" "Application Status" ".status" "UP"; then
    ((failed_checks++))
fi

# Database connectivity
if ! check_json_endpoint "/actuator/health" "Database Health" ".components.db.status" "UP"; then
    ((failed_checks++))
fi

# Redis connectivity (if available)
log_info "Checking Redis connectivity..."
redis_status=$(curl -s --max-time $TIMEOUT "$BASE_URL/actuator/health" | jq -r '.components.redis.status' 2>/dev/null || echo "null")
if [[ "$redis_status" == "UP" ]]; then
    log_success "Redis - OK"
elif [[ "$redis_status" == "null" ]]; then
    log_warning "Redis - Not configured or not available"
else
    log_error "Redis - FAILED (status: $redis_status)"
    ((failed_checks++))
fi

# Application info
if ! check_endpoint "/actuator/info" 200 "Application Info"; then
    ((failed_checks++))
fi

# Metrics endpoint
if ! check_endpoint "/actuator/prometheus" 200 "Metrics (Prometheus)"; then
    log_warning "Prometheus metrics not available"
fi

# API endpoints (basic smoke tests)
log_info "Testing API endpoints..."

# Categories endpoint (should be publicly accessible)
if ! check_endpoint "/api/categories" 200 "Categories API"; then
    ((failed_checks++))
fi

# Products endpoint (should be publicly accessible)
if ! check_endpoint "/api/products" 200 "Products API"; then
    ((failed_checks++))
fi

# Protected endpoint (should return 401/403)
log_info "Checking protected endpoint..."
orders_response=$(curl -s -w "%{http_code}" -o /tmp/orders_response.txt --max-time $TIMEOUT "$BASE_URL/api/orders" || echo "000")
if [[ "$orders_response" == "401" || "$orders_response" == "403" ]]; then
    log_success "Orders API protection - OK (HTTP $orders_response)"
else
    log_warning "Orders API protection - Unexpected response (HTTP $orders_response)"
fi

# Performance check
log_info "Checking response time..."
start_time=$(date +%s%3N)
curl -s --max-time $TIMEOUT "$BASE_URL/actuator/health" > /dev/null
end_time=$(date +%s%3N)
response_time=$((end_time - start_time))

if [[ $response_time -lt 1000 ]]; then
    log_success "Response time - OK (${response_time}ms)"
elif [[ $response_time -lt 3000 ]]; then
    log_warning "Response time - Slow (${response_time}ms)"
else
    log_error "Response time - Too slow (${response_time}ms)"
    ((failed_checks++))
fi

# Memory usage
log_info "Checking memory usage..."
memory_info=$(curl -s --max-time $TIMEOUT "$BASE_URL/actuator/metrics/jvm.memory.used" | jq -r '.measurements[0].value' 2>/dev/null || echo "unknown")
if [[ "$memory_info" != "unknown" ]]; then
    memory_mb=$((memory_info / 1024 / 1024))
    if [[ $memory_mb -lt 512 ]]; then
        log_success "Memory usage - OK (${memory_mb}MB)"
    elif [[ $memory_mb -lt 1024 ]]; then
        log_warning "Memory usage - High (${memory_mb}MB)"
    else
        log_error "Memory usage - Very high (${memory_mb}MB)"
    fi
else
    log_warning "Memory usage - Unable to determine"
fi

# Summary
echo "=================================="
if [[ $failed_checks -eq 0 ]]; then
    log_success "All health checks passed! ✅"
    exit 0
else
    log_error "$failed_checks health check(s) failed! ❌"
    exit 1
fi
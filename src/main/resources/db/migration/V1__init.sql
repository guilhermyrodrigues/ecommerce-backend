-- USERS TABLE
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- CATEGORY TABLE
CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- PRODUCT TABLE
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    category_id INTEGER REFERENCES category(id)
);

-- ORDERS TABLE
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    moment TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL,
    user_id INTEGER REFERENCES users(id)
);

-- ORDER_ITEM TABLE
CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(id) ON DELETE CASCADE,
    product_id INTEGER REFERENCES product(id),
    quantity INTEGER NOT NULL
);

-- PAYMENT TABLE
CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    moment TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    order_id INTEGER UNIQUE REFERENCES orders(id) ON DELETE CASCADE
);

-- INDEXES (opcional, para desempenho)
CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_order_user ON orders(user_id);
CREATE INDEX idx_orderitem_order ON order_item(order_id);

-- DADOS INICIAIS — CATEGORY
INSERT INTO category (name) VALUES
('Eletrônicos'),
('Livros'),
('Games'),
('Moda');

-- DADOS INICIAIS — PRODUCT
INSERT INTO product (name, description, price, stock, category_id) VALUES
('Fone Bluetooth', 'Fone de ouvido com cancelamento de ruído', 199.90, 50, 1),
('Livro: Clean Code', 'Guia para boas práticas de código', 89.90, 100, 2),
('Console XStation', 'Console de última geração', 2999.00, 20, 3),
('Camiseta Dev', 'Camiseta com estampa tech', 49.90, 150, 4);

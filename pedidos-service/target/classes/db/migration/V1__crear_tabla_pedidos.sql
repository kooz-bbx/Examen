CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    detalle TEXT,
    total DECIMAL(10,2),
    estado VARCHAR(50)
);

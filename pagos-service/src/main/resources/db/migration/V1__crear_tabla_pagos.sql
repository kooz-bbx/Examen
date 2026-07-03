CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(50),
    estado VARCHAR(50),
    pedido_id BIGINT
);

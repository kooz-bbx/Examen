CREATE TABLE IF NOT EXISTS envios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT,
    direccion VARCHAR(255),
    estado VARCHAR(50),
    fecha_envio VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha VARCHAR(20) NOT NULL,
    hora VARCHAR(10),
    cantidad_personas INT,
    cliente_id BIGINT,
    local_id BIGINT
);

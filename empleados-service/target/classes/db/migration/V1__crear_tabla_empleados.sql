CREATE TABLE IF NOT EXISTS empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    cargo VARCHAR(100),
    salario DECIMAL(10,2),
    correo VARCHAR(150) UNIQUE,
    turno VARCHAR(50),
    fecha_ingreso DATE,
    local_id BIGINT
);

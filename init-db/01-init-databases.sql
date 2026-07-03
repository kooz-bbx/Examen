-- ============================================================
-- Inicializacion automatica de bases de datos - RestaurantEnzo
-- ============================================================
-- Este script se ejecuta SOLO la primera vez que se crea el volumen
-- de MySQL (carpeta init-db montada en /docker-entrypoint-initdb.d).
-- Cada microservicio mantiene su propia base de datos (normalizada,
-- por tabla/dominio), y Flyway se encarga de crear las tablas dentro
-- de cada una al levantar el servicio correspondiente.
--
-- IMPORTANTE: este script SOLO crea las bases vacias. NO crea tablas
-- ni datos: eso lo hacen las migraciones de Flyway de cada servicio
-- (src/main/resources/db/migration de cada microservicio).
-- ============================================================

CREATE DATABASE IF NOT EXISTS bd_autenticacion CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_clientes      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_empleados     CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_envios        CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_inventario    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_locales       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_pagos         CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_pedidos       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_productos     CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_reservas      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usuario de aplicacion (alternativa a usar 'root' directamente).
-- Se deja comentado: por defecto el proyecto usa root sin clave (igual
-- que el resto del curso). Si tu equipo prefiere un usuario dedicado,
-- descomenta estas lineas y actualiza las variables DB_USERNAME /
-- DB_PASSWORD en el .env.
-- CREATE USER IF NOT EXISTS 'enzo_app'@'%' IDENTIFIED BY 'enzo_app_pwd';
-- GRANT ALL PRIVILEGES ON bd_autenticacion.* TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_clientes.*      TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_empleados.*     TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_envios.*        TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_inventario.*    TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_locales.*       TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_pagos.*         TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_pedidos.*       TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_productos.*     TO 'enzo_app'@'%';
-- GRANT ALL PRIVILEGES ON bd_reservas.*      TO 'enzo_app'@'%';
-- FLUSH PRIVILEGES;

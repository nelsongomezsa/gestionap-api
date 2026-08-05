-- Datos de ejemplo cargados automaticamente al arrancar (perfil H2 por defecto)

INSERT INTO habitaciones (codigo, direccion, precio_mensual, estado) VALUES
('H-101', 'Calle Mayor 12, 1A', 450.00, 'DISPONIBLE'),
('H-102', 'Calle Mayor 12, 1B', 480.00, 'OCUPADA'),
('H-201', 'Avenida de la Constitucion 5, 2A', 520.00, 'EN_MANTENIMIENTO');

INSERT INTO inquilinos (nombre, documento_identidad, email, telefono) VALUES
('Laura Gomez', '11111111A', 'laura.gomez@example.com', '600111222'),
('Marcos Ruiz', '22222222B', 'marcos.ruiz@example.com', '600333444');

INSERT INTO contratos (fecha_inicio, fecha_fin, habitacion_id, inquilino_id) VALUES
('2026-01-01', NULL, 2, 1);

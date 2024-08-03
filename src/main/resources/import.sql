-- Datos iniciales para la tabla `role`
INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');
INSERT INTO role (id, name) VALUES (3, 'DISABLED');

-- Datos iniciales para la tabla `invoice_status`
INSERT INTO invoice_status (id, status) VALUES (1, 'BORROWED');
INSERT INTO invoice_status (id, status) VALUES (2, 'RETURNED');

-- Datos iniciales para la tabla `section`
INSERT INTO section (id, description, name) VALUES (1, 'Libros con historias de fantasía, drama y suspenso', 'Novelas fantásticas');
INSERT INTO section (id, description, name) VALUES (2, 'Libros sobre ciencias de la informática, tecnología, programación y web', 'Informática');

-- Datos iniciales para la tabla `member`
INSERT INTO member (id, address, email, name, phone) VALUES (1, 'Montería', 'correo1@mail.com', 'Francisco Martínez', '3024327055');
INSERT INTO member (id, address, email, name, phone) VALUES (2, 'Montería', 'correo2@mail.com', 'Jorge Martínez', '3024327036');

-- Datos iniciales para la tabla `author`
INSERT INTO author (id, country, name) VALUES (1, 'Colombia', 'Gabriel García Márquez');
INSERT INTO author (id, country, name) VALUES (2, 'Colombia', 'Rafael Pombo');

-- Datos iniciales para la tabla `user_system`
INSERT INTO user_system (id, name, password, username) VALUES (1, 'admin', '$2a$10$nhwH3wWsrnbLKHsYD/uU7O2r1XN471osY6/fpr4Y/TY.7BVB25b6.', 'admin');

-- Datos iniciales para la tabla `book`
INSERT INTO book (id, genre, isbn, name, stock, total, section_id) VALUES (1, 'string', '932903JAS12', 'Cien años de soledad', 10, 10, 1);
INSERT INTO book (id, genre, isbn, name, stock, total, section_id) VALUES (3, 'string', '93290233JAS12', 'El coronel no tiene quien le escriba', 20, 20, 1);

-- Datos iniciales para la tabla `invoice`
INSERT INTO invoice (id, comments, final_date, start_date, member_id, user_id, status_id) VALUES (1, 'detalles sobre el prestamo', '2024-08-20', '2024-08-02', 1, 1, 2);

-- Datos iniciales para la tabla `book_author`
INSERT INTO book_author (book_id, author_id) VALUES (1, 1);
INSERT INTO book_author (book_id, author_id) VALUES (3, 1);

-- Datos iniciales para la tabla `invoice_item`
INSERT INTO invoice_item (id, book_id, invoice_id) VALUES (1, 1, 1);
INSERT INTO invoice_item (id, book_id, invoice_id) VALUES (2, 3, 1);

-- Datos iniciales para la tabla `user_role`
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

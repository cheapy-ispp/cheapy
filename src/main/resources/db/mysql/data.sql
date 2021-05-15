INSERT INTO users (username,password,enabled) VALUES ('admin',MD5('admin'), TRUE );
INSERT INTO authorities (username,authority) VALUES ('admin','admin');

INSERT INTO users (username,password,enabled) VALUES ('manoli',MD5('manoli'), TRUE );
INSERT INTO authorities (username,authority) VALUES ('manoli','client');
INSERT INTO users (username,password,enabled) VALUES ('david',MD5('david'), TRUE );
INSERT INTO authorities (username,authority) VALUES ('david','client');
INSERT INTO users (username,password,enabled) VALUES ('lopez',MD5('lopez'), TRUE );
INSERT INTO authorities (username,authority) VALUES ('lopez','client');

INSERT INTO users (username,password,enabled) VALUES ('paco',MD5('paco'), TRUE );
INSERT INTO authorities (username,authority) VALUES ('paco','usuario');
INSERT INTO users (username,password,enabled) VALUES ('lolo',MD5('lolo'), TRUE );
INSERT INTO authorities (username,authority) VALUES ('lolo','usuario');
INSERT INTO users (username,password,enabled) VALUES ('pepe',MD5('pepe'), TRUE );
INSERT INTO authorities (username,authority) VALUES ('pepe','usuario');

INSERT INTO administrators (id, username) VALUES (1, 'admin');
INSERT INTO usuarios (id, nombre, apellidos, email, pregunta_segura1, pregunta_segura2, username) VALUES (2, 'Paco', 'Naranjo', 'Paco@gmail.com', 'paco', 'paco', 'paco');
INSERT INTO usuarios (id, nombre, apellidos, email, pregunta_segura1, pregunta_segura2, username) VALUES (3, 'Lolo', 'Lopez', 'Lolo@gmail.com', 'lolo', 'lolo','lolo');
INSERT INTO usuarios (id, nombre, apellidos, email, pregunta_segura1, pregunta_segura2, username) VALUES (4, 'Pepe', 'Lopez', 'Pepe@gmail.com','pepe','pepe','pepe');

INSERT INTO clients (id, name, email, address, parking, municipio, init, finish, telephone, description, food, expiration, pregunta_segura1, pregunta_segura2, username, image) VALUES (1,'Bar manoli','manoli@gmail.com','C/Betis', TRUE,'Sevilla','10:00:00','22:00:00','608726190', 'Bar Manoli, ¡tu bar favorito!', 'española', '2029-08-15' ,'manoli','manoli','manoli','https://tse4.mm.bing.net/th?id=OIP.YX0mM0PK0RWz1lRDNvqQWgHaEK&pid=Api');
INSERT INTO clients (id, name, email, address, parking, municipio, init, finish, telephone, description, food, expiration, pregunta_segura1, pregunta_segura2, username, image) VALUES (2,'Bar david','david@gmail.com', 'C/Sevilla', FALSE, 'Dos_Hermanas','09:30:00','22:00:00','608726190', 'Food from USA', 'americana', '2029-08-15','david', 'david', 'david','https://u.tfstatic.com/restaurant_photos/225/75225/169/612/1950-american-diner-forte-dei-marmi-a-01f72.jpg');
INSERT INTO clients (id, name, email, address, parking, municipio, init, finish, telephone, description, food, expiration, pregunta_segura1, pregunta_segura2, username) VALUES (3,'Bar lopez','loez@gmail.com', 'C/Sevilla', FALSE, 'Dos_Hermanas','09:30:00','22:00:00','608726191', 'Lo mejor de América del Norte y del Sur', 'americana y mexicana', '2019-08-15','lopez', 'lopez', 'lopez');

INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-04-29 12:00:00', '2021-06-10 12:00:00', 'FO-1', 'inactive', 1, 'Macarrones', 15, 10.0);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price, image) VALUES ('2021-04-29 12:00:00', '2021-06-10 12:00:00', 'FO-2', 'active', 3, 'Macarrones con tomate', 10, 20.0,' https://tse4.mm.bing.net/th?id=OIP.XhLWjAnzj7a3QdJ0U2gKQwHaFi&pid=Api');
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price, image) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-3', 'active', 3, 'Estofado', 10, 25.0,'https://tse4.mm.bing.net/th?id=OIP.pEofBQc71BZpGWBMln_6ywHaFj&pid=Api');
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price, image) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-4', 'active', 1, 'Puchero', 10, 14.50,'https://tse4.mm.bing.net/th?id=OIP.KLBAw5sza4q7qPgJBMrUagHaE-&pid=Api');
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price, image) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-5', 'active', 2, 'Tumbalobos', 10, 9.45,'https://tse4.mm.bing.net/th?id=OIP.UxNHjDnczhFXWqJqv1W9qAHaE9&pid=Api');
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price, image) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-6', 'active', 2, 'Tortilla', 10, 5.2,'https://caminandopormadrid.com/wp-content/uploads/2017/04/spanish-omelette.jpg');
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price, image) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-7', 'active', 2, 'Arroz con leche', 10, 50.70,'https://tse3.mm.bing.net/th?id=OIP.QIF_7eeaKHTwlWn2D7QowAHaFj&pid=Api');
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden', 1, 'Macarrones con queso', 5, 12.43);

INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-04-25 12:00:00', '2021-06-6 12:00:00', 'T-1', 'inactive', 1, '12:00:00', '13:00:00', 5);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-04-25 12:00:00', '2021-06-6 12:00:00', 'T-2', 'active', 3, '12:00:00', '13:00:00', 10);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-3', 'active', 3, '12:30:00', '14:30:00', 10);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-4', 'active', 1, '12:00:00', '13:00:00', 5);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-5', 'active', 2, '13:00:00', '16:00:00', 15);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-6', 'active', 2, '14:00:00', '17:00:00', 15);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-7', 'active', 2, '11:00:00', '20:00:00', 20);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden', 1, '12:00:00', '13:00:00', 15);

INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-05-01 12:00:00', '2021-05-20 12:00:00', 'SP-1', 'inactive',1,5,25,10,15,15,10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-05-01 12:00:00', '2021-05-20 12:00:00', 'SP-2', 'active',1,'23:35:00',25,'23:40:00',15,'23:55:00',10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-3', 'active',3,'23:15:00',25,'23:20:00',15,'23:30:00',10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-4', 'active',3,'23:15:00',25,'23:20:00',15,'23:30:00',10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-5', 'active',2,'23:15:00',30,'23:20:00',15,'23:30:00',5);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-05-23 12:00:00', '2021-05-30 12:00:00', 'SP-6', 'active',2,'23:17:00',20,'23:24:00',15,'23:35:00',5);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-06-02 12:00:00', '2021-02-07 12:00:00', 'SP-7', 'active',2,'23:15:00',25,'23:20:00',20,'23:30:00',10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden',1,5,25,10,15,15,10);
																																																
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-04-30 12:00:00', '2021-05-4 12:00:00', 'NU-1', 'inactive',1,15,25,10,15,5,10);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-04-30 12:00:00', '2021-05-4 12:00:00', 'NU-2', 'active',1,15,25,10,15,5,10);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-3', 'active',3,15,25,12,15,3,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-4', 'active',3,15,25,13,15,2,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-5', 'active',2,20,35,15,15,5,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-6', 'active',2,20,30,15,10,10,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-7', 'active',2,20,35,15,15,10,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden',1,15,25,10,15,5,10);

INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('Es un bar muy bueno para ir a comer',5,4,5,5,'paco',1);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('Las ofertas eran buenas pero el servicio mejorable',2,2,2,2,'lolo',1);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('Nos trataron genial a mi y a mi amigo',4,4,4,4,'pepe',1);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('Abren a todas horas!!!',5,3,2,4,'paco',1);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('La comida de hoy estaba muy rica',4,4,3,4,'lolo',1);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('Recomiendo ir por la noche, tiene muy buenas vistas',4,1,1,2,'pepe',1);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('No retrasmiten futbol',1,1,1,1,'pepe',1);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('Un sitio perfecto para llevar a tu pareja',5,4,3,4,'paco',2);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('En hora punta nunca hay sitio',2,3,4,3,'lolo',2);
INSERT INTO review_client(opinion, service, food, quality_price, media, username, client) VALUES ('Fui una vez por probar y ahora voy todas las tardes',4,5,5,5,'pepe',2);

INSERT INTO review (opinion,stars,username) VALUES ('Muy útil la aplicación',4, 'paco');
INSERT INTO review (opinion,stars,username) VALUES ('Las ofertas son geniales!',5, 'pepe');
INSERT INTO review (opinion,stars,username) VALUES ('Me ha ayudado bastante',4, 'manoli');


INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (1,2);
INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (2,2);	
INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (3,2);
INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (1,3);
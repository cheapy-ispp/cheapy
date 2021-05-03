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
INSERT INTO usuarios (id, nombre, apellidos, email, username) VALUES (2, 'Paco', 'Naranjo', 'Paco@gmail.com','paco');
INSERT INTO usuarios (id, nombre, apellidos, email, username) VALUES (3, 'Lolo', 'Lopez', 'Lolo@gmail.com','lolo');
INSERT INTO usuarios (id, nombre, apellidos, email, username) VALUES (4, 'Pepe', 'Lopez', 'Pepe@gmail.com','pepe');

INSERT INTO clients (id, name, email, address, municipio, init, finish, telephone, description, food, expiration, username) VALUES (1,'Bar manoli','manoli@gmail.com','C/Betis', 'Sevilla','10:00:00','22:00:00','608726190', 'description 1', 'ESPAÑOLA', '2029-08-15' ,'manoli');
INSERT INTO clients (id, name, email, address, municipio, init, finish, telephone, description, food, expiration, username) VALUES (2,'Bar david','david@gmail.com', 'C/Sevilla', 'Dos_Hermanas','09:30:00','22:00:00','608726190', 'description 2', 'americana', '2029-08-15','david');
INSERT INTO clients (id, name, email, address, municipio, init, finish, telephone, description, food, expiration, username) VALUES (3,'Bar lopez','loez@gmail.com', 'C/Sevilla', 'Dos_Hermanas','09:30:00','22:00:00','608726191', 'description 3', 'americana y mexicana', '2019-08-15','lopez');

INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-04-29 12:00:00', '2021-05-10 12:00:00', 'FO-1', 'inactive', 1, 'Macarrones', 15, 10.0);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-2', 'active', 3, 'Macarrones con tomate', 10, 20.0);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-3', 'active', 3, 'Estofado', 10, 25.0);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-4', 'active', 1, 'Puchero', 10, 14.50);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-5', 'active', 2, 'Tumbalobos', 10, 9.45);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-6', 'active', 2, 'Tortilla', 10, 5.2);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'FO-7', 'active', 2, 'Arroz con leche', 10, 50.70);
INSERT INTO food_offers(start, end, code, status, client_id, food, discount, price) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden', 1, 'Macarrones con queso', 5, 12.43);

INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-04-25 12:00:00', '2021-05-6 12:00:00', 'T-1', 'inactive', 1, '12:00:00', '13:00:00', 5);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-2', 'active', 3, '12:00:00', '13:00:00', 10);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-3', 'active', 3, '12:30:00', '14:30:00', 10);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-4', 'active', 1, '12:00:00', '13:00:00', 5);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-5', 'active', 2, '13:00:00', '16:00:00', 15);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-6', 'active', 2, '14:00:00', '17:00:00', 15);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'T-7', 'active', 2, '11:00:00', '20:00:00', 20);
INSERT INTO time_offers(start, end, code, status, client_id, init, finish, discount) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden', 1, '12:00:00', '13:00:00', 15);

INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-05-01 12:00:00', '2021-05-20 12:00:00', 'SP-1', 'inactive',1,5,25,10,15,15,10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-2', 'active',1,35,25,40,15,55,10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-3', 'active',3,25,25,30,15,35,10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-4', 'active',3,15,25,20,15,35,10);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-5', 'active',2,15,30,20,15,50,5);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-6', 'active',2,15,30,21,15,50,5);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'SP-7', 'active',2,15,30,22,15,50,5);
INSERT INTO speed_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden',1,5,25,10,15,15,10);

INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-04-30 12:00:00', '2021-05-4 12:00:00', 'NU-1', 'inactive',1,15,25,10,15,5,10);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-2', 'active',1,15,25,10,15,5,10);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-3', 'active',3,15,25,12,15,3,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-4', 'active',3,15,25,13,15,2,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-5', 'active',2,20,35,15,15,5,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-6', 'active',2,20,30,15,10,10,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-15 12:00:00', '2021-08-16 12:00:00', 'NU-7', 'active',2,20,35,15,15,10,5);
INSERT INTO nu_offers(start, end, code, status, client_id, gold, discount_gold, silver, discount_silver, bronze, discount_bronze) VALUES ('2021-08-16 12:00:00', '2021-08-17 12:00:00', null, 'hidden',1,15,25,10,15,5,10);

INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('Es un bar muy bueno para ir a comer',5,4,5,'paco',1);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('Las ofertas eran buenas pero el servicio mejorable',2,2,2,'lolo',1);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('Nos trataron genial a mi y a mi amigo',4,4,4,'pepe',1);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('Abren a todas horas!!!',5,3,2,'paco',1);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('La comida de hoy estaba muy rica',4,4,3,'lolo',1);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('Recomiendo ir por la noche, tiene muy buenas vistas',4,1,1,'pepe',1);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('No retrasmiten futbol',1,1,1,'pepe',1);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('Un sitio perfecto para llevar a tu pareja',5,4,3,'paco',2);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('En hora punta nunca hay sitio',2,3,4,'lolo',2);
INSERT INTO review_client(opinion, service, food, quality_price, username, client) VALUES ('Fui una vez por probar y ahora voy todas las tardes',4,5,5,'pepe',2);

INSERT INTO review (opinion,stars,username) VALUES ('Muy útil la aplicación',4, 'paco');

INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (1,2);
INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (2,2);	
INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (3,2);
INSERT INTO usuario_favoritos(client_id, usuario_id) VALUES (1,3);
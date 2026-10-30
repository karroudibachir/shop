CREATE TABLE categories (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(80) NOT NULL UNIQUE, description VARCHAR(500));
CREATE TABLE products (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(160) NOT NULL, description TEXT NOT NULL, price DECIMAL(10,2) NOT NULL, stock_quantity INT NOT NULL, image_url VARCHAR(500), category_id BIGINT NOT NULL, active BOOLEAN NOT NULL DEFAULT TRUE, created_at TIMESTAMP(6) NOT NULL, updated_at TIMESTAMP(6) NOT NULL, CONSTRAINT fk_product_category FOREIGN KEY(category_id) REFERENCES categories(id), INDEX idx_product_category_active(category_id,active), INDEX idx_product_name(name));
INSERT INTO categories(name,description) VALUES ('Electronics','Everyday technology'),('Home','Useful things for home'),('Fashion','Clothing and accessories'),('Sports','Fitness and outdoor'),('Books','Reading and learning');
INSERT INTO products(name,description,price,stock_quantity,image_url,category_id,active,created_at,updated_at) VALUES
('Wireless Headphones','Comfortable over-ear headphones with clear sound.',79.99,24,'https://images.unsplash.com/photo-1505740420928-5e560c06d30e',1,TRUE,NOW(),NOW()),
('Bluetooth Speaker','Portable speaker with rich sound.',49.90,31,'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1',1,TRUE,NOW(),NOW()),
('Smart Watch','Track activity and stay connected.',129.00,18,'https://images.unsplash.com/photo-1523275335684-37898b6baf30',1,TRUE,NOW(),NOW()),
('Desk Lamp','Adjustable warm LED desk lamp.',34.50,42,'https://images.unsplash.com/photo-1507473885765-e6ed057f782c',2,TRUE,NOW(),NOW()),
('Ceramic Planter','Minimal ceramic planter for indoor plants.',22.00,36,'https://images.unsplash.com/photo-1485955900006-10f4d324d411',2,TRUE,NOW(),NOW()),
('Cotton Throw','Soft woven throw for the sofa.',45.00,15,'https://images.unsplash.com/photo-1600210492486-724fe5c67fb0',2,TRUE,NOW(),NOW()),
('Canvas Backpack','Durable everyday backpack.',58.00,20,'https://images.unsplash.com/photo-1553062407-98eeb64c6a62',3,TRUE,NOW(),NOW()),
('Classic Sunglasses','Lightweight UV protective sunglasses.',39.00,28,'https://images.unsplash.com/photo-1511499767150-a48a237f0083',3,TRUE,NOW(),NOW()),
('Leather Wallet','Slim wallet with multiple card slots.',42.00,17,'https://images.unsplash.com/photo-1627123424574-724758594e93',3,TRUE,NOW(),NOW()),
('Yoga Mat','Non-slip mat for home or studio practice.',32.00,40,'https://images.unsplash.com/photo-1592432678016-e910b452f9a2',4,TRUE,NOW(),NOW()),
('Water Bottle','Insulated stainless steel bottle.',24.95,50,'https://images.unsplash.com/photo-1602143407151-7111542de6e8',4,TRUE,NOW(),NOW()),
('Running Cap','Breathable cap for outdoor training.',19.00,33,'https://images.unsplash.com/photo-1588850561407-ed78c282e89b',4,TRUE,NOW(),NOW()),
('Everyday Sneakers','Comfortable lightweight sneakers.',89.00,12,'https://images.unsplash.com/photo-1542291026-7eec264c27ff',3,TRUE,NOW(),NOW()),
('Cookbook: Simple Meals','A practical guide to quick home cooking.',28.00,14,'https://images.unsplash.com/photo-1544947950-fa07a98d237f',5,TRUE,NOW(),NOW()),
('Travel Journal','Hardcover journal for notes and ideas.',16.00,44,'https://images.unsplash.com/photo-1531346878377-a5be20888e57',5,TRUE,NOW(),NOW()),
('Desk Organizer','Keep stationery and accessories tidy.',27.00,25,'https://images.unsplash.com/photo-1494438639946-1ebd1d20bf85',2,TRUE,NOW(),NOW()),
('Wireless Mouse','Ergonomic wireless mouse.',29.00,37,'https://images.unsplash.com/photo-1527814050087-3793815479db',1,TRUE,NOW(),NOW()),
('Yoga Blocks','Supportive pair of foam yoga blocks.',18.00,30,'https://images.unsplash.com/photo-1599447421416-3414500d18a5',4,TRUE,NOW(),NOW()),
('Wool Beanie','Warm ribbed knit beanie.',26.00,21,'https://images.unsplash.com/photo-1576871337622-98d48d1cf531',3,TRUE,NOW(),NOW()),
('Reading Light','Rechargeable clip-on reading light.',21.00,19,'https://images.unsplash.com/photo-1507842217343-583bb7270b66',5,TRUE,NOW(),NOW());

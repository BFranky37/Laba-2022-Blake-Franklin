-- Insertion Statements
INSERT INTO addresses (street, city, zipcode)
VALUES ('123 Street st', 'Cityville', 11111),
		('321 Road rd', 'Hometown', 22222),
        ('555 Road rd', 'Hometown', 22222),
        ('635 Frank ave', 'New Orleans', 70118),
        ('765 Victory st', 'Cityville', 11112),
        ('111 Lane ln', 'New Yourk', 95221),
        ('951 Rainbow rd', 'Los Angeles', 10059),
        ('357 Cheese st', 'Miami', 83373),
        ('456 Green ave', 'Orlando', 85225),
        ('545 Forest ln', 'Atlanta', 67676);

INSERT INTO boxes (length, width, height)
VALUES (5, 5, 5),
		(8, 8, 8),
        (12, 12, 12),
        (10, 10, 10),
        (6, 6, 6),
        (12, 12, 8),
        (16, 16, 12),
        (16, 16, 16),
        (16, 16, 8),
        (12, 8, 6);

INSERT INTO discounts (name, rate)
VALUES ('Veteran', .10),
		('Senior Citizen', .05),
        ('Employee', .10),
        ('Membership Holder', .15),
        ('None', 0);

INSERT INTO insurance (name, base_cost, price_rate)
VALUES ('Basic Insurance', .3, .015),
		('Fragility Insurance', .5, .03),
        ('High Value Insurance', 1, .5),
        ('No Insurance', 0, 0),
        ('High Value Fragility Insurance', 1.5, .55);

INSERT INTO profiles (name, phone_number, addressID)
VALUES ('Blake Franklin', '1234567890', 1),
		('Kevin Boller', '9876543210', 2),
        ('Will Billiam', '4365402748', 3),
        ('Donny Gee', '4274930282', 4),
        ('Alex Fantastic', '9305714378', 5),
        ('John Madden', '8887776321', 6),
        ('Mike Madden', '8887775123', 6),
        ('Melina Dagel', '5876302811', 7),
        ('Grace Falsetto', '3388004422', 8),
        ('Arthur Takanaka', '1231231234', 9);

INSERT INTO users (profileID, password)
VALUES (1, 'myPassword'),
		(2, 'hehehaha33'),
        (3, 'LoLoL!!'),
        (4, 'qwertyuiop'),
        (5, 'H0TB0D'),
        (6, 'B@tman911'),
        (7, '12345'),
        (8, 'q2o9r7cqby3'),
        (9, 'the1PIECE'),
        (10, '10fingers10toes?');
        
INSERT INTO user_has_discount (userID, discountID)
VALUES (1, 1),
		(2, 1),
        (3, 3),
        (4, 4),
        (5, 4),
        (6, 5),
        (7, 5),
        (8, 5),
        (9, 5),
        (10, 5);

INSERT INTO vehicle_types (name, cost_rate, weight_capacity, space_capacity)
VALUES ('Standard Delivery Car', 2.0, 150, 65000),
		('Heavy Delivery Truck', 3.0, 300, 90000),
        ('Priority Delivery Car', 6.0, 150, 65000),
        ('Delivery Freight Truck', 8.0, 1000, 500000),
        ('Standard Delivery Plane', 10.0, 300, 90000),
        ('Heavy Cargo Plane', 20.0, 3000, 500000);

INSERT INTO vehicles (vehicle_typeID, tracking_number)
VALUES (1, '01-123456'),
		(1, '01-938566'),
        (2, '02-098765'),
        (3, '03-524509'),
        (4, '04-965258'),
        (5, '05-225543'),
        (5, '05-445566'),
        (6, '06-111231'),
        (1, '01-709870'),
        (2, '02-608242');

INSERT INTO packages (boxID, weight, fragility, value, cost)
VALUES (1, 15, 1, 25, 7.3),
		(2, 20, 0, 30, 9),
        (2, 18, 1, 55, 8.2),
        (3, 22, 0, 60, 6),
        (4, 25, 1, 12, 6.3),
        (5, 29, 0, 10, 7),
        (5, 30, 1, 75, 12),
        (6, 30, 0, 15, 13.5),
        (7, 26, 1, 200, 14.65),
        (8, 35, 0, 150, 16);

INSERT INTO routes (distance, price, from_addressID, to_addressID)
VALUES (11122, 3.5, 1, 2),
		(26485, 3.75, 2, 3),
        (86425, 12.9, 1, 4),
        (96385, 14, 5, 1),
        (45691, 6.25, 8, 4),
        (91465, 13.4, 7, 9),
        (65232, 8.35, 9, 7),
        (33221, 4.5, 6, 5),
        (41233, 5.5, 6, 1),
        (32615, 4.25, 4, 3);

INSERT INTO shipments (senderID, recipientID, packageID, insuranceID, routeID, vehicleID, priority, price)
VALUES (1, 2, 1, 1, 1, 1, 0, 18),
		(2, 3, 2, 2, 2, 2, 1, 24),
        (1, 4, 3, 3, 3, 3, 0, 20.25),
        (5, 1, 4, 4, 4, 4, 1, 30),
        (8, 4, 5, 5, 5, 5, 0, 17.5),
        (7, 9, 6, 1, 6, 6, 1, 14.75),
        (9, 7, 7, 2, 7, 7, 0, 15),
        (6, 5, 8, 3, 8, 8, 1, 16),
        (6, 1, 8, 4, 9, 9, 0, 22),
        (4, 3, 10, 5, 10, 10, 1, 50);
        
INSERT INTO shipment_status (shipment_id, delivered)
VALUES (1, 1),
		(2, 0),
        (3, 0),
        (4, 0),
        (5, 1),
        (6, 1),
        (7, 0),
        (8, 1),
        (9, 0),
        (10, 1);

-- Update statements
UPDATE addresses
SET street = '124 Street st'
WHERE AddressID = 1;

UPDATE addresses
SET city = 'Bigtown', zipcode = 22233
WHERE AddressID = 2;

UPDATE profiles
SET name = 'Blake Franklin'
WHERE id = 1;

UPDATE profiles
SET name = 'Kevin Burns'
WHERE id = 2;

UPDATE profiles
SET phone_number = '5555555555'
WHERE id = 2;

UPDATE boxes
SET length = 8, width = 8
WHERE id = 1;

UPDATE discounts
SET rate = .15
WHERE id = 1;

UPDATE users
SET password = 'm0reS3cure!'
WHERE id = 1;

UPDATE vehicle_types
SET cost_rate = 1.5
WHERE id = 1;

UPDATE vehicle_types
SET weight_capacity = 200
WHERE id = 1;

-- Deletion statements
DELETE FROM shipments
WHERE vehicleID = 1;

DELETE FROM vehicles
WHERE vehicle_typeID = 1;

DELETE FROM vehicle_types
WHERE id = 1;

DELETE FROM packages
WHERE boxID = 1;

DELETE FROM boxes
WHERE id = 1;

DELETE FROM boxes;

DELETE FROM insurance
WHERE base_cost = .3;

DELETE FROM discounts
WHERE rate = .10;

DELETE FROM users;

DELETE FROM profiles
WHERE id = 1;

-- Alter table statements
ALTER TABLE profiles
DROP COLUMN phone_number;

ALTER TABLE users
ADD email varchar(255);

ALTER TABLE users
ADD phone_number varchar(255);

ALTER TABLE users
MODIFY phone_number bigint;

ALTER TABLE addresses
ADD country varchar(255);


-- Join statements
SELECT * FROM boxes
INNER JOIN packages ON boxes.id = packages.boxID;

SELECT packages.value FROM packages
LEFT JOIN shipments ON packages.id = shipments.packageID;

SELECT vehicles.tracking_number FROM vehicles
RIGHT JOIN vehicle_types ON vehicle_types.id = vehicles.vehicle_typeID;

SELECT * FROM profiles
CROSS JOIN users;

SELECT addresses.city FROM addresses
CROSS JOIN routes;


-- Join all tables
SELECT
shipments.id AS 'Shipment ID',
users.id AS "User ID",
discounts.name AS "Discount name",
discounts.rate AS "Discount rate",
profiles.id AS "Recipient ID",
profiles.name AS "Recipient name",
profiles.phone_number AS "Recipient phone #",
routes.id AS "Route ID",
routes.distance AS "Distance",
addresses.id AS "Destination ID",
addresses.street AS "Street",
addresses.city AS "City",
addresses.zipcode AS "Zipcode",
packages.id AS 'Package ID',
packages.weight AS 'Package weight',
packages.fragility AS 'Package fragility',
packages.value AS 'Package value',
packages.cost AS 'Package cost',
boxes.length AS 'Box lenght',
boxes.width AS 'Box width',
boxes.height AS 'Box height',
insurance.name AS 'Insurance',
insurance.price_rate AS 'Insurance rate',
shipments.vehicleID AS 'Vehicle ID',
vehicles.tracking_number AS 'Vehicle tracking number',
vehicle_types.name AS 'Vehicle type name',
vehicle_types.cost_rate AS 'Vehicle type cost rate',
vehicle_types.weight_capacity AS 'Vehicle weight capacity',
vehicle_types.space_capacity AS 'Vehicle space capacity',
shipments.priority AS "Priority",
shipments.price AS "Total price",
shipment_status.delivered AS 'Delivered status'
FROM
shipments
JOIN users ON shipments.senderID = users.id
JOIN user_has_discount ON users.id = user_has_discount.userID
JOIN discounts ON user_has_discount.discountID = discounts.ID
JOIN profiles ON shipments.recipientID = profiles.ID
JOIN routes ON shipments.routeID = routes.id
JOIN addresses ON routes.to_addressID = addresses.id
JOIN packages ON shipments.packageID = packages.id
JOIN boxes ON packages.boxID = boxes.id
JOIN insurance ON shipments.insuranceID = insurance.id
JOIN vehicles ON shipments.vehicleID = vehicles.id
JOIN vehicle_types ON vehicles.vehicle_typeID = vehicle_types.id
JOIN shipment_status ON shipments.id = shipment_status.shipment_id;






-- Insertion Statements
INSERT INTO address (street, city, zipcode)
VALUES ('123 Street st', 'Cityville', 11111);
		
INSERT INTO address (street, city, zipcode)
VALUES ('321 Road rt', 'Hometown', 22222);

INSERT INTO box (length, width, height)
VALUES (5, 5, 5),
		(8, 8, 8);

INSERT INTO discount (name, rate)
VALUES ('Veteran', .10);

INSERT INTO insurance (name, base_cost, price_rate)
VALUES ('Basic Insurance', .3, .015);

INSERT INTO profile (name, phone_number, addressID)
VALUES ('Blake', '123456789', 1);

INSERT INTO profile (name, phone_number, addressID)
VALUES ('Kevin', '987654321', 2);

INSERT INTO user (profileID, password)
VALUES (1, 'myPassword');

INSERT INTO vehicle_type (name, cost_rate, weight_capacity, space_capacity)
VALUES ('Standard Delivery Car', 2.0, 150, 65000);

INSERT INTO vehicle (vehicle_typeID, tracking_number)
VALUES (1, '01-123456');

INSERT INTO package (boxID, weight, fragility, value, cost)
VALUES (1, 15, 1, 25, 7.3);

INSERT INTO route (distance, price, from_addressID, to_addressID)
VALUES (11122, 5.5, 1, 2);

INSERT INTO shipment (senderID, recipientID, packageID, insuranceID, routeID, vehicleID, priority, price)
VALUES (1, 2, 1, 1, 1, 1, 0, 18);

-- Update statements
UPDATE address
SET street = '124 Street st'
WHERE AddressID = 1;

UPDATE address
SET city = 'Bigtown', zipcode = 22233
WHERE AddressID = 2;

UPDATE profile
SET name = 'Blake Franklin'
WHERE id = 1;

UPDATE profile
SET name = 'Kevin Burns'
WHERE id = 2;

UPDATE profile
SET phone_number = '5555555555'
WHERE id = 2;

UPDATE box
SET length = 8, width = 8
WHERE id = 1;

UPDATE discount
SET rate = .15
WHERE id = 1;

UPDATE user
SET password = 'm0reS3cure!'
WHERE id = 1;

UPDATE vehicle_type
SET cost_rate = 1.5
WHERE id = 1;

UPDATE vehicle_type
SET weight_capacity = 200
WHERE id = 1;

-- Deletion statements
DELETE FROM shipment
WHERE vehicleID = 1;

DELETE FROM vehicle
WHERE vehicle_typeID = 1;

DELETE FROM vehicle_type
WHERE id = 1;

DELETE FROM package
WHERE boxID = 1;

DELETE FROM box
WHERE id = 1;

DELETE FROM box;

DELETE FROM insurance
WHERE base_cost = .3;

DELETE FROM discount
WHERE rate = .10;

DELETE FROM user;

DELETE FROM profile
WHERE id = 1;

-- Alter table statements
ALTER TABLE profile
DROP COLUMN phone_number;

ALTER TABLE user
ADD email varchar(255);

ALTER TABLE user
ADD phone_number varchar(255);

ALTER TABLE user
MODIFY phone_number bigint;

ALTER TABLE address
ADD country varchar(255);


-- Join statements
SELECT * FROM box
INNER JOIN package ON box.id = package.boxID;

SELECT package.value FROM package
LEFT JOIN shipment ON package.id = shipment.packageID;

SELECT vehicle.tracking_number FROM vehicle
RIGHT JOIN vehicle_type ON vehicle_type.id = vehicle.vehicle_typeID;

SELECT * FROM profile
CROSS JOIN user;

SELECT address.city FROM address
CROSS JOIN route;


-- Join all tables
SELECT address.street, address.city, address.zipcode FROM address
CROSS JOIN (
	SELECT box.length, box.width, box.height FROM box
    CROSS JOIN (
		SELECT discount.name, discount.rate FROM discount
		CROSS JOIN (
			SELECT insurance.name, insurance.base_cost, insurance.price_rate FROM insurance
			CROSS JOIN (
				SELECT package.boxID, package.weight, package.fragility, package.value, package.cost FROM package
				CROSS JOIN (
					SELECT profile.name, profile.phone_number, profile.addressID FROM profile
					CROSS JOIN (
						SELECT route.distance, route.price, route.from_addressID, route.to_addressID FROM route
						CROSS JOIN (
							SELECT shipment.senderID, shipment.recipientID, shipment.packageID, shipment.insuranceID, shipment.routeID, shipment.vehicleID, shipment.priority, shipment.price FROM shipment
							CROSS JOIN (
								SELECT shipment_status.delivered, shipment_status.shipment_id FROM shipment_status
								CROSS JOIN (
									SELECT user.profileID, user.password FROM user
									CROSS JOIN (
										SELECT user_has_discount.userID, user_has_discount.discountID FROM user_has_discount
										CROSS JOIN (
											SELECT vehicle.vehicle_typeID, vehicle.tracking_number FROM vehicle
											CROSS JOIN vehicle_type
										) AS VT
									) AS V
								) AS U
							) AS SS
						) AS S
					) AS R
				) AS P
			) AS PA
		) AS I
	) AS D
) AS B






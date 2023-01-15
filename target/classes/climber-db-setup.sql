START TRANSACTION;

-- DROP TABLE IF EXISTS climber;

CREATE TABLE climber(
	climber_id serial,
	climber_name VARCHAR(50) NOT NULL,
	is_injured boolean DEFAULT false,
	CONSTRAINT PK_climber PRIMARY KEY (climber_id)
);
CREATE TABLE climber_availability(
	day VARCHAR(10),
	climber_id int,
	CONSTRAINT PK_climber_availability PRIMARY KEY (day, climber_id),
	CONSTRAINT FK_climber_availability FOREIGN KEY (climber_id) REFERENCES climber,
	CONSTRAINT CK_day CHECK (day IN ('sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday'))
);
COMMIT;

SELECT * FROM climber;
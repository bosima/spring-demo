CREATE TABLE t_product (
	id BIGINT NULL AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	price BIGINT NOT NULL,
	`desc` varchar(255) NULL,
	create_time TIMESTAMP NOT NULL,
	update_time TIMESTAMP NOT NULL,
	CONSTRAINT t_product_PK PRIMARY KEY (Id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

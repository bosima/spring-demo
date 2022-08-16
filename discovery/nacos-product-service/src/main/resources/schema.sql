create TABLE t_product (
id bigint not null AUTO_INCREMENT,
name varchar(100) not null,
desc nvarchar(255),
price bigint not null,
create_time Timestamp not null,
update_time Timestamp not null,
primary key(id)
);

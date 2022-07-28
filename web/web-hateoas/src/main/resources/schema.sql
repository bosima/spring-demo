CREATE TABLE t_product (
    id bigint not null auto_increment,
    name varchar(100) not null,
    price bigint not null,
    create_time timestamp not null,
    update_time timestamp not null,
    primary key(id)
);
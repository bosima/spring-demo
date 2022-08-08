CREATE TABLE t_product (
    id bigint not null auto_increment,
    name varchar(100) not null,
    price bigint not null,
    create_time timestamp not null,
    update_time timestamp not null,
    primary key(id)
);

create table t_order (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    customer varchar(255),
    state integer not null,
    primary key (id)
);

create table t_order_product (
    order_id bigint not null,
    product_id bigint not null
);
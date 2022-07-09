create table t_product (
    id bigint not null auto_increment,
    name varchar(60),
    desc varchar(255),
    price bigint not null,
    create_time timestamp,
    update_time timestamp,
    primary key(id)
);
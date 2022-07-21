create table t_product (
    id bigint not null auto_increment,
    name varchar(60),
    description varchar(255),
    add_time timestamp,
    update_time timestamp,
    primary key(id)
);
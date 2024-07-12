DROP TABLE IF EXISTS t_order_detail;
DROP TABLE IF EXISTS t_order;
DROP TABLE IF EXISTS t_product;
DROP TABLE IF EXISTS employee;

create table if not exists t_product  (
    id varchar(100) primary key,
    name varchar(100),
    price integer,
    stock integer
);
create table if not exists t_order (
    id varchar(100) primary key,
    order_date_time timestamp,
    customer_name varchar(100),
    employee_name varchar(100)
);
--create table if not exists t_order_detail (
--    foreign key (order_id) references t_order(id),
--    foreign key (product_id) references t_product(id),
--    quantity integer
--);

create table if not exists employee (
    id varchar(100) primary key,
    name varchar(100)
);
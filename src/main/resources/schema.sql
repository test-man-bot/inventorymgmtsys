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
    employee_name varchar(100),
    payment_method varchar(100)
);
create table if not exists t_order_detail (
    order_id varchar(100),
    product_id varchar(100),
    quantity integer,
    foreign key (order_id) references t_order(id),
    foreign key (product_id) references t_product(id)
);

create table if not exists employee (
    employeeid varchar(100) primary key,
    employeename varchar(100)
);
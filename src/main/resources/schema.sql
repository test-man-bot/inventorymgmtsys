DROP TABLE IF EXISTS t_order_detail;
DROP TABLE IF EXISTS t_order;
DROP TABLE IF EXISTS t_product;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS t_user;
DROP ALIAS IF EXISTS gen_random_uuid;
CREATE ALIAS gen_random_uuid AS '
import java.util.UUID;
@CODE
java.util.UUID genRandomUuid() throws Exception {
	return UUID.randomUUID();
}
';

create table if not exists t_product  (
    id uuid DEFAULT gen_random_uuid() primary key,
    name varchar(100),
    price integer,
    stock integer
);
create table if not exists t_order (
    orderid varchar(100) primary key,
    order_date_time timestamp,
    customer_name varchar(100),
    employee_name varchar(100),
    payment_method varchar(100)
);
create table if not exists t_order_detail (
    order_id varchar(100),
    product_id varchar(100),
    quantity integer,
    foreign key (order_id) references t_order(orderid),
    foreign key (product_id) references t_product(id),
    primary key (order_id, product_id)
);

create table if not exists employee (
    employeeid uuid DEFAULT gen_random_uuid() primary key,
    employeename varchar(100),
    phone varchar(25),
    emailaddress varchar(100)
);


create table if not exists t_user (
    id uuid DEFAULT gen_random_uuid() primary key,
    userName varchar(100) UNIQUE,
    emailAddress varchar(100) UNIQUE,
    address varchar(100),
    phone varchar(100),
    password varchar(100),
    enabled BOOLEAN
);

create table if not exists authorities (
    id INT AUTO_INCREMENT primary key,
    username varchar(100) NOT NULL,
    authority varchar(100) NOT NULL,
    FOREIGN KEY (username) REFERENCES t_user(username)
);

create schema if not exists setronica;
create table if not exists PRODUCT (
    id  BIGSERIAL not null,
    name VARCHAR(150),
    description VARCHAR(3000),
    price NUMERIC(19, 2),
    primary key (id));

insert into PRODUCT (name,description,price)
    values ('Хлеб','Свежий, вкусный 300 гр.',25.00),
       ('Молоко','Домашнее 1 л.',80.00),
       ('Чай травяной','Ароматный с мелисой и чебрецом 50 г.',75.00);
drop database flowershopdb;
drop user hdang;
create user hdang with password 'secret';
create database flowershopdb with template=template0 owner=hdang;
\connect flowershopdb;
alter default privileges grant all on tables to hdang;

create table users (
    user_id  serial primary key,
    user_name varchar(30) default 'no_name',
    email varchar(30) not null,
    password text not null
);

create table categories (
    category_id serial primary key,
    category varchar(30) not null
);

create table flowers(
    flower_id serial primary key,
    name varchar(30) not null,
    image_url text not null,
    price numeric not null,
    quantity integer default 1,

    category_id int not null,

    foreign key (category_id) references categories(category_id)
);


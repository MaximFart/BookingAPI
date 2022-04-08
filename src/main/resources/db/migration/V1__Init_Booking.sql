create table tours (
    id serial primary key,
    price int8 not null,
    route varchar(255) not null,
    start date not null,
    finish date not null
);

create table roles (
    id serial primary key,
    role varchar(255) not null
);

create table users (
    login varchar(255) primary key,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    role_id int8 references roles(id)
)

create table guides (
    login varchar(255) primary key,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    position varchar(255) not null,
    role_id int8 references roles(id)
);

create table bookings (
    id serial primary key,
    tour_id int8 references tours(id),
    guide_id int8 references guides(id),
    user_id int8 references users(id)
)
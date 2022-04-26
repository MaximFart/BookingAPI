drop table if exists booking, guides, tours, users, roles;

create table roles (
    id serial not null primary key,
    name varchar(255) not null
);

create table users (
  	id serial not null primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    role_id integer not null,
    foreign key (role_id) references roles(id) on delete cascade
);

alter table users add constraint unique_username unique (username);

create table guides (
  	id serial not null primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
);

create table tours (
    id serial not null primary key,
    price integer not null,
    route varchar(255) not null,
    start date not null,
    finish date not null
);

create table bookings (
    id serial not null primary key,
    tour_id integer,
    user_id integer,
    guide_id integer,
    foreign key (tour_id) references tours(id) on delete cascade,
    foreign key (user_id) references users(id) on delete cascade,
    foreign key (guide_id) references guides(id) on delete cascade
);
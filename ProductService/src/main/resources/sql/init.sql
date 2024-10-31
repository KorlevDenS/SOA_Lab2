create table product (
    id serial primary key,
    name varchar,

    coordinates_x integer,
    coordinates_y bigint,

    creation_date date,
    price double precision,
    part_number varchar unique,
    manufacture_cost integer,
    unit_of_measure varchar,

    person_name varchar,
    person_birthday timestamp with time zone,
    person_height float,
    person_passport_id varchar,

    person_location_x bigint,
    person_location_y bigint,
    person_location_name varchar
);

delete from product where creation_date IS NULL;
create table if not exists CATEGORY (
    "ID" integer not null primary key,
    "NAME" varchar(255) not null,
    "DESCRIPTION" text
);

create table if not exists PRODUCT (
    "ID" integer not null primary key,
    "NAME" varchar(255) not null,
    "DESCRIPTION" text,
    "PRICE" decimal(10, 2) not null,
    "AVAILABLE_QUANTITY" double precision not null,
    "CATEGORY_ID" integer,
    foreign key ("CATEGORY_ID") references CATEGORY("ID")
);
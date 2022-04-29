create table contact_person
(
    ID                    numeric (255),
    FIRST_NAME             varchar(255),
    LAST_NAME              varchar(255),
    MOBILE_PHONE_NUMBER   varchar(255),
    TELEPHONE_NUMBER      varchar(255),
    EMAIL                 varchar(255),
    STREET_NAME           varchar(255),
    STREET_NUMBER         varchar(255),
    FK_POSTALCODE_CITY_ID integer,
    foreign key (FK_POSTALCODE_CITY_ID) references postalcode_city (id),
    primary key (id)
);
create sequence contact_person_seq start with 1 increment by 1;
ALTER TABLE contact_person ALTER id SET DEFAULT NEXTVAL('contact_person_seq');



create table parking_lot
(
    ID                    numeric (255),
    NAME                  varchar(255),
    CATEGORY              varchar(255),
    MAX_CAPACITY          integer,
    STREET_NAME           varchar(255),
    STREET_NUMBER         varchar(255),
    PRICE_PER_HOUR        numeric ,
    FK_POSTALCODE_CITY_ID integer,
    FK_CONTACT_PERSON_ID  numeric,
    foreign key (FK_CONTACT_PERSON_ID ) references contact_person (id),
    foreign key (FK_POSTALCODE_CITY_ID) references postalcode_city (id),
    primary key (id)
);
create sequence parking_lot_seq start with 1 increment by 1;
ALTER TABLE parking_lot ALTER id SET DEFAULT NEXTVAL('parking_lot_seq');




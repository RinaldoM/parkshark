create table POSTALCODE_CITY
(
    ID      bigint      NOT NULL,
    ZIPCODE varchar(255) NOT NULL,
    CITY    varchar(255) NOT NULL,
    CONSTRAINT PK_POSTALCODE_CITY primary key (ID)

);
create sequence postalcode_city_seq start with 1 increment by 1;

create table LICENSE_PLATE
(
    ID      bigint      NOT NULL,
    NUMBER varchar(255) NOT NULL,
    ISSUING_COUNTRY    varchar(255) NOT NULL,
    CONSTRAINT PK_LICENSE_PLATE primary key (ID)

);
create sequence license_plate_seq start with 1 increment by 1;

create table MEMBER
(
    ID                    varchar(255) NOT NULL,
    FIRSTNAME             varchar(255) NOT NULL,
    LASTNAME              varchar(255) NOT NULL,
    STREET_NAME           varchar(255) NOT NULL,
    STREET_NUMBER         varchar(255) NOT NULL,
    FK_POSTALCODE_CITY_ID integer,
    PHONE_NUMBER          varchar(255) NOT NULL,
    EMAIL                 varchar(255) NOT NULL,
    FK_LICENSE_PLATE_ID   integer,
    REGISTRATION_DATE     timestamp,
    CONSTRAINT FK_POSTALCODE_CITY_ID foreign key (FK_POSTALCODE_CITY_ID) references POSTALCODE_CITY (ID),
    CONSTRAINT FK_LICENSE_PLATE_ID foreign key (FK_LICENSE_PLATE_ID) references LICENSE_PLATE (ID),
    CONSTRAINT PK_MEMBER primary key (ID)
);


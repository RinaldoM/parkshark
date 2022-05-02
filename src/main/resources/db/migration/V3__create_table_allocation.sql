create table allocation
(
    ID                          NUMERIC         NOT NULL,
    FK_MEMBER_ID                VARCHAR(255)    NOT NULL,
    LICENSE_PLATE_NUMBER        VARCHAR(255)    NOT NULL,
    FK_PARKING_LOT_ID           NUMERIC         NOT NULL,
    START_DATE_TIME             TIMESTAMP       NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (FK_MEMBER_ID) references MEMBER (id),
    FOREIGN KEY (FK_PARKING_LOT_ID) references PARKING_LOT (id)
);

create sequence allocation_seq start with 1 increment by 1;
ALTER TABLE allocation ALTER ID SET DEFAULT NEXTVAL('allocation_seq');

ALTER TABLE parking_lot
    ADD COLUMN NUMBER_OF_ALLOCATED_SPOTS integer;
create table membership_level
(
    id                   numeric,
    NAME                 varchar(255),
    monthly_cost         numeric,
    allocation_reduction numeric,
    max_allocation_time  numeric,
    primary key (id)
);
create sequence membership_level_seq start with 1 increment by 1;

ALTER TABLE member
    add FK_MEMBERSHIP_LEVEL_ID numeric ;
Alter table member
    add constraint FK_MEMBERSHIP_LEVEL_ID foreign key (FK_MEMBERSHIP_LEVEL_ID) references membership_level (id);
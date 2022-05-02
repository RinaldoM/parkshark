insert into membership_level(id, NAME, monthly_cost, allocation_reduction, max_allocation_time)
values (nextVal('membership_level_seq'), 'BRONZE', 0, 0, 4);
insert into membership_level(id, NAME, monthly_cost, allocation_reduction, max_allocation_time)
values (nextVal('membership_level_seq'), 'SILVER', 10, 20, 6);
insert into membership_level(id, NAME, monthly_cost, allocation_reduction, max_allocation_time)
values (nextVal('membership_level_seq'), 'GOLD', 40, 30, 24);


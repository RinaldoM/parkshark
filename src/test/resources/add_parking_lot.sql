insert into postalcode_city (id, zipcode, city)
values (1, '3600', 'Genk');

insert into contact_person(id, first_name, last_name, mobile_phone_number, telephone_number, email, street_name,
                           street_number, fk_postalcode_city_id)
values (1, 'Rinaldo', 'Menichetti', '06546540', '65406540', 'rinaldo@shark.com', 'Keystreet', '45', 1);

insert into parking_lot(id, name, category, max_capacity, street_name, street_number, price_per_hour,
                        fk_postalcode_city_id, fk_contact_person_id, number_of_allocated_spots)
values (1, 'Astridplein', 'ABOVE_GROUND_BUILDING', 500, 'Keystreet', '45', 2, 1, 1, 0);
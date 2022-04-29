insert into postalcode_city (id,zipcode,city) values (1,'3600','Genk');
insert into license_plate(id, number, issuing_country) values (1,'676','LXA');
insert into member (id, firstname, lastname, street_name,street_number, fk_postalcode_city_id, phone_number, email, fk_license_plate_id, registration_date)
values ('ABC', 'Baby', 'Shark', 'ParkStreet','1B', 1,'0489656566', 'shark@baby.com', 1,'2016-06-22 19:10:25-07')
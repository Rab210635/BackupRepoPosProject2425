insert into author(id, first_name, last_name, email, penname)
values (nextval('author_seq'),'Thomas', 'Tschernko','BitteNurSachenBewertenDieWIrMachenMüssen@gmail.com','Temmy');

insert into addresses_in_authors(author_id, zip, city, street_and_number)
values (currval('author_seq'),'1010','Vienna','Tschernko Sie sind der !beste! ')


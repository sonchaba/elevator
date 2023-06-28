drop table if exists elevator;
create table elevator(
id int primary key,
current_floor int,
 state varchar(15),
 direction varchar(15)
);
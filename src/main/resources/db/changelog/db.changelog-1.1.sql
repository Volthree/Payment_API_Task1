--liquibase formatted sql

--changeset vladislav:1
insert into users (name, usd, role, pass)
values
('Ivan', 8, 'USER', '{noop}qw'),
('Petr', 8, 'USER', '{noop}we'),
('Bob', 8, 'USER', '{noop}er'),
('Dan', 8, 'USER', '{noop}rt'),
('Rob', 8, 'USER', '{noop}as'),
('John', 8, 'USER', '{noop}sd'),
('qw', 8, 'USER', '{noop}qw');

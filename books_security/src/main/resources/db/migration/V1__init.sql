create table books
(
    isbn     varchar(20) primary key,
    title    varchar(30) not null,
    author   varchar(30) not null
);


create table users
(
    id       int primary key auto_increment,
    login    varchar(30) not null,
    password varchar(40) not null,
    unique uniq_login (login)
);

create table permissions
(
    id         int primary key auto_increment,
    permission varchar(30) not null,
    unique uniq_permission (permission)
);

create table user_to_permissions (
                                     user_id int not null,
                                     permission_id int not null,
                                     constraint fk_user_to_permission_user foreign key (user_id) references users(id),
                                     constraint fk_user_to_permission_permission foreign key (permission_id) references permissions(id)
);

create table user_to_books (
                                     user_id int not null,
                                     book_isbn varchar not null,
                                     constraint fk_user_to_books_user foreign key (user_id) references users(id),
                                     constraint fk_user_to_books_book foreign key (book_isbn) references books(isbn)
);


insert into books (isbn, title, author) values
('aaa', 'Flowers for Algernon', 'Daniel Keyes'),
('bbb', 'One Hundred Years of Solitude', 'Gabriel García Márquez');


insert into users (login, password) values
('admin', 'password'),
('user', 'password');

insert into permissions (permission) values
('ADMIN'),
('USER');

insert into user_to_permissions (user_id, permission_id) values
((select id from users where login = 'admin'), (select id from permissions where permission = 'ADMIN')),
((select id from users where login = 'user'), (select id from permissions where permission = 'USER'));

insert into user_to_books (user_id, book_isbn) values
((select id from users where login = 'user'), (select isbn from books where title = 'Flowers for Algernon'));
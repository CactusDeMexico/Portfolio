create table if not exists editor
(
	id_editeur serial not null
		constraint id_editeur
			primary key,
	name varchar not null
);

alter table editor owner to postgres;

create table if not exists type_book
(
	id_type serial not null
		constraint id_type
			primary key,
	book_type varchar not null
);

alter table type_book owner to postgres;

create table if not exists author
(
	id_author serial not null
		constraint id_author
			primary key,
	last_name varchar not null,
	first_name varchar not null,
	id_editeur integer not null
);

alter table author owner to postgres;

create table if not exists book
(
	id_book serial not null
		constraint id_book
			primary key,
	id_type integer not null,
	id_editeur integer not null,
	title varchar not null,
	summary varchar not null,
	url_image varchar not null,
	isbn varchar not null,
	purchase_date date not null,
	price numeric not null,
	creation_date date not null,
	update_date date not null
);

alter table book owner to postgres;

create table if not exists book_author
(
	id_author integer not null
		constraint book__author___fk
			references author,
	id_book integer not null
		constraint book__author___fk2
			references book
		constraint fklna7k18qnoc76p16i41g7514l
			references author,
	id_editeur integer not null
		constraint fkrxljtra98sosfply0k8b336ob
			references book
);

alter table book_author owner to postgres;

create table if not exists role
(
	id_role integer not null
		constraint role_pkey
			primary key,
	role varchar(255)
);

alter table role owner to postgres;

create table if not exists user_account
(
	id_user serial not null
		constraint user1_pk
			primary key,
	first_name varchar not null,
	last_name varchar not null,
	email varchar not null,
	password varchar not null,
	active integer not null,
	creation_date date not null,
	update_date date not null
);

alter table user_account owner to postgres;

create table if not exists user_role
(
	id_user integer not null
		constraint fktkh161xmudgywjdwoawesdk9i
			references user_account
		constraint user1_user_role_fk
			references user_account,
	id_role integer not null
		constraint fka68196081fvovjhkek5m97n3y
			references role
		constraint fklqrhjak9uiiytups744gq095j
			references role,
	constraint user_role_pkey
		primary key (id_user, id_role)
);

alter table user_role owner to postgres;

create table if not exists borrowed
(
	id_borrowed integer not null
		constraint id_borrowed
			primary key,
	id_book integer not null
		constraint book__archive___fk
			references book,
	id_user integer not null
		constraint user__archive___fk
			references user_account,
	borrowing_date date not null,
	return_date date not null
);

alter table borrowed owner to postgres;

create table if not exists borrow
(
	id_borrow integer not null
		constraint id_borrow
			primary key,
	id_book integer not null
		constraint book__archive___fk
			references book,
	id_user integer not null
		constraint user__archive___fk
			references user_account,
	is_loan boolean not null,
	borrowing_date date not null,
	return_date date not null,
	is_extended boolean
);

alter table borrow owner to postgres;

create table if not exists persistent_logins
(
	username varchar(64) not null,
	series varchar(64) not null
		constraint persistent_logins_pkey
			primary key,
	token varchar(64) not null,
	last_used timestamp not null
);

alter table persistent_logins owner to postgres;

create table if not exists reservation
(
	id_reservation integer not null
		constraint reservation_pkey
			primary key,
	id_book integer,
	id_user integer,
	title varchar(255)
);

alter table reservation owner to postgres;


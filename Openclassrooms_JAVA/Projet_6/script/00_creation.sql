create table if not exists role
(
  idrole integer not null
    constraint role_pkey
      primary key,
  role varchar(255)
);

alter table role owner to postgres;

create table if not exists user1
(
  iduser serial not null
    constraint user1_pk
      primary key,
  name varchar not null,
  lastname varchar not null,
  email varchar not null,
  password varchar not null,
  active integer not null
);

alter table user1 owner to postgres;

create table if not exists user_role
(
  iduser integer not null
    constraint fktkh161xmudgywjdwoawesdk9i
      references user1
    constraint user1_user_role_fk
      references user1,
  idrole integer not null
    constraint fka68196081fvovjhkek5m97n3y
      references role
    constraint fklqrhjak9uiiytups744gq095j
      references role,
  constraint user_role_pkey
    primary key (iduser, idrole)
);

alter table user_role owner to postgres;

create table if not exists publication
(
  idpublication serial not null
    constraint publication_pk
      primary key,
  name varchar not null,
  iduser integer not null
    constraint user_publication_fk
      references user1,
  creationdate date not null,
  updatedate date not null
);

alter table publication owner to postgres;

create table if not exists commentaire
(
  idcommentaire serial not null
    constraint commentaire_pk
      primary key,
  iduser integer not null,
  texte varchar not null,
  idpublication integer not null,
  hiddencom boolean
);

alter table commentaire owner to postgres;

create table if not exists topo
(
  idtopo serial not null
    constraint topo_pk
      primary key,
  lieu varchar not null,
  hiddentopo boolean
);

alter table topo owner to postgres;

create table if not exists spot
(
  idspot serial not null
    constraint spot_pk
      primary key,
  idtopo integer not null
    constraint topo_spot_fk
      references topo,
  idpublication integer not null
    constraint publication_spot_fk
      references publication,
  nom varchar not null,
  description varchar not null,
  lien varchar not null
);

alter table spot owner to postgres;

create table if not exists secteur
(
  idsecteur serial not null
    constraint secteur_pk
      primary key,
  idspot integer not null
    constraint spot_secteur_fk
      references spot,
  idpublication integer not null
    constraint publication_secteur_fk
      references publication,
  nom varchar not null,
  lieu varchar not null,
  type varchar not null,
  lien varchar not null,
  hauteur integer not null
);

alter table secteur owner to postgres;

create table if not exists voie
(
  idvoie serial not null
    constraint voie_pk
      primary key,
  idsecteur integer not null
    constraint secteur_voie_fk
      references secteur,
  nom varchar not null,
  equipees boolean not null,
  relai varchar,
  cotation varchar not null
);

alter table voie owner to postgres;

create table if not exists rent
(
  idtopo integer not null
    constraint rent_pk
      primary key
    constraint topo_rent_fk
      references topo,
  isloan boolean not null,
  borrowingdate date not null,
  return date not null,
  iduser integer not null
    constraint user_rent_fk
      references user1,
  isborrow boolean,
  isseen boolean
);

alter table rent owner to postgres;

create table if not exists proprietaire
(
  idtopo integer not null
    constraint propritaire_pk
      primary key
    constraint fkkuye5m7ksslk8dem952n007ga
      references topo
    constraint topo_propri_taire_fk
      references topo,
  iduser integer not null
    constraint fkmhgj4faad34vfs81la4b2lo0h
      references user1
    constraint user_propri_taire_fk
      references user1
);

alter table proprietaire owner to postgres;

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

INSERT INTO role (idrole,role) values ('1','ADMIN');
alter table commentaire
  add constraint user__commentaire___fk
    foreign key (iduser) references user1;
alter table commentaire
  add constraint publication__commentaire___fk
    foreign key (idpublication) references publication;

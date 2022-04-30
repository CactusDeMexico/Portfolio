create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create table blocked_mail
(
  id_blocked_mail integer not null
    constraint blocked_mail_pkey
      primary key,
  cause           varchar(255),
  email           varchar(255)
);

alter table blocked_mail
  owner to postgres;

create table material
(
  id_material integer not null
    constraint material_pkey
      primary key,
  name        varchar(255),
  opaque      boolean,
  thickness   integer
);

alter table material
  owner to postgres;

create table meeting
(
  id_meeting        integer not null
    constraint meeting_pkey
      primary key,
  closed            boolean,
  date_sended       timestamp,
  invitation_sended boolean,
  meeting_validate  boolean,
  purpose           varchar(255),
  date_meeting      timestamp,
  email             varchar(255)
);

alter table meeting
  owner to postgres;

create table project
(
  id_project  integer not null
    constraint project_pkey
      primary key,
  description varchar(255),
  hidden      boolean,
  name        varchar(255),
  surface     integer,
  type        varchar(255),
  url_img     varchar(255)
);

alter table project
  owner to postgres;

create table quote
(
  id_quote    integer not null
    constraint quote_pkey
      primary key,
  description varchar(255),
  name        varchar(255),
  surface     integer,
  type        varchar(255),
  email       varchar(255),
  url_img     varchar(255)
);

alter table quote
  owner to postgres;

create table user_account
(
  id_user    integer not null
    constraint user_account_pkey
      primary key,
  email      varchar(255),
  first_name varchar(255),
  hidden     boolean,
  last_name  varchar(255),
  password   varchar(255),
  active     integer
);

alter table user_account
  owner to postgres;

create table project_material
(
  id_project  integer not null
    constraint fk2h6f3k2tr05dows4lrx8then4
      references project,
  id_material integer not null
    constraint fk4wqf5ncvq1ausmmp6apygpwx7
      references material,
  constraint project_material_pkey
    primary key (id_project, id_material)
);

alter table project_material
  owner to postgres;

create table role
(
  id_role integer not null
    constraint role_pkey
      primary key,
  role    varchar(255)
);

alter table role
  owner to postgres;

create table user_role
(
  id_user integer not null
    constraint fkmsj2itk680u3kf123ap4u35ew
      references user_account,
  id_role integer not null
    constraint fk2aam9nt2tv8vcfymi3jo9c314
      references role,
  constraint user_role_pkey
    primary key (id_user, id_role)
);

alter table user_role
  owner to postgres;

create table persistent_logins
(
  username  varchar(64) not null,
  series    varchar(64) not null
    constraint persistent_logins_pkey
      primary key,
  token     varchar(64) not null,
  last_used timestamp   not null
);

alter table persistent_logins
  owner to postgres;


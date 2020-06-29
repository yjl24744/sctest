drop table if exists role;
create table role (
 id bigint(20) not null auto_increment,
 name varchar(255) collate utf8_bin not null,
 primary key (id)
) engine=innodb auto_increment=3 default charset=utf8 collate=utf8_bin;

drop table if exists user;
create table user (
 id bigint(20) not null auto_increment,
 password varchar(255) collate utf8_bin default null,
 username varchar(255) collate utf8_bin not null,
 primary key (id),
 unique key UK_sb8bbouer5wak8vyiiy4pf2bx (username)
) engine=innodb auto_increment=3 default charset=utf8 collate=utf8_bin;

drop table if exists user_role;
create table user_role (
 user_id bigint(20) not null,
 role_id bigint(20) not null,
 key FKa68196081fvovjhkek5m97n3y (role_id),
 key FK859n2jvi8ivhui0rl0esws6o (user_id),
 constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id),
 constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id)
) engine=innodb default charset=utf8 collate=utf8_bin;

INSERT INTO user (id, username, password) VALUES (1, ''yang'', ''123456'');
INSERT INTO user (id, username, password)  VALUES (2, ''admin'', ''123456'');

INSERT INTO role (id, name) VALUES (1, ''ROLE_USER'');
INSERT INTO role (id, name) VALUES (2, ''ROLE_ADMIN'');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
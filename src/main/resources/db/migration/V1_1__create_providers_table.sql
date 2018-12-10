CREATE TABLE IF NOT EXISTS providers (
  id int PRIMARY KEY AUTO_INCREMENT,
  name varchar(100) not null,
  address varchar(100) not null,
  created_at timestamp not null
) DEFAULT CHARSET=UTF8;
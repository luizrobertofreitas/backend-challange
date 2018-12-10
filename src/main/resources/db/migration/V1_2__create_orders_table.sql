CREATE TABLE IF NOT EXISTS orders (
  id int PRIMARY KEY AUTO_INCREMENT,
  address varchar(100) not null,
  confirmed_at timestamp not null,
  status varchar(80) not null
) DEFAULT CHARSET=UTF8;
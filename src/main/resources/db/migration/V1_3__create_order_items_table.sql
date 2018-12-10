CREATE TABLE IF NOT EXISTS order_items (
  id int PRIMARY KEY AUTO_INCREMENT,
  description varchar(20) not null,
  price decimal(10,2) not null,
  quantity int not null,
  order_id int not null,
  constraint order_items_order_id_fk foreign key (order_id) references orders(id)
) DEFAULT CHARSET=UTF8;
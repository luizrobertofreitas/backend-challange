CREATE TABLE IF NOT EXISTS order_payments (
  id int PRIMARY KEY AUTO_INCREMENT,
  credit_card_number varchar(20) not null,
  status varchar(30) not null,
  order_id int not null,
  paid_at timestamp,
  constraint payments_order_id foreign key(order_id) references orders(id)
) DEFAULT CHARSET=UTF8;
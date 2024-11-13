show databases;

use om;

drop table profiles;
create table profiles
(prof_id int not null,
prof_name varchar(30) not null,
descr varchar(100),
constraint pk_profiles primary key (prof_id)
);

insert into	profiles values (21, 'admin','Administrator');


drop table privs;
create table privs
(priv_id int not null,
priv_name varchar(30) not null,
descr varchar(100),
constraint pk_privs primary key (priv_id)
);

insert into	privs values (1, 'product_management','add, update, delete products in products table');
insert into	privs values (2, 'order_management','add, update, delete orders in orders table');
insert into	privs values (3, 'user_management','add, update, delete users in users table');
insert into	privs values (4, 'profile_management','add, update, delete profiles in profiles table');
insert into	privs values (5, 'cart_management','add, update, delete items in cart table');
insert into	privs values (6, 'stock_management','add, update, delete product quantity in stocks table');
insert into	privs values (7, 'session_management','add, update, delete sessions in sessions table');
insert into	privs values (8, 'view_products','select products from products table');
insert into	privs values (9, 'view_orders','select orders from orders table');
insert into	privs values (10, 'bill_management','order calculation for total bill');
insert into	privs values (11, 'view_bills','only see the bills for orders');
commit;

select * from privs;
delete from privs;

drop table profprivs;
create table profprivs
(prof_id int not null,
 priv_id int not null,
 constraint pk_profprivs primary key (prof_id,priv_id),
 constraint fk_profprivs_prof_id foreign key (prof_id) references profiles (prof_id),
 constraint fk_profprivs_priv_id foreign key (priv_id) references privs (priv_id)
);

insert into	profprivs values (21, 1);
insert into	profprivs values (21, 2);
insert into	profprivs values (21, 3);
insert into	profprivs values (21, 4);
insert into	profprivs values (21, 5);
insert into	profprivs values (21, 6);
insert into	profprivs values (21, 7);
insert into	profprivs values (21, 8);
insert into	profprivs values (21, 9);
insert into	profprivs values (21, 10);
insert into	profprivs values (21, 11);

select * from profprivs;


drop table users;
create table users
(user_id int not null,
user_short_name varchar(20) not null,
user_full_name varchar(40) not null,
email varchar(40) not null,
phone_number varchar(15) not null,
login_id varchar(25) not null,
passwd varchar(25) not null,
prof_id int not null,
status varchar(100),
failed_login_attempts int,
create_date timestamp,
update_date timestamp,
constraint pk_users primary key (user_id),
constraint fk_users_prof_id foreign key (prof_id) references profiles (prof_id));

insert into users values (1, 'pradipta', 'pradipta ranjan khuntia', 'bablu.mail@gmail.com', '9980524345',
						  'prad3345','admin1234',21,'ACTIVE', 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

select * from users;

create table countries
(country_id int not null,
country_name varchar(20) not null,
constraint pk_countries primary key (country_id)
);

insert into countries values (1, 'India');

create table states
(state_id int not null,
state_name varchar(20) not null,
country_id int not null,
constraint pk_states primary key (state_id),
constraint fk_states_country_id foreign key (country_id) references countries (country_id)
);

insert into states values (1, 'Odisha', 1);

create table district
(dist_id int not null,
dist_name varchar(20) not null,
state_id int not null,
constraint pk_district primary key (dist_id),
constraint fk_district_state_id foreign key (state_id) references states (state_id));

insert into district values (1, 'Cuttack', 1);


drop table address;
create table address
(addr_id int not null,
user_id int not null,
country_id int not null,
state_id int not null,
dist_id int not null,
city_name varchar(40),
area_name varchar(40),
zip_code int not null,
addr_dtl varchar(300),
status varchar(10),
constraint pk_address primary key (addr_id,user_id),
constraint fk_address_user_id foreign key (user_id) references users (user_id),
constraint fk_address_country_id foreign key (country_id) references countries (country_id),
constraint fk_address_state_id foreign key (state_id) references states (state_id),
constraint fk_address_dist_id foreign key (dist_id) references district (dist_id)
);

insert into address values (1, 1, 1, 1, 1, 'Choudwar', 'Kapaleswar', 754051,'ShibaShakti, House No# 201, Tarini Vihar','Active' );

drop table phones;
create table phones
(phone_id int not null,
user_id int not null,
phone_number varchar(15) not null,
status varchar(10),
constraint pk_phones primary key (phone_id),
constraint fk_phones_user_id foreign key (user_id) references users (user_id)
);

insert into phones values (1, 1, '9980524345', 'Active' );

drop table emails;
create table emails
(email_id int not null,
user_id int not null,
email varchar(40) not null,
status varchar(10),
constraint pk_emails primary key (email_id),
constraint fk_emails_user_id foreign key (user_id) references users (user_id)
);

insert into emails values (1, 1, 'bablu.mail@gmail.com', 'Active' );

drop table prodtypes;
create table prodtypes
(prod_type_id int not null,
type_name varchar(40) not null,
prod_parent_type_id int,
constraint pk_prodtypes primary key (prod_type_id),
constraint fk_prodtypes foreign key (prod_parent_type_id) references prodtypes (prod_type_id)
);

insert into prodtypes values (1, 'Medicines', null );
insert into prodtypes values (2, 'General Medicines', 1 );
insert into prodtypes values (3, 'Cardio Medicines', 1 );
insert into prodtypes values (4, 'Pregnecy Medicines', 1 );
insert into prodtypes values (5, 'ENT Medicines', 1 );

select * from prodtypes;

drop table products;
create table products
(product_id int not null,
prod_short_name varchar(40) not null,
prod_name varchar(200),
prod_type_id int not null,
brand_name varchar(40),
prod_form varchar(20),
price_per_qty decimal(10,2),
description varchar(200),
constraint pk_products primary key (product_id),
constraint fk_products_type foreign key (prod_type_id) references prodtypes (prod_type_id)
);

insert into products values (1, 'paracetamol', 'Paracetamol 650 mg', 1, 45.00, 'Medicine for fever and pain' );


drop table cart;
create table cart
(user_id int not null,
product_id int not null,
add_date timestamp,
qty int not null,
price decimal(10,2),
discount decimal(10,2),
checkedout varchar(1) default 'N',
checkout_date timestamp,
constraint pk_cart primary key (user_id,product_id),
constraint fk_cart_user_id foreign key (user_id) references users (user_id),
constraint fk_cart_product_id foreign key (product_id) references products (product_id)
);

drop table prodstock;
create table prodstock
(product_id int not null,
stock_qty int,
update_date timestamp,
constraint fk_productavl foreign key (product_id) references products (product_id)
);

drop table Orders;
create table Orders
(ord_id int not null,
user_id int not null,
ord_date timestamp,
tot_discount decimal(10,2),
tot_amount decimal(10,2),
status varchar(20),
update_date timestamp,
completed_date timestamp,
constraint pk_orders primary key (ord_id),
constraint fk_orders_user_id foreign key (user_id) references users (user_id)
);

drop table Orderdtls;
create table Orderdtls
(ord_id int not null,
product_id int not null,
qty int not null,
price decimal(10,2),
discount decimal(10,2),
status varchar(20),
update_date timestamp,
completed_date timestamp,
constraint fk_Orderdtls foreign key (ord_id) references Orders (ord_id)
);

drop table usession;
create table usession
(session_id int,
user_id int,
start_time timestamp,
end_time timestamp,
status varchar(20),
constraint pk_usession primary key (session_id),
constraint fk_usession_user_id foreign key (user_id) references users (user_id)
);

drop table notifications;
create table notifications
(note_id int,
user_id int,
note varchar(2000),
status varchar(1),
constraint pk_notifications primary key (note_id),
constraint fk_notifications_user_id foreign key (user_id) references users (user_id)
);
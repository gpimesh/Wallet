drop table if exists account CASCADE ;
drop table if exists transactions CASCADE ;
drop table if exists users CASCADE;
drop sequence if exists hibernate_sequence;

create table ACCOUNT (
 					  id long not null primary key,
 					  cust_account_number bigint (19) not null UNIQUE,  
                      balance float (20) not null,
                      account_owner  varchar_ignorecase (25) not null,
                      creation_date timestamp,
                      modified_date timestamp,
                      creation_user varchar_ignorecase (25) not null
);

CREATE INDEX cust_account_number ON ACCOUNT(cust_account_number);

create table USERS (    
						id long not null primary key,
						user_id varchar_ignorecase(25) not null UNIQUE,
                       	first_name varchar (20) not null,
                        last_name varchar (30) not null,
                        email varchar_ignorecase (40) not null,
                        creation_date timestamp,
                        modified_date timestamp,
                        account_no  bigint (19) not null UNIQUE,
                        constraint fk_customer_account foreign key(account_no) references ACCOUNT(cust_account_number)
  
);
CREATE INDEX user_id ON USERS(user_id);
                           
create table TRANSACTIONS (
						id long not null primary key,
						transaction_id bigint (19) UNIQUE,
						account_number bigint (19) not null, 
						amount float,
						creation_date timestamp,
						modified_date timestamp,
						creation_userid varchar_ignorecase(25),
	constraint fk_account_transaction foreign key(account_number) references ACCOUNT(cust_account_number)				
);

CREATE INDEX transaction_id ON TRANSACTIONS(transaction_id);



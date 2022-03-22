create table users
(
first_name varchar(100),
last_name varchar(100),
email varchar(100)
)

insert into users(id,first_name,last_name,email) 
values(1,'Sachin', 'Nemade', 'sachin.nemade@gmail.com');

create table migrationlog 

( 
rowid int not null AUTO_INCREMENT PRIMARY KEY, 
logtype varchar(100), 
logdesc TEXT, 
recorddate datetime ,
Job varchar(200) 
) 

#package 
$mvn package

#Run test profile
java -jar -Dspring.profiles.active=test target/s3tocloudinary-0.0.1-SNAPSHOT.jar


#apis


# Sql script
create table migrationlog 

( 

rowid int not null AUTO_INCREMENT PRIMARY KEY, 

logtype varchar(100), 

logdesc TEXT, 

recorddate datetime,
 
job varchar(100),

) 
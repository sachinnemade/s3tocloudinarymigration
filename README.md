
#package 
$mvn package

#declare envionment variable with default value - migration
cloudinarydestinationfolder: ${DEST_FOLDER:migration}

#Run test profile

java -jar -DDEST_FOLDER=test_migration  target/s3tocloudinary-0.0.1-SNAPSHOT.jar --spring.profiles.active=test 

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
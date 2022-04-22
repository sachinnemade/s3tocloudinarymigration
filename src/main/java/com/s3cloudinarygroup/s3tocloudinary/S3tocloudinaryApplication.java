package com.s3cloudinarygroup.s3tocloudinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableJpaRepositories //Comment while deploy to EB
//@ComponentScan(basePackageClasses = {bucketResource.class,MigrationlogJPARepo.class}) //Comment while deploy to EB
@EntityScan("com.s3cloudinarygroup.s3tocloudinary")
public class S3tocloudinaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3tocloudinaryApplication.class, args);
	}


}

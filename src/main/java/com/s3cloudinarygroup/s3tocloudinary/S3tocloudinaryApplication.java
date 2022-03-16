package com.s3cloudinarygroup.s3tocloudinary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackageClasses = {bucketResource.class,MigrationlogJPARepo.class})
public class S3tocloudinaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3tocloudinaryApplication.class, args);
	}

}

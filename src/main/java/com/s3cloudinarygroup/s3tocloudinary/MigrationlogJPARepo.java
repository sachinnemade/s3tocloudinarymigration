package com.s3cloudinarygroup.s3tocloudinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MigrationlogJPARepo extends  JpaRepository<MigrationlogdbEntity,Integer> {
    public List<MigrationlogdbEntity> findByJob(String job);

}

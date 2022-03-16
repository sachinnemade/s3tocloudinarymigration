package com.s3cloudinarygroup.s3tocloudinary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MigrationlogJPARepo extends  JpaRepository<MigrationlogdbEntity,Integer> {
    public List<MigrationlogdbEntity> findByJob(String job);
}

package com.s3cloudinarygroup.s3tocloudinary;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MigrationlogService implements  iMigrationlogService{

    @Autowired
    private MigrationlogJPARepo migrationlogJPARepo;

    @Override
    public void savelog(String type, String desc, String job) {

        MigrationlogdbEntity migrationlogdbEntity = new MigrationlogdbEntity();
        migrationlogdbEntity.setJob(job);
        migrationlogdbEntity.setLogtype(type);
        migrationlogdbEntity.setLogdesc(desc);
        migrationlogdbEntity.setRecorddate(DateTime.now().toString("yyyy-MM-dd hh:mm:ss"));
        migrationlogJPARepo.save(migrationlogdbEntity);
    }
}

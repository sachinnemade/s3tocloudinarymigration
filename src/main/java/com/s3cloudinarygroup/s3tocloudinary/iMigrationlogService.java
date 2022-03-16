package com.s3cloudinarygroup.s3tocloudinary;

import org.springframework.stereotype.Service;

@Service
public interface iMigrationlogService {

    public void savelog(String type, String desc, String job);
}

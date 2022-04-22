package com.s3cloudinarygroup.s3tocloudinary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplogdtlJPARepository extends JpaRepository<AppLogDtlsdbEntity,Long> {

    @Query("SELECT a FROM AppLogDtlsdbEntity a WHERE a.filename = ?1 and a.cldfoldername = ?2 and a.s3bucketForCLDkeypartreplaceblank=?3")
    List<AppLogDtlsdbEntity> findAppLogDtlsdbEntityByFilenameAndCldfoldername
            (String filename, String cldfoldername, String s3bucketForCLDkeypartreplaceblank);

    @Query("SELECT a FROM AppLogDtlsdbEntity a WHERE a.rowid >= ?1 and a.rowid <= ?2")
    List<AppLogDtlsdbEntity> findAppLogDtlsdbEntityByrowidrange
            (long rowidFrom, long rowidTo);

}

package com.s3cloudinarygroup.s3tocloudinary;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplogdtlService {

    @Autowired
    private ApplogdtlJPARepository applogdtlJPARepository;

    public List<AppLogDtlsdbEntity> getFiledetails(String filename, String cldfoldername,String s3bucketForCLDkeypartreplaceblank){
        return applogdtlJPARepository.
                findAppLogDtlsdbEntityByFilenameAndCldfoldername(filename,cldfoldername,s3bucketForCLDkeypartreplaceblank);
    }

    public List<AppLogDtlsdbEntity> getFiledetailsByRowIDRange(long rowidFrom, long rowidTo){
        return applogdtlJPARepository.
                findAppLogDtlsdbEntityByrowidrange(rowidFrom,rowidTo);
    }

    public void savelog(String cldFolderName, String desc, String filename,long filesizeinbytes, String inputfoldername
            ,int isError, int isProcessed,String s3bucketForCLDkeypartreplaceblank
    ) {

        AppLogDtlsdbEntity app_log_dtls = new AppLogDtlsdbEntity();
        app_log_dtls.setCldfoldername(cldFolderName);
        app_log_dtls.setDesc(desc);
        app_log_dtls.setFilename(filename);
        app_log_dtls.setFilesizeinbytes(filesizeinbytes);
        app_log_dtls.setInputfoldername(inputfoldername);
        app_log_dtls.setIserror(isError);
        app_log_dtls.setIsprocessed(isProcessed);
        app_log_dtls.setUpdatedatetime(DateTime.now().toString("yyyy-MM-dd hh:mm:ss"));
        app_log_dtls.setTimestamp(DateTime.now().toString("yyyy-MM-dd hh:mm:ss"));
        app_log_dtls.setS3bucketForCLDkeypartreplaceblank(s3bucketForCLDkeypartreplaceblank);
        applogdtlJPARepository.save(app_log_dtls);
    }

}

package com.s3cloudinarygroup.s3tocloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.joda.time.DateTime;

import java.util.concurrent.Callable;

//public class CloudinaryUploadThread extends Thread{
//
//    public void run()
//    {
//        try {
//            // Displaying the thread that is running
//            String job ="Job-" + DateTime.now().toString("ddmmyyyhhmm");
//            System.out.println(
//                    job + "Thread " + Thread.currentThread().getId()
//                            + " is running");
//            Thread.sleep(6000);
//            System.out.println(
//                    job + "Thread " + Thread.currentThread().getId()
//                            + " is Completed");
//
//        }
//        catch (Exception e) {
//            // Throwing an exception
//            System.out.println("Exception is caught");
//        }
//    }
//}
public class CloudinaryUploadThread implements Callable<String> {
    String fileKey = "";
    String sourceBucketNameForCloudinary = "";
    String cloudinarydestinationfolder = "";
    String S3bucketForCLDkeypartreplaceblank = "";
    String job;
    Cloudinary cloudinary;
    MigrationlogService migrationlogService;

    public CloudinaryUploadThread(String _fileKey, String _sourceBucketNameForCloudinary,String _cloudinarydestinationfolder
                            ,String _S3bucketForCLDkeypartreplaceblank, Cloudinary _cloudinary, MigrationlogService _migrationlogService
                            ,String _job
                            ){
        fileKey = _fileKey;
        sourceBucketNameForCloudinary = _sourceBucketNameForCloudinary;
        cloudinarydestinationfolder = _cloudinarydestinationfolder;
        S3bucketForCLDkeypartreplaceblank = _S3bucketForCLDkeypartreplaceblank;
        cloudinary = _cloudinary;
        migrationlogService = _migrationlogService;
        job = _job;
    }

    @Override
    public String call() throws Exception {

        if (fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0) { //folder
        } else {
            try{
                cloudinary.uploader().upload("s3://" + sourceBucketNameForCloudinary + "/" + fileKey,
                        ObjectUtils.asMap("public_id", cloudinarydestinationfolder + "/" +
                                (S3bucketForCLDkeypartreplaceblank.trim().length()>0? fileKey.replace(S3bucketForCLDkeypartreplaceblank,""):fileKey), "resource_type", "auto"));

            }
            catch (Exception ex){
                migrationlogService.savelog("Error","Error while uploading key " + fileKey + ". Tech Error -" + ex.getMessage(),job);
                return ex.getMessage();
            }
        }

        return "";
    }
}

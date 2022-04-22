package com.s3cloudinarygroup.s3tocloudinary;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bucketcloudinary")
@Slf4j
public class bucketResourceWithEnvVariables {
//    @Value("${S3bucket.bucket-name}")
//    private String bucketName;
//
//    @Value("${S3bucketFrom.bucket-name}")
//    private String sourceBucketName;
//
//    @Value("${S3bucketFrom.prefix}")
//    private String sourceBucketPrefix;
//
//    @Value("${S3bucketTo.bucket-name}")
//    private String destBucketName;
//
//    @Value("${S3bucketTo.dest-key-replace-blank}")
//    private String destkeyreplaceblank;
//
//    @Value("${S3bucketForCLD.bucket-name}")
//    private String S3bucketForCLD;
//    @Value("${S3bucketForCLD.prefix}")
//    private String S3bucketForCLDprefix;
//    @Value("${S3bucketForCLD.key-part-replace-blank}")
//    private String S3bucketForCLDkeypartreplaceblank;
//
//    @Value("${S3bucket.bucket-url}")
//    private String bucketurl;
//
//    @Value("${cloudinary.cloud_name}")
//    private String cloud_name;
//    @Value("${cloudinary.api_key}")
//    private String api_key;
//    @Value("${cloudinary.api_secret}")
//    private String api_secret;
//    @Value("${cloudinarydestinationfolder}")
//    private String cloudinarydestinationfolder;
//
//    @Autowired
//    private AmazonS3 amazonS3;
//
//    @Autowired
//    private MigrationlogJPARepo migrationlogJPARepo;
//
//    @Autowired
//    MigrationlogService migrationlogService;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @GetMapping("/testsqlite")
//    public ResponseEntity<String> testsqlite() throws IOException {
//
////        //Create the database table:
////        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS beers(name VARCHAR(100))");
////
////        //Insert a record:
////        jdbcTemplate.execute("INSERT INTO beers VALUES ('Stella')");
////
////        //Read records:
////        List<Beer> beers = jdbcTemplate.query("SELECT * FROM beers",
////                (resultSet, rowNum) -> new Beer(resultSet.getString("name")));
////
////        //Print read records:
////        beers.forEach(System.out::println);
//
//        migrationlogService.savelog("Info", "Job started.", "");
//
//        return new ResponseEntity<String>("", HttpStatus.OK);
//    }
//
//
//    @GetMapping("/cloudinarydtls")
//    public ResponseEntity<String> cldDtsl() throws IOException {
//        String retString = "DestinationFolder:" + cloudinarydestinationfolder + " cloud_name:" + cloud_name + ". api_key:" + api_key + ". api_secret:" + api_secret;
//        retString = retString + "\r\n" + " AWS Details: S3bucketFrom - " + sourceBucketName + ", DestBucket" + destBucketName + ", Source prefix :" + sourceBucketPrefix;
//        return new ResponseEntity<String>(retString, HttpStatus.OK);
//    }
//
//    @GetMapping("cldupload")
//    public ResponseEntity<String> cldUpload() {
//        int n = 8; // Number of threads
//        for (int i = 0; i < n; i++) {
////            CloudinaryUploadThread object = new CloudinaryUploadThread(); object.start();
//        }
//        return new ResponseEntity<String>("Job Submitted", HttpStatus.OK);
//    }
//
//    @GetMapping("/logs/{job}")
//    public ResponseEntity<List<MigrationlogdbEntity>> logs(@PathVariable String job) throws IOException {
//        //List<MigrationlogdbEntity> migrationlogdbEntities= migrationlogJPARepo.findAll();
//        List<MigrationlogdbEntity> migrationlogdbEntities;
//        if (job.trim().length() == 0) {
//            migrationlogdbEntities = migrationlogJPARepo.findAll();
//        } else {
//            migrationlogdbEntities = migrationlogJPARepo.findByJob(job);
//        }
//
//        return new ResponseEntity<List<MigrationlogdbEntity>>(migrationlogdbEntities, HttpStatus.OK);
//    }
//
//    @GetMapping("/s3tos3Transfer")
//    public ResponseEntity<String> getS3toS3Transfer() {
//        boolean errorOccured = false;
//        List<String> newfileList = new ArrayList();
//        try {
//            migrationlogService.savelog("Info", "S3 Transfer Process started " + DateTime.now().toString("dd/MM/yyyy hh:mm:ss"), "");
//
//            boolean continueloop = true;
//            String nextcontinuationToken = "";
//            int batchno = 0;
//            while (continueloop) {
//                ListObjectsV2Result result = null;
//                if (nextcontinuationToken == "") {
//                    ListObjectsV2Request request = new ListObjectsV2Request()
//                            .withBucketName(sourceBucketName)
//                            .withPrefix(sourceBucketPrefix)
//                            .withMaxKeys(100);
//                    result = amazonS3.listObjectsV2(request);
//                    if (result.getNextContinuationToken() == null) {
//                        continueloop = false;
//                        nextcontinuationToken = "";
//                    } else if (result.getNextContinuationToken().length() > 0) {
//                        nextcontinuationToken = result.getNextContinuationToken();
//                    }
//                } else {
//                    ListObjectsV2Request request = new ListObjectsV2Request()
//                            .withBucketName(sourceBucketName)
//                            .withPrefix(sourceBucketPrefix)
//                            .withContinuationToken(nextcontinuationToken)
//                            .withMaxKeys(100);
//                    result = amazonS3.listObjectsV2(request);
//
//                    if (result.getNextContinuationToken() == null) {
//                        continueloop = false;
//                        nextcontinuationToken = "";
//                    } else if (result.getNextContinuationToken().length() > 0) {
//                        nextcontinuationToken = result.getNextContinuationToken();
//                    }
//
//                }
//
//                if (result != null) {
//
//                    List<String> fileList = result.getObjectSummaries().stream()
//                            .map(S3ObjectSummary::getKey)
//                            .collect(Collectors.toList());
//                    for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
//                        if(objectSummary.getLastModified().compareTo(new SimpleDateFormat("dd-MM-yyyy").parse("01-02-2022"))>0){
//
//                            CopyObjectRequest copyObjRequest = new CopyObjectRequest(sourceBucketName, objectSummary.getKey(),
//                                    destBucketName, objectSummary.getKey().replace(destkeyreplaceblank, ""));
//                            amazonS3.copyObject(copyObjRequest);
//
//                        }
//
//                    }
//                    //migrationlogService.savelog("Info","File is copied with Modified date " + objectSummary.getKey() + "-" + new SimpleDateFormat("dd/MM/yyy hhmmss").format(objectSummary.getLastModified()),"");
//                }
//
//            } //End of infinite while loop
//
//            newfileList.clear();
//
//            //    return newfileList;
//
//        } catch (Exception ex) {
//            migrationlogService.savelog("Info", "Job aborted." + ex.getMessage(), "");
//            errorOccured = true;
//            //    return newfileList;
//
//        } finally {
//            if (errorOccured) {
//                migrationlogService.savelog("Info", "Job finished with error.", "");
//            } else {
//                migrationlogService.savelog("Info", "Job finished successfully..", "");
//            }
//            migrationlogService.savelog("Info", "S3 Transfer Finished at " + DateTime.now().toString("dd/MM/yyyy hh:mm:ss"), "");
//
//            List<MigrationlogdbEntity> migrationlogdbEntities = migrationlogJPARepo.findByJob("");
//            return new ResponseEntity<String>("", HttpStatus.OK);
//
//        }
//
//
//        //return new ResponseEntity<String>("",HttpStatus.OK);
//    }
//
//    @GetMapping("/s3tocloudinary")
//    public ResponseEntity<List<MigrationlogdbEntity>> getAllDocuments() throws IOException {
//        String job = "Job-" + DateTime.now().toString("ddmmyyyhhmm");
//        migrationlogService.savelog("Info", "Job started.", job);
//        boolean errorOccured = false;
//        List<String> newfileList = new ArrayList();
//        int noofKeysTORetrieve = 2;
//        String sourceBucketNameForCloudinary = S3bucketForCLD;
//        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", cloud_name,
//                "api_key", api_key,
//                "api_secret", api_secret,
//                "secure", true));
//
//        try {
//
//            boolean continueloop = true;
//            String nextcontinuationToken = "";
//            int batchno = 0;
//            migrationlogService.savelog("Info", "S3 Transfer to Cloudinary Started at " + DateTime.now().toString("dd/MM/yyyy hh:mm:ss"), "");
//
//            while (continueloop) {
//                ListObjectsV2Result result = null;
//                if (nextcontinuationToken == "") {
//                    ListObjectsV2Request request = new ListObjectsV2Request()
//                            .withBucketName(sourceBucketNameForCloudinary)
//                            .withPrefix(S3bucketForCLDprefix)
//                            .withMaxKeys(100);
//                    result = amazonS3.listObjectsV2(request);
//                    if (result.getNextContinuationToken() == null) {
//                        continueloop = false;
//                        nextcontinuationToken = "";
//                    } else if (result.getNextContinuationToken().length() > 0) {
//                        nextcontinuationToken = result.getNextContinuationToken();
//                    }
//                } else {
//                    ListObjectsV2Request request = new ListObjectsV2Request()
//                            .withBucketName(sourceBucketNameForCloudinary)
//                            .withPrefix(S3bucketForCLDprefix)
//                            .withMaxKeys(100)
//                            .withContinuationToken(nextcontinuationToken);
//                    result = amazonS3.listObjectsV2(request);
//
//                    if (result.getNextContinuationToken() == null) {
//                        continueloop = false;
//                        nextcontinuationToken = "";
//                    } else if (result.getNextContinuationToken().length() > 0) {
//                        nextcontinuationToken = result.getNextContinuationToken();
//                    }
//
//                }
//
//                if (result != null) {
//                    List<String> fileList = result.getObjectSummaries().stream()
//                            .map(S3ObjectSummary::getKey)
//                            .collect(Collectors.toList());
//                    for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
//
//                        migrationlogService.savelog("Info", "File Modified date " + objectSummary.getKey() + "-" + new SimpleDateFormat("dd/MM/yyy hhmmss").format(objectSummary.getLastModified()), job);
//                    }
////                      https://stackoverflow.com/questions/36153110/aws-s3-get-last-modified-timestamp-java
//
//                    for (String fileitem : fileList) {
//                        newfileList.add(fileitem);
//                    }
//                }
//
//                List<String> finalListToProcess = new ArrayList();
//                ;
//                for (String fileKey : newfileList) {
//                    if (!(fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0)) {
//                        finalListToProcess.add(fileKey);
//                    }
//                }
//                batchno = batchno + 1;
//
//                for (String fileKey : finalListToProcess) {
//
//
//                    if (fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0) { //folder
//
//                    } else {
//                        try {
////                            cloudinary.uploader().upload("s3://" + sourceBucketNameForCloudinary + "/" + fileKey,ObjectUtils.emptyMap());
//                            cloudinary.uploader().upload("s3://" + sourceBucketNameForCloudinary + "/" + fileKey,
//                                    ObjectUtils.asMap("public_id", cloudinarydestinationfolder + "/" +
//                                            (S3bucketForCLDkeypartreplaceblank.trim().length() > 0 ? fileKey.replace(S3bucketForCLDkeypartreplaceblank, "") : fileKey), "resource_type", "auto"));
//
//                            //                          migrationlogService.savelog("Info",fileKey + " copied.",job);
//
//                        } catch (Exception ex) {
//                            migrationlogService.savelog("Error", fileKey + " copy error. " + ex.getMessage(), job);
//                        }
//                    }
//                }
//                migrationlogService.savelog("Info", "Batch no " + String.valueOf(batchno) + " finished. ", job);
//
//                newfileList.clear();
//
//            } //End of infinite while loop
//
//
//            //    return newfileList;
//
//        } catch (Exception ex) {
//            migrationlogService.savelog("Info", "Job aborted." + ex.getMessage(), job);
//            errorOccured = true;
//            //    return newfileList;
//
//        } finally {
//            if (errorOccured) {
//                migrationlogService.savelog("Info", "Job finished with error.", job);
//            } else {
//                migrationlogService.savelog("Info", "Job finished successfully..", job);
//            }
//            migrationlogService.savelog("Info", "S3 Transfer to Cloudinary finished at " + DateTime.now().toString("dd/MM/yyyy hh:mm:ss"), "");
//
//            List<MigrationlogdbEntity> migrationlogdbEntities = migrationlogJPARepo.findByJob(job);
//            return new ResponseEntity<List<MigrationlogdbEntity>>(migrationlogdbEntities, HttpStatus.OK);
//        }
//
//    }
//
//
//    @GetMapping("/s3tocld")
//    public ResponseEntity<List<MigrationlogdbEntity>> s3tocld() throws IOException {
//        String job = "Job-" + DateTime.now().toString("ddmmyyyhhmm");
//        migrationlogService.savelog("Info", "Job started.", job);
//        boolean errorOccured = false;
//        List<String> newfileList = new ArrayList();
//        String sourceBucketNameForCloudinary = S3bucketForCLD;
//        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", cloud_name,
//                "api_key", api_key,
//                "api_secret", api_secret,
//                "secure", true));
//
//        try {
//
//            boolean continueloop = true;
//            String nextcontinuationToken = "";
//            int batchno = 0;
//            migrationlogService.savelog("Info", "S3 Transfer to Cloudinary Started at " + DateTime.now().toString("dd/MM/yyyy hh:mm:ss"), "");
//
//            while (continueloop) {
//                ListObjectsV2Result result = null;
//                if (nextcontinuationToken == "") {
//                    ListObjectsV2Request request = new ListObjectsV2Request()
//                            .withBucketName(sourceBucketNameForCloudinary)
//                            .withPrefix(S3bucketForCLDprefix)
//                            .withMaxKeys(10);
//                    result = amazonS3.listObjectsV2(request);
//                    if (result.getNextContinuationToken() == null) {
//                        continueloop = false;
//                        nextcontinuationToken = "";
//                    } else if (result.getNextContinuationToken().length() > 0) {
//                        nextcontinuationToken = result.getNextContinuationToken();
//                    }
//                } else {
//                    ListObjectsV2Request request = new ListObjectsV2Request()
//                            .withBucketName(sourceBucketNameForCloudinary)
//                            .withPrefix(S3bucketForCLDprefix)
//                            .withMaxKeys(10)
//                            .withContinuationToken(nextcontinuationToken);
//                    result = amazonS3.listObjectsV2(request);
//
//                    if (result.getNextContinuationToken() == null) {
//                        continueloop = false;
//                        nextcontinuationToken = "";
//                    } else if (result.getNextContinuationToken().length() > 0) {
//                        nextcontinuationToken = result.getNextContinuationToken();
//                    }
//
//                }
//
//                if (result != null) {
//                    List<String> fileList = result.getObjectSummaries().stream()
//                            .map(S3ObjectSummary::getKey)
//                            .collect(Collectors.toList());
//                    for (String fileitem : fileList) {
//                        newfileList.add(fileitem);
//                    }
//                }
//
//                List<String> finalListToProcess = new ArrayList();
//                ;
//                for (String fileKey : newfileList) {
//                    if (!(fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0)) {
//                        finalListToProcess.add(fileKey);
//                    }
//                }
//                batchno = batchno + 1;
//                List<CloudinaryUploadThread> cloudinaryUploadThreads = new ArrayList();
//
//                for (String fileKey : finalListToProcess) {
//
//                    CloudinaryUploadThread cloudinaryUploadThread = new CloudinaryUploadThread(
//                            fileKey,
//                            sourceBucketNameForCloudinary,
//                            cloudinarydestinationfolder,
//                            S3bucketForCLDkeypartreplaceblank,
//                            cloudinary,
//                            migrationlogService,
//                            job
//                    );
//                    cloudinaryUploadThreads.add(cloudinaryUploadThread);
////                    ExecutorService executorService = Executors.newSingleThreadExecutor();
////                    executorService.submit(cloudinaryUploadThread );
//
//                }
//                ExecutorService executorService = Executors.newFixedThreadPool(10);
//                List<Future<String>> ThreadResult = executorService.invokeAll(cloudinaryUploadThreads);
//                executorService.shutdown();
//                try {
//                    if (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
//                        executorService.shutdownNow();
//                    }
//                } catch (InterruptedException ex) {
//                    executorService.shutdownNow();
//                    Thread.currentThread().interrupt();
//                }
//
////                    if (fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0) { //folder
////                    } else {
////                        try{
////                            cloudinary.uploader().upload("s3://" + sourceBucketNameForCloudinary + "/" + fileKey,
////                                    ObjectUtils.asMap("public_id", cloudinarydestinationfolder + "/" +
////                                            (S3bucketForCLDkeypartreplaceblank.trim().length()>0? fileKey.replace(S3bucketForCLDkeypartreplaceblank,""):fileKey), "resource_type", "auto"));
////
////                        }
////                        catch (Exception ex){
////                            migrationlogService.savelog("Error",fileKey + " copy error. " + ex.getMessage(),job);
////                        }
////                    }
//
//                migrationlogService.savelog("Info", "Batch no " + String.valueOf(batchno) + " finished. ", job);
//
//                newfileList.clear();
//
//            } //End of infinite while loop
//
//
//            //    return newfileList;
//
//        } catch (Exception ex) {
//            migrationlogService.savelog("Info", "Job aborted." + ex.getMessage(), job);
//            errorOccured = true;
//            //    return newfileList;
//
//        } finally {
//            if (errorOccured) {
//                migrationlogService.savelog("Info", "Job finished with error.", job);
//            } else {
//                migrationlogService.savelog("Info", "Job finished successfully..", job);
//            }
//            migrationlogService.savelog("Info", "S3 Transfer to Cloudinary finished at " + DateTime.now().toString("dd/MM/yyyy hh:mm:ss"), "");
//
//            List<MigrationlogdbEntity> migrationlogdbEntities = migrationlogJPARepo.findByJob(job);
//            return new ResponseEntity<List<MigrationlogdbEntity>>(migrationlogdbEntities, HttpStatus.OK);
//        }
//
//    }
//

}
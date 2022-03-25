package net.javaguides.springboot.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.MigrationlogJPARepo;
import net.javaguides.springboot.MigrationlogService;
import net.javaguides.springboot.MigrationlogdbEntity;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bucketcloudinary")
@Slf4j
public class bucketResource {
    @Value("${S3bucket.bucket-name}")
    private String bucketName;

    @Value("${S3bucket.bucket-url}")
    private String bucketurl;

    @Value("${cloudinary.cloud_name}")
    private String cloud_name;
    @Value("${cloudinary.api_key}")
    private String api_key;
    @Value("${cloudinary.api_secret}")
    private String api_secret;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private MigrationlogJPARepo migrationlogJPARepo;

    @Autowired
    MigrationlogService migrationlogService;

    @GetMapping("/cloudinarydtls")
    public ResponseEntity< String> cldDtsl() throws IOException {
        String retString = " cloud_name:" + cloud_name + ". api_key:"+ api_key + ". api_secret:"+api_secret;
        return new ResponseEntity<String>(retString, HttpStatus.OK);
    }


    @GetMapping("/logs/{job}")
    public ResponseEntity< List<MigrationlogdbEntity>> logs(@PathVariable String job) throws IOException {
        //List<MigrationlogdbEntity> migrationlogdbEntities= migrationlogJPARepo.findAll();
        List<MigrationlogdbEntity> migrationlogdbEntities;
        if(job.trim().length()==0){
            migrationlogdbEntities= migrationlogJPARepo.findAll();
        }
        else{
            migrationlogdbEntities= migrationlogJPARepo.findByJob(job);
        }

        return new ResponseEntity<List<MigrationlogdbEntity>>(migrationlogdbEntities, HttpStatus.OK);
    }


    @GetMapping("/s3tocloudinary")
    public ResponseEntity<List<MigrationlogdbEntity>> getAllDocuments() throws IOException {
        String job ="Job-" + DateTime.now().toString("ddmmyyyhhmm");
        migrationlogService.savelog("Info","Job started.",job);
        boolean errorOccured = false;
        List<String> newfileList = new ArrayList();
        int noofKeysTORetrieve = 2;
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloud_name,
                "api_key", api_key,
                "api_secret", api_secret,
                "secure", true));

        try{

            boolean continueloop = true;
            String nextcontinuationToken = "";
            int batchno = 0;
            while (continueloop) {
                ListObjectsV2Result result = null;
                if (nextcontinuationToken == "") {
                    ListObjectsV2Request request = new ListObjectsV2Request()
                            .withBucketName(bucketName)
//                .withPrefix(key)
                            .withMaxKeys(10);
                    result = amazonS3.listObjectsV2(request);
                    if (result.getNextContinuationToken() == null) {
                        continueloop = false;
                        nextcontinuationToken = "";
                    } else if (result.getNextContinuationToken().length() > 0) {
                        nextcontinuationToken = result.getNextContinuationToken();
                    }
                } else {
                    ListObjectsV2Request request = new ListObjectsV2Request()
                            .withBucketName(bucketName)
//                .withPrefix(key)
                            .withContinuationToken(nextcontinuationToken)
                            .withMaxKeys(noofKeysTORetrieve);
                    result = amazonS3.listObjectsV2(request);

                    if (result.getNextContinuationToken() == null) {
                        continueloop = false;
                        nextcontinuationToken = "";
                    } else if (result.getNextContinuationToken().length() > 0) {
                        nextcontinuationToken = result.getNextContinuationToken();
                    }

                }

                if (result != null) {
                    List<String> fileList = result.getObjectSummaries().stream()
                            .map(S3ObjectSummary::getKey)
                            .collect(Collectors.toList());
                    for (String fileitem : fileList) {
                        newfileList.add(fileitem);
                    }
                }

                List<String> finalListToProcess = new ArrayList();;
                for (String fileKey : newfileList) {
                    if (!(fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0)) {
                        finalListToProcess.add(fileKey);
                    }
                }

                for (String fileKey : finalListToProcess) {
                    batchno = batchno + 1;
                    migrationlogService.savelog("Info","Batch no " + String.valueOf(batchno) + " started with total " + finalListToProcess + " files." + "",job);

                    if (fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0) { //folder

                    } else {
                        try{
                            cloudinary.uploader().upload("s3://" + bucketName + "/" + fileKey,
                                    ObjectUtils.asMap("public_id", "FromS3/" + fileKey, "resource_type", "auto"));

                            migrationlogService.savelog("Info",fileKey + " copied.",job);

                        }
                        catch (Exception ex){
                            migrationlogService.savelog("Error",fileKey + " copy error. " + ex.getMessage(),job);
                        }
                    }
                    migrationlogService.savelog("Info","Batch no " + String.valueOf(batchno) + " Finished.",job);

                }

                newfileList.clear();

            } //End of infinite while loop


        //    return newfileList;

        }
        catch (Exception ex){
            migrationlogService.savelog("Info","Job aborted.",job);
            errorOccured=true;
        //    return newfileList;

        }
        finally {
            if(errorOccured){
                migrationlogService.savelog("Info","Job finished with error.",job);
            }
            else {
                migrationlogService.savelog("Info","Job finished successfully..",job);
            }

            List<MigrationlogdbEntity> migrationlogdbEntities= migrationlogJPARepo.findByJob(job);
            return new ResponseEntity<List<MigrationlogdbEntity>>(migrationlogdbEntities, HttpStatus.OK);
        }

    }

}
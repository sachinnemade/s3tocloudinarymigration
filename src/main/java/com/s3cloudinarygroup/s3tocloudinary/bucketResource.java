package com.s3cloudinarygroup.s3tocloudinary;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;

@RestController
@RequestMapping("/bucketcloudinary")
@Slf4j
public class bucketResource {
    @Value("${S3bucket.bucket-name}")
    private String bucketName;

    @Value("${S3bucket.bucket-url}")
    private String bucketurl;

    @Autowired
    private AmazonS3 amazonS3;

    @GetMapping("/files")
    public String filedetails() throws IOException {
        return "sachin";
    }

    @GetMapping("/s3files")
    public List<String> getAllDocuments() throws IOException {

        /*
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "anuvu-dev",
                "api_key", "336625465193458",
                "api_secret", "WuvXWKKtWJrcfNulItmGqvViSGU",
                "secure", true));
        */
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "anuvu-test",
                "api_key", "659539734383637",
                "api_secret", "jQrkZzSXbIl4QGKIvbJLNsOlyOI",
                "secure", true));

        List<String> newfileList = new ArrayList();
        boolean continueloop = true;
        String nextcontinuationToken = "";
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
                        .withMaxKeys(10);
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

        }

        for (String fileKey : newfileList) {
            if (fileKey.length() - 1 == fileKey.lastIndexOf("/") || fileKey.indexOf(".") == 0) { //folder

            } else {
                cloudinary.uploader().upload("s3://" + bucketName + "/" + fileKey,
                        ObjectUtils.asMap("public_id", "FromS3/" + fileKey, "resource_type", "auto"));
            }
        }
        return newfileList;

    }

}
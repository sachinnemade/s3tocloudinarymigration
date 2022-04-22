package com.s3cloudinarygroup.s3tocloudinary;

public class S3toS3TransferModel {
    //s3tos3Transfer/{sourceBucketName}/{destBucketName}/{sourceBucketPrefix}/{destkeyreplaceblank
    private String sourceBucketPrefix;
    private String sourceBucketName;
    private String destBucketName;
    private String destkeyreplaceblank;


    public String getSourceBucketName() {
        return sourceBucketName;
    }

    public String getSourceBucketPrefix() {
        return sourceBucketPrefix;
    }

    public void setSourceBucketPrefix(String sourceBucketPrefix) {
        this.sourceBucketPrefix = sourceBucketPrefix;
    }

    public void setSourceBucketName(String sourceBucketName) {
        this.sourceBucketName = sourceBucketName;
    }

    public String getDestBucketName() {
        return destBucketName;
    }

    public void setDestBucketName(String destBucketName) {
        this.destBucketName = destBucketName;
    }

    public String getDestkeyreplaceblank() {
        return destkeyreplaceblank;
    }

    public void setDestkeyreplaceblank(String destkeyreplaceblank) {
        this.destkeyreplaceblank = destkeyreplaceblank;
    }
}

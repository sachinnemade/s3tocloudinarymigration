package com.s3cloudinarygroup.s3tocloudinary;

public class S3ToCldTransferModel {
    private long rowIndexFrom;
    private long rowIndexTo;
    private String sourceBucketNameForCloudinary;
    private String cloud_name;
    private String api_key;
    private String api_secret;

    public long getRowIndexFrom() {
        return rowIndexFrom;
    }

    public void setRowIndexFrom(int rowIndexFrom) {
        this.rowIndexFrom = rowIndexFrom;
    }

    public long getRowIndexTo() {
        return rowIndexTo;
    }

    public void setRowIndexTo(int rowIndexTo) {
        this.rowIndexTo = rowIndexTo;
    }

    public String getCloud_name() {
        return cloud_name;
    }

    public void setCloud_name(String cloud_name) {
        this.cloud_name = cloud_name;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_secret() {
        return api_secret;
    }

    public void setApi_secret(String api_secret) {
        this.api_secret = api_secret;
    }

    public void setRowIndexFrom(long rowIndexFrom) {
        this.rowIndexFrom = rowIndexFrom;
    }

    public void setRowIndexTo(long rowIndexTo) {
        this.rowIndexTo = rowIndexTo;
    }

    public String getSourceBucketNameForCloudinary() {
        return sourceBucketNameForCloudinary;
    }

    public void setSourceBucketNameForCloudinary(String sourceBucketNameForCloudinary) {
        this.sourceBucketNameForCloudinary = sourceBucketNameForCloudinary;
    }
}

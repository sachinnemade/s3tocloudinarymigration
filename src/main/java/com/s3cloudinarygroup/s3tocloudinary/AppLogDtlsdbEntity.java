package com.s3cloudinarygroup.s3tocloudinary;

import javax.persistence.*;

@Table(name="applogdtls")
@Entity
public class AppLogDtlsdbEntity {

    // Entity columns

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rowid;

    private String inputfoldername;
    private String filename;
    @Column(nullable = true)
    private long filesizeinbytes;
    private String cldfoldername;
    private int isprocessed;
    private int iserror;
    private String desc;
    private String s3bucketForCLDkeypartreplaceblank;
    private String updatedatetime;
    private String timestamp;

    // Getters and Setters properties

    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }

    public String getInputfoldername() {
        return inputfoldername;
    }

    public void setInputfoldername(String inputfoldername) {
        this.inputfoldername = inputfoldername;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCldfoldername() {
        return cldfoldername;
    }

    public void setCldfoldername(String cldfoldername) {
        this.cldfoldername = cldfoldername;
    }

    public int getIsprocessed() {
        return isprocessed;
    }

    public void setIsprocessed(int isprocessed) {
        this.isprocessed = isprocessed;
    }

    public int getIserror() {
        return iserror;
    }

    public void setIserror(int iserror) {
        this.iserror = iserror;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUpdatedatetime() {
        return updatedatetime;
    }

    public void setUpdatedatetime(String updatedatetime) {
        this.updatedatetime = updatedatetime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getS3bucketForCLDkeypartreplaceblank() {
        return s3bucketForCLDkeypartreplaceblank;
    }

    public void setS3bucketForCLDkeypartreplaceblank(String s3bucketForCLDkeypartreplaceblank) {
        this.s3bucketForCLDkeypartreplaceblank = s3bucketForCLDkeypartreplaceblank;
    }

    public long getFilesizeinbytes() {
        return filesizeinbytes;
    }

    public void setFilesizeinbytes(long filesizeinbytes) {
        this.filesizeinbytes = filesizeinbytes;
    }

}

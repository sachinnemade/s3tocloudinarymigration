package net.javaguides.springboot;

import javax.persistence.*;

@Table(name="migrationlog")
@Entity
public class MigrationlogdbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rowid")
    private int rowid;

    @Column(name="logtype")
    private String logtype;

    @Column(name="logdesc")
    private String logdesc;

    @Column(name="recorddate")
    private String recorddate;

    @Column(name="job")
    private String job;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getLogdesc() {
        return logdesc;
    }

    public void setLogdesc(String logdesc) {
        this.logdesc = logdesc;
    }

    public String getRecorddate() {
        return recorddate;
    }

    public void setRecorddate(String recorddate) {
        this.recorddate = recorddate;
    }
}

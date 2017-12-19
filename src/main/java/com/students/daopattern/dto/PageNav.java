
package com.students.daopattern.dto;


public class PageNav {

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public void setNoOfRecords(int noOfRecords) {
        this.noOfRecords = noOfRecords;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    
private int noOfRecords;
 private int offset;
 private final int recordsPerPage = 3;
 private int page = 1 ;
 
     public int getRecordsPerPage() {
        return recordsPerPage;
    }
}

package com.example.testcodestudy;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    public Study(int limit) {
        if(limit < 0){
            throw new IllegalArgumentException("리밋은 0보다 커야함");
        }
        this.limit = limit;
    }
    public StudyStatus getStatus() {
        return this.status;
    }
    public int getLimit() {
        return this.limit;
    }
}

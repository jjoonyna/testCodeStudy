package com.example.testcodestudy;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private String name;

    public Study(int limit) {
        if(limit < 0){
            throw new IllegalArgumentException("리밋은 0보다 커야함");
        }
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public StudyStatus getStatus() {
        return this.status;
    }
    public int getLimit() {
        return this.limit;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() {
        return "Study{"
                + "status: " + status +
                ", limit: " + limit +
                ", name: " + name +
                "}";
    }
}

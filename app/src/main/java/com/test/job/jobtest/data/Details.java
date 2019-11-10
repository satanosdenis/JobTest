package com.test.job.jobtest.data;

public class Details {
    private String detailDescription;
    private String detailValue;

    public Details(String detailDescription, String detailValue) {
        this.detailDescription = detailDescription;
        this.detailValue = detailValue;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public String getDetailValue() {
        return detailValue;
    }
}

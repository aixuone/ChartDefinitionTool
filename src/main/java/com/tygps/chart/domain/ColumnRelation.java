package com.tygps.chart.domain;

public class ColumnRelation {

    private String relatedColumnPK;
    private String chartDefPK;
    private String relatedColumnUUID;
    private String relatedColumnName;
    private String relatedColumnID;

    public String getRelatedColumnPK() {
        return relatedColumnPK;
    }

    public void setRelatedColumnPK(String relatedColumnPK) {
        this.relatedColumnPK = relatedColumnPK;
    }

    public String getChartDefPK() {
        return chartDefPK;
    }

    public void setChartDefPK(String chartDefPK) {
        this.chartDefPK = chartDefPK;
    }

    public String getRelatedColumnName() {
        return relatedColumnName;
    }

    public void setRelatedColumnName(String relatedColumnName) {
        this.relatedColumnName = relatedColumnName;
    }

    public String getRelatedColumnID() {
        return relatedColumnID;
    }

    public void setRelatedColumnID(String relatedColumnID) {
        this.relatedColumnID = relatedColumnID;
    }

    public String getRelatedColumnUUID() {
        return relatedColumnUUID;
    }

    public void setRelatedColumnUUID(String relatedColumnUUID) {
        this.relatedColumnUUID = relatedColumnUUID;
    }
}

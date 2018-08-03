package com.tygps.chart.domain;

public class ColumnRelation {
    private String columnID;
    private String relatedColumnUUID;

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }

    public String getRelatedColumnUUID() {
        return relatedColumnUUID;
    }

    public void setRelatedColumnUUID(String relatedColumnUUID) {
        this.relatedColumnUUID = relatedColumnUUID;
    }
}

package com.tygps.chart.domain;

public class ColumnValue{
    private String columnID;
    private String columnDispType;
    private ChartPolymer columnPolymer;

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }

    public String getColumnDispType() {
        return columnDispType;
    }

    public void setColumnDispType(String columnDispType) {
        this.columnDispType = columnDispType;
    }

    public ChartPolymer getColumnPolymer() {
        return columnPolymer;
    }

    public void setColumnPolymer(ChartPolymer columnPolymer) {
        this.columnPolymer = columnPolymer;
    }
}

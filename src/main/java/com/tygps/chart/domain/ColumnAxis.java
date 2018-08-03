package com.tygps.chart.domain;

public class ColumnAxis {
    private String columnID;
    private String columnPolymer;

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }

    public String getColumnPolymer() {
        return columnPolymer;
    }

    public void setColumnPolymer(String columnPolymer) {
        this.columnPolymer = ChartPolymer.valueOf(columnPolymer).toString();
    }
}

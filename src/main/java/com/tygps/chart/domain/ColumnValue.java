package com.tygps.chart.domain;

public class ColumnValue{
    private String columnID;
    private String columnDispType;
    private String columnPolymer;

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

    public String getColumnPolymer() {
        return columnPolymer;
    }

    public void setColumnPolymer(String columnPolymer) {
        if(columnPolymer!=null) {
            if(ChartPolymer.valueOf(columnPolymer)==null){
                System.out.println("值字段聚合方式不支持="+columnPolymer);
            }else {
                this.columnPolymer = ChartPolymer.valueOf(columnPolymer).toString();
            }
        }
    }
}

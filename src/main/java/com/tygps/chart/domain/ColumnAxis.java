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
        if(columnPolymer!=null){
            if(ChartPolymer.valueOf(columnPolymer)==null){
                System.out.println("轴字段聚合方式不支持="+columnPolymer);
            }else{
                this.columnPolymer = ChartPolymer.valueOf(columnPolymer).toString();
            }
        }
    }
}

package com.tygps.chart.domain;

import com.tygps.chart.web.ChartGC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartDefinition {
    private String chartName;
    private String chartTheme;
    private String chartType;
    private String dataSetID;
    private List<Map> columnValue;
    private List<ColumnValue> columnValues;
    private List<Map> columnRelation;
    private List<ColumnRelation> columnRelations;
    private List<Map> columnAxis;
    private List<ColumnAxis> columnAxises;
    private List<Map> columnSeries;
    private List<ColumnSeries> columnSerieses;

    public List<ColumnRelation> getColumnRelations() {
        return columnRelations;
    }

    public void setColumnRelations(List<ColumnRelation> columnRelations) {
        this.columnRelations = columnRelations;
    }

    public List<ColumnAxis> getColumnAxises() {
        return columnAxises;
    }

    public void setColumnAxises(List<ColumnAxis> columnAxises) {
        this.columnAxises = columnAxises;
    }

    public List<ColumnSeries> getColumnSerieses() {
        return columnSerieses;
    }

    public void setColumnSerieses(List<ColumnSeries> columnSerieses) {
        this.columnSerieses = columnSerieses;
    }

    public List<Map> getColumnRelation() {

        return columnRelation;

    }

    public void setColumnRelation(List<Map> columnRelation) {
        this.columnRelation = columnRelation;
        columnRelations = new ArrayList<ColumnRelation>();
        for (Map map : columnRelation) {
            ColumnRelation tmp = new ColumnRelation();
            tmp.setRelatedColumnID((String) map.get(ChartGC.COLUMN_ID));
            tmp.setRelatedColumnUUID((String) map.get(ChartGC.COLUMN_RELATED_UUID));
            columnRelations.add(tmp);
        }
    }

    public List<Map> getColumnAxis() {
        return columnAxis;
    }

    public void setColumnAxis(List<Map> columnAxis) {
        this.columnAxis = columnAxis;

        columnAxises = new ArrayList<ColumnAxis>();
        for (Map map : columnAxis) {
            ColumnAxis tmp = new ColumnAxis();
            tmp.setColumnID((String) map.get(ChartGC.COLUMN_ID));
            tmp.setColumnPolymer((String)map.get(ChartGC.COLUMN_POLYMER));
            columnAxises.add(tmp);
        }
    }

    public List<Map> getColumnSeries() {
        return columnSeries;
    }

    public void setColumnSeries(List<Map> columnSeries) {
        this.columnSeries = columnSeries;

        columnSerieses = new ArrayList<ColumnSeries>();
        for (Map map : columnSeries) {
            ColumnSeries tmp = new ColumnSeries();
            tmp.setColumnID((String) map.get(ChartGC.COLUMN_ID));
            columnSerieses.add(tmp);
        }
    }

    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }



    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public String getChartTheme() {
        return chartTheme;
    }

    public void setChartTheme(String chartTheme) {
        this.chartTheme = chartTheme;
    }

    public String getDataSetID() {
        return dataSetID;
    }

    public void setDataSetID(String dataSetID) {
        this.dataSetID = dataSetID;
    }

    public List<Map> getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(List<Map> columnValue) {
        this.columnValue = columnValue;
        columnValues = new ArrayList<ColumnValue>();
        for (Map map : columnValue) {
            ColumnValue tmp = new ColumnValue();
            tmp.setColumnID((String) map.get(ChartGC.COLUMN_ID));
            tmp.setColumnDispType((String) map.get(ChartGC.COLUMN_DISP_TYPE));
            tmp.setColumnPolymer((String) map.get(ChartGC.COLUMN_POLYMER));
            columnValues.add(tmp);
        }
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
}


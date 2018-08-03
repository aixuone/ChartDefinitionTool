package com.tygps.chart.domain;

import com.tygps.chart.service.ChartDataSetType;

public class DWFDataSet {

    private String dataSetName;
    private String dataSetID;
    private ChartDataSetType dataSetType;

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getDataSetID() {
        return dataSetID;
    }

    public void setDataSetID(String dataSetID) {
        this.dataSetID = dataSetID;
    }

    public ChartDataSetType getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(ChartDataSetType dataSetType) {
        this.dataSetType = dataSetType;
    }
}

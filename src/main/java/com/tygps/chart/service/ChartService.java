package com.tygps.chart.service;

import com.tygps.chart.dao.ChartDao;
import com.tygps.chart.dao.ChartDataDao;
import com.tygps.chart.domain.*;
import com.tygps.chart.tools.ChartDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {

    @Autowired
    private ChartDao chartDao;
    @Autowired
    private ChartDataDao chartDataDao;

    @Transactional(rollbackFor = Exception.class)
    public String saveChartDefinition(ChartDefinition chart) {

        String chartID = chartDao.insertChartDefinition(chart);

        return chartID;

    }

    public void setChartDao(ChartDao chartDao) {
        this.chartDao = chartDao;
    }

    public List<ColumnRelation> getRelatedColumns() {
        System.out.println("服务调用开始：获取筛选字段列表");
        List<ColumnRelation> relatedColumns = chartDao.selectRelatedColumn(null);
        return relatedColumns;
    }

    public String addRelatedColumns(ChartRelatedColumn column) {
        System.out.println("服务调用开始：新增筛选字段");
        String relatedColumnUUID = chartDao.insertRelatedColumn(column.getRelatedColumnName());
        return relatedColumnUUID;
    }

    public List<Map> getData(String userID, String chartID) {
        String dataSetID = "";
        List<ColumnRelation> relatedColumns = chartDao.selectRelatedColumn(chartID);
        List<ColumnValue> valueColumns = chartDao.selectValueColumn(chartID);
        List<ColumnAxis> axisColumns = chartDao.selectAxisColumn(chartID);
        List<ColumnSeries> seriesColumns = chartDao.selectSeriesColumn(chartID);

        //series fields 不能在 axis column和 value column中出现，否则series作废
        List<String> conflictFields = new ArrayList<String>();

        ChartDataUtil.parseRelation(relatedColumns, conflictFields);
        List<String> selFields = ChartDataUtil.parseValue(valueColumns, conflictFields);
        List<String> groupFields = ChartDataUtil.parseAxis(axisColumns, conflictFields);
        List<String> seriesFields = ChartDataUtil.parseSeries(seriesColumns, conflictFields);
        groupFields.addAll(seriesFields);

        if(seriesFields.size()>1) {
            List retSeries = chartDataDao.selectSerise(
                    ChartDataUtil.buildSeriesSQL(chartID, seriesFields));

            if(retSeries.size()>0){

            }
        }

        List<Map<String, Object>> middleResult = chartDataDao.selectChartData(ChartDataUtil.
                buildChartDataSQL(dataSetID, selFields, groupFields, seriesFields));

        ChartDataUtil.convertEchartStyle(middleResult, seriesFields);
        return null;
    }
}

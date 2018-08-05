package com.tygps.chart.service;

import com.tygps.chart.dao.ChartDao;
import com.tygps.chart.dao.ChartDataDao;
import com.tygps.chart.domain.*;
import com.tygps.chart.tools.ChartDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Map getChartData(String userID, String chartID) {

        ChartDefinition chart = chartDao.selectChartDefinition(chartID);

        String dataSetID = chart.getDataSetID();
        List<ColumnRelation> relatedColumns = chartDao.selectRelatedColumn(chartID);

        List<ColumnValue> valueColumns = chartDao.selectValueColumn(chartID);
        List<ColumnAxis> axisColumns = chartDao.selectAxisColumn(chartID);
        List<ColumnSeries> seriesColumns = chartDao.selectSeriesColumn(chartID);

        //series fields 不能在 axis column和 value column中出现，否则series作废

        ChartDataSQL sql = new ChartDataSQL(dataSetID);
        ChartDataUtil.parseValue(valueColumns, sql);
        System.out.println("值字段="+sql);
        ChartDataUtil.parseAxis(axisColumns, sql);
        System.out.println("轴字段="+sql);
        ChartDataUtil.parseSeries(seriesColumns, sql);
        System.out.println("系列字段="+sql);


        List<Map<String, Object>> middleResult = chartDataDao.selectChartData(sql.toString());

        List<Map<String, Object>> tmp = chartDataDao.select(
                ChartDataUtil.getSeriesResultSQL(dataSetID, seriesColumns));
        List<String> returnSeries  = ChartDataUtil.getReturnSeries(tmp);
        Map retInfo = ChartDataUtil.convertEchartStyle(middleResult, axisColumns, valueColumns,
                seriesColumns, returnSeries);
        return retInfo;
    }
}

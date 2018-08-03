package com.tygps.chart.service;

import com.tygps.chart.dao.ChartDao;
import com.tygps.chart.domain.ChartDefinition;
import com.tygps.chart.domain.ChartRelatedColumn;
import com.tygps.chart.domain.RelatedColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class ChartService {

    @Autowired
    private ChartDao chartDao;

    @Transactional(rollbackFor = Exception.class)
    public String saveChartDefinition(ChartDefinition chart) {

        String chartID = chartDao.insertChartDefinition(chart);

        return chartID;

    }

    public void setChartDao(ChartDao chartDao) {
        this.chartDao = chartDao;
    }

    public List<RelatedColumn> getRelatedColumns() {
        System.out.println("服务调用开始：获取筛选字段列表");
        List<RelatedColumn> relatedColumns = chartDao.selectRelatedColumn();
        return relatedColumns;
    }

    public String addRelatedColumns(ChartRelatedColumn column) {
        System.out.println("服务调用开始：新增筛选字段");
        String relatedColumnUUID = chartDao.insertRelatedColumn(column.getRelatedColumnName());
        return relatedColumnUUID;
    }
}

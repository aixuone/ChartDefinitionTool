package com.tygps.chart.service;

import com.tygps.chart.dao.DataSetDao;
import com.tygps.chart.domain.DWFDataSet;
import com.tygps.chart.domain.DWFDataSetColumn;
import com.tygps.chart.domain.TYUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSetService {
    private DataSetDao dataSetDao;

    public List<DWFDataSet> getDataSets(String userID){

        System.out.println("服务调用开始：获取数据集列表");
        TYUser user = new TYUser(userID);

        List<DWFDataSet> dataSets = dataSetDao.getDataSets(user);
        System.out.println("服务调用结束：获取数据集列表="+dataSets.size());

        return dataSets;
    }

    @Autowired
    public void setDataSetDao(DataSetDao dataSetDao){
        this.dataSetDao = dataSetDao;
    }

    public List<DWFDataSetColumn> getColumns(String dataSetID) {
        System.out.println("服务调用开始：获取数据集字段列表");
        DWFDataSet dataSet = new DWFDataSet();
        dataSet.setDataSetID(dataSetID);
        List<DWFDataSetColumn> columns = dataSetDao.getColumns(dataSet);

        return columns;
    }
}

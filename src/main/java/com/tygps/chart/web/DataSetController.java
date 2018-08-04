package com.tygps.chart.web;

import com.tygps.chart.domain.DWFDataSet;
import com.tygps.chart.domain.DWFDataSetColumn;
import com.tygps.chart.service.ChartResponse;
import com.tygps.chart.service.DataSetService;
import com.tygps.chart.tools.ChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataSetController {
    private DataSetService dataSetService;

    @RequestMapping(value = "/dataSets/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public ChartResponse getDataSets(@PathVariable String userID){

        System.out.println("GET请求：获取数据集。userID="+userID);
        List<DWFDataSet> dataSets  = dataSetService.getDataSets(userID);

        return ChartUtil.returnSuccessResponse(dataSets);
    }

    @RequestMapping(value="/dataSets/{dataSetID}/columns", method = RequestMethod.GET)
    @ResponseBody
    public ChartResponse getColunms(@PathVariable String dataSetID){
        System.out.println("GET请求：获取数据集字段。dataSetID="+dataSetID);

        List<DWFDataSetColumn> columns = dataSetService.getColumns(dataSetID);
        return ChartUtil.returnSuccessResponse(columns);
    }

    @Autowired
    public void setDataSetService(DataSetService dataSetService){
        this.dataSetService = dataSetService;
    }
}

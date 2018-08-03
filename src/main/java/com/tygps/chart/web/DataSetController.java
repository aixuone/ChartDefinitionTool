package com.tygps.chart.web;

import com.tygps.chart.domain.DWFDataSet;
import com.tygps.chart.domain.DWFDataSetColumn;
import com.tygps.chart.service.ChartResponse;
import com.tygps.chart.service.DataSetService;
import com.tygps.chart.tools.ChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataSetController {
    private DataSetService dataSetService;

    @RequestMapping(value = "/dataSets/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public ChartResponse getDataSets(@PathVariable String userID){
//    public String getDataSets(HttpServletRequest request, @PathVariable String userId){

        System.out.println("GET请求：获取数据集。userID="+userID);
        List<DWFDataSet> dataSets  = dataSetService.getDataSets(userID);

        return ChartUtil.returnSuccessResponse(dataSets);
//        return new HashMap().put("success", "true");
    }

    @RequestMapping(value="/dataSets/{dataSetID}/columns", method = RequestMethod.GET)
    @ResponseBody
    public List<DWFDataSetColumn> getColunms(@PathVariable String dataSetID){
        System.out.println("GET请求：获取数据集字段。dataSetID="+dataSetID);

        List<DWFDataSetColumn> columns = dataSetService.getColumns(dataSetID);
        return columns;
    }

    @Autowired
    public void setDataSetService(DataSetService dataSetService){
        this.dataSetService = dataSetService;
    }
}

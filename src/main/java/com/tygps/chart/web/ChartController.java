package com.tygps.chart.web;

import com.tygps.chart.domain.ChartDefinition;
import com.tygps.chart.domain.ChartRelatedColumn;
import com.tygps.chart.domain.ColumnRelation;
import com.tygps.chart.service.ChartResponse;
import com.tygps.chart.service.ChartService;
import com.tygps.chart.tools.ChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @RequestMapping(value="/data/{userID}/{chartID}", method = GET)
    public ChartResponse getData(@PathVariable String userID, @PathVariable String chartID){

        System.out.println("GET请求：获取表单数据。userID="+userID);
        Map retInfo = chartService.getChartData(userID, chartID);
        return ChartUtil.returnSuccessResponse(retInfo);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ChartResponse saveChartDefinition(@RequestBody ChartDefinition chart ){


        System.out.println("POST请求：保存表单定义");

        String chartID = chartService.saveChartDefinition(chart);

        return ChartUtil.returnSuccessResponse(chartID);
    }

    @RequestMapping(value = "/columns/relation", method = GET)
    public ChartResponse getRelatedColumns(){

        System.out.println("GET请求：获取穿透字段。");
        List<ColumnRelation> columns = chartService.getRelatedColumns();

        return ChartUtil.returnSuccessResponse(columns);

    }

    @RequestMapping(value = "/columns/relation", method = RequestMethod.POST)
    public ChartResponse addRelatedColumns(@RequestBody ChartRelatedColumn column){

        System.out.println("GET请求：新增穿透字段。");
        String uuid = chartService.addRelatedColumns(column);

        return ChartUtil.returnSuccessResponse(uuid);
    }

    public void setChartService(ChartService chartService){
        this.chartService = chartService;
    }
}

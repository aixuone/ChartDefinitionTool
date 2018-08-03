package com.tygps.chart.web;

import com.tygps.chart.domain.ChartDefinition;
import com.tygps.chart.domain.ChartRelatedColumn;
import com.tygps.chart.domain.RelatedColumn;
import com.tygps.chart.service.ChartResponse;
import com.tygps.chart.service.ChartService;
import com.tygps.chart.tools.ChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {

    @Autowired
    private ChartService chartService;


    @RequestMapping(value ="/charts", method = RequestMethod.POST)
    public ChartResponse saveChartDefinition(@RequestBody ChartDefinition chart ){


        System.out.println("POST请求：保存表单定义");

        String chartID = chartService.saveChartDefinition(chart);

        return ChartUtil.returnSuccessResponse(chartID);
    }

    @RequestMapping(value = "/charts/columns/relation", method = RequestMethod.GET)
    public ChartResponse getRelatedColumns(){

        System.out.println("GET请求：获取穿透字段。");
        List<RelatedColumn> columns = chartService.getRelatedColumns();

        return ChartUtil.returnSuccessResponse(columns);

    }

    @RequestMapping(value = "/charts/columns/relation", method = RequestMethod.POST)
    public ChartResponse addRelatedColumns(@RequestBody ChartRelatedColumn column){

        System.out.println("GET请求：新增穿透字段。");
        String uuid = chartService.addRelatedColumns(column);

        return ChartUtil.returnSuccessResponse(uuid);
//        return "add related column";
    }

    public void setChartService(ChartService chartService){
        this.chartService = chartService;
    }
}

package com.tygps.chart.tools;

import com.tygps.chart.service.ChartResponse;
import com.tygps.chart.web.ChartGC;

import java.util.UUID;

public class ChartUtil {
    public static ChartResponse returnSuccessResponse(Object data){
        ChartResponse response = new ChartResponse();
        response.setStatue(ChartGC.HTTP_OK);
        response.setSuccess(ChartGC.SUCCESS);
        response.setMessage(ChartGC.MESSAGE_SUCCESS);
        response.setData(data);
        return response;
    }

    public static  String returnUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

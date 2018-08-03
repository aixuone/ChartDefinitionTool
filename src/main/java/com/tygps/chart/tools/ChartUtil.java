package com.tygps.chart.tools;

import com.tygps.chart.service.ChartResponse;
import com.tygps.chart.web.ChartGC;

public class ChartUtil {
    public static ChartResponse returnSuccessResponse(Object data){
        ChartResponse response = new ChartResponse();
        response.setStatue(ChartGC.HTTP_OK);
        response.setSuccess(ChartGC.SUCCESS);
        response.setMessage(ChartGC.MESSAGE_SUCCESS);
        response.setData(data);
        return response;
    }
}

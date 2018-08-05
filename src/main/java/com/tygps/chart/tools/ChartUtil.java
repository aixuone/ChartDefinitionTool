package com.tygps.chart.tools;

import com.tygps.chart.service.ChartResponse;
import com.tygps.chart.web.ChartGC;

import java.util.*;

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

    public static Map hasAndReturnMapFromMap(Map map, String key) {
        if(!map.containsKey(key)) {
            Map tmp = new HashMap();
            map.put(key, tmp);
        }
        return (Map) map.get(key);
        }

    public static List hasAndReturnMapFromList(Map map, String key) {
        if(!map.containsKey(key)){
            List list = new ArrayList();
            map.put(key, list);
        }
        return (List) map.get(key);
    }
}

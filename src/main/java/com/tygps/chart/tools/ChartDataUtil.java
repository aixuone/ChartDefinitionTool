package com.tygps.chart.tools;

import com.tygps.chart.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartDataUtil {
    //系列不能和其他字段重复

    //当作查询条件
    public static void parseRelation(List<ColumnRelation> relatedColumns, List<String> conflictFields) {
        for (ColumnRelation relatedColumn : relatedColumns) {

        }
    }

    //当作查询结果
    public static List<String> parseValue(List<ColumnValue> valueColumns, List<String> conflictFields) {
        List<String> values = new ArrayList<String>();

        for (ColumnValue valueColumn : valueColumns) {
            conflictFields.add(valueColumn.getColumnID());
            if(ChartPolymer.COUNT.equals(valueColumn.getColumnPolymer())){
                values.add("COUNT("+valueColumn.getColumnID()+") AS COUNT_"+valueColumn.getColumnID());
            }else if(ChartPolymer.SUM.equals(valueColumn.getColumnPolymer())){
                values.add("COUNT(INTEGER("+valueColumn.getColumnID()+")) AS COUNT_"+valueColumn.getColumnID());
            }else{
                values.add(valueColumn.getColumnID());
            }
        }

        return values;
    }

    public static List<String> parseAxis(List<ColumnAxis> axisColumns, List<String> conflictFields) {
        List<String> groups = new ArrayList<String>();
        for (ColumnAxis axisColumn : axisColumns) {
            conflictFields.add(axisColumn.getColumnID());
            groups.add(axisColumn.getColumnID());
        }
        return groups;
    }

    //系列暂时只支持一个字段
    public static List<String> parseSeries(List<ColumnSeries> seriesColumns, List<String> conflictFields) {
        ColumnSeries series = seriesColumns.get(0);
        if(conflictFields.contains(series.getColumnID())) return null;
        List<String> serieses = new ArrayList<String>();
        serieses.add(series.getColumnID());
        return serieses;
    }

    public static String buildSeriesSQL(String dataSetID, List<String> serieses) {
        String sql = "SELECT "+serieses.get(0) + "FROM "+dataSetID+" GROUP BY "+serieses;
        return sql;
    }

    public static String buildChartDataSQL(String dataSetID, List<String> selFields, List<String> groupFields,
                                           List<String> seriesFields) {
        StringBuilder sql = new StringBuilder("SELECT ");
        for (String selField : selFields) {
            sql.append(selField+",");
        }
        sql.delete(sql.length(), 1);
        sql.append(" FROM "+ dataSetID);
        for (String groupField : groupFields) {
            sql.append(" GROUP BY "+groupFields+",");
        }

        sql.delete(sql.length(), 1);
        for (String seriesField : seriesFields) {
            sql.append(" ORDER BY "+seriesField+",");
        }
        sql.delete(sql.length(), 1);
        return sql.toString();
    }

    public static List<List> convertEchartStyle(List<Map<String, Object>> middleResult,
                                                List<String> seriesFields, List<String> axisFields) {
        List<List> retList = new ArrayList<List>();

        /*
         	    Forest	Steppe	Desert	Wetland
        2012	320	    220     150     0
        2013	332	    182     0       77
        2014	301     191     201     101
        2015	334     0       154     99
        2016	0       290     190     40
         */
        //系列字段转换
        if(seriesFields.size()>0){
            retList.add(seriesFields);
        }else{
            retList.add(new ArrayList());
        }

        //数据转换
        List dataList =


    }
}

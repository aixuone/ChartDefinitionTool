package com.tygps.chart.tools;

import com.tygps.chart.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static void parseValue(List<ColumnValue> valueColumns, ChartDataSQL sql) {

        for (ColumnValue valueColumn : valueColumns) {
            sql.setConflictFields(valueColumn.getColumnID());
            if(ChartPolymer.COUNT.toString().equals(valueColumn.getColumnPolymer())){
                sql.setValueFields("COUNT("+valueColumn.getColumnID()+") " +
                        "AS COUNT_"+valueColumn.getColumnID(), ChartDataSQL.Index.TAIL);
            }else if(ChartPolymer.SUM.toString().equals(valueColumn.getColumnPolymer())){
                sql.setValueFields("SUM(INTEGER("+valueColumn.getColumnID()+")) " +
                        "AS SUM_"+valueColumn.getColumnID(), ChartDataSQL.Index.TAIL);
            }else{
                sql.setValueFields(valueColumn.getColumnID(), ChartDataSQL.Index.TAIL);
            }
        }
    }

    public static void parseAxis(List<ColumnAxis> axisColumns, ChartDataSQL sql) {
        for (ColumnAxis axisColumn : axisColumns) {
            sql.setConflictFields(axisColumn.getColumnID());
            if(ChartPolymer.GROUP.toString().equals(axisColumn.getColumnPolymer())) {
                sql.setGroupFields(axisColumn.getColumnID(), ChartDataSQL.Index.HEAD);
            }
            sql.setValueFields(axisColumn.getColumnID(), ChartDataSQL.Index.TAIL);
            sql.setOrderFields(axisColumn.getColumnID(), ChartDataSQL.Index.HEAD);
        }
    }

    //系列暂时只支持一个字段
    public static void parseSeries(List<ColumnSeries> seriesColumns, ChartDataSQL sql) {
        ColumnSeries series = seriesColumns.get(0);
        if(sql.getConflictFields().contains(series.getColumnID())) return ;
        sql.setGroupFields(series.getColumnID(), ChartDataSQL.Index.TAIL);
        sql.setValueFields(series.getColumnID(), ChartDataSQL.Index.TAIL);
        sql.setOrderFields(series.getColumnID(), ChartDataSQL.Index.TAIL);
    }
/*
    public static String buildSeriesSQL(String dataSetID, List<String> serieses) {
        String sql = "SELECT "+serieses.get(0) + "FROM "+dataSetID+" GROUP BY "+serieses;
        return sql;
    }*/


    public static Map convertEchartStyle(List<Map<String, Object>> middleResult,
            List<ColumnAxis> axisColumns, List<ColumnValue> valueColumns, List<ColumnSeries> seriesColumns, List<String> retSeries) {

        /*
         	    Forest	Steppe	Desert	Wetland
        2012	320	    220     150     0
        2013	332	    182     0       77
        2014	301     191     201     101
        2015	334     0       154     99
        2016	0       290     190     40
         */

        //轴->系列->值

        Map retInfo = new HashMap();

        Map<String, List<String>> retAxises = new HashMap<String, List<String>>();
//        Map<String, Map<String, List>> retValues = new HashMap<String, Map<String, List>>();
        List[] valuesAry = new ArrayList[retSeries.size()];

        int seriesIndex = 0;
        Map<String, List<Integer>> needAddNull = new HashMap<String, List<Integer>>();
        if(retSeries==null || retSeries.size()==0){
            retSeries.add("PUBLIC");
        }
        for (Map<String, Object> tmp : middleResult) {
            for (ColumnAxis axisColumn : axisColumns) {
                List axises = ChartUtil.hasAndReturnMapFromList(retAxises, axisColumn.getColumnID());
                if(!axises.contains(tmp.get(axisColumn.getColumnID()))){
                    axises.add(tmp.get(axisColumn.getColumnID()));
                    break;
                }
            }


                for (ColumnValue valueColumn : valueColumns) {
                    String fieldAlias = ChartDataUtil.returnFieldAlias(valueColumn.getColumnID(),
                            ChartPolymer.valueOf(valueColumn.getColumnPolymer()));
                    if (valuesAry[seriesIndex] == null) valuesAry[seriesIndex] = new ArrayList();
                    if (seriesColumns.size() > 0) {
                        //暂时只支持单系列字段
                        //根据系列个数建立值的分组
                        if (retSeries.get(0).equals("PUBLIC") || !retSeries.get(seriesIndex).equals(
                                tmp.get(seriesColumns.get(0).getColumnID()))) {
                            valuesAry[seriesIndex].add(valuesAry[seriesIndex].size(), 0);
                            seriesIndex++;
                        }
                        valuesAry[seriesIndex].add(tmp.get(fieldAlias));
                    }
                }
                if(seriesIndex==retSeries.size()-1){
                    seriesIndex = 0;
                }else {
                    seriesIndex++;
                }
        }

        retInfo.put("axis", retAxises);
        retInfo.put("value", valuesAry);

        return retInfo;
    }

    public static String getSeriesResultSQL(String dataSetID, List<ColumnSeries> serieses) {

        StringBuilder sqlB = new StringBuilder("SELECT DISTINCT ");
        String seriesField = "";
        for (ColumnSeries s : serieses) {
            seriesField+=s.getColumnID()+",";
        }
        seriesField = seriesField.substring(0, seriesField.length()-1);
        sqlB.append(seriesField);

        sqlB.append(" FROM "+dataSetID);

        sqlB.append(" GROUP BY "+seriesField);

        sqlB.append(" ORDER BY "+seriesField);

        return sqlB.toString();
    }

    public static List<String> getReturnSeries(List<Map<String, Object>> serieses) {
        List<String> retSerieses = new ArrayList<String>();

        for (Map<String, Object> tmp : serieses) {
            for (String s : tmp.keySet()) {
                retSerieses.add((String) tmp.get(s));
            }
        }
        return retSerieses;
    }

    public static String returnFieldAlias(String fieldName, ChartPolymer polymer){
        if(polymer!=null){
            return polymer.toString()+"_"+fieldName;
        }
        return fieldName;
    }
}

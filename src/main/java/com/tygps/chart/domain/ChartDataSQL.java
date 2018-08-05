package com.tygps.chart.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChartDataSQL {
    private String dataSetID;
    private Set<String> conflictFields = new HashSet<String>();
    private List<String> valueFields = new ArrayList<String>();;
    private List<String> groupFields = new ArrayList<String>();;
    private List<String> orderFields = new ArrayList<String>();;

    public void setOrderFields(String field, Index index) {
        if(orderFields.contains(field)) {
            if (Index.HEAD.equals(index)) {
                orderFields.remove(field);
            }else{ return;}
        }
        setFields(field, index, Type.ORDER);
    }

    public static enum Index {HEAD,TAIL};
    private enum Type {VALUE, GROUP, ORDER};

    public ChartDataSQL(String dataSetID) {
        this.dataSetID = dataSetID;
    }

    public void setDataSetID(String dataSetID) {
        this.dataSetID = dataSetID;
    }

    public void setValueFields(String field, Index index) {
        if(valueFields.contains(field)) return;
        setFields(field, index, Type.VALUE);
    }

    public void setGroupFields(String field, Index index){
        setFields(field, index, Type.GROUP);
    }

    private void setFields(String field, Index index, Type type){
        List fieldList = null;
        if(Type.VALUE.equals(type)){
            fieldList = valueFields;
        }else if(Type.GROUP.equals(type)){
            fieldList = groupFields;
        }else if(Type.ORDER.equals(type)){
            fieldList = orderFields;
        }

        if(Index.HEAD.equals(index)){
            fieldList.add(0, field);
        }else{
            fieldList.add(field);
        }
    }

    public void setConflictFields(String field){
        conflictFields.add(field);
    }

    public Set<String> getConflictFields() {
        return conflictFields;
    }


    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder("SELECT ");
        for (String valueField : valueFields) {
            sql.append(valueField+",");
        }
        sql.delete(sql.length()-1,sql.length());
        sql.append(" FROM "+ dataSetID+ " ");
        if(groupFields.size()>0) {
            sql.append(" GROUP BY ");
            for (String groupField : groupFields) {
                sql.append(groupField + ",");
            }
            sql.delete(sql.length()-1,sql.length());
        }
        if(orderFields.size()>0) {
            sql.append(" ORDER BY ");
            for (String orderField : orderFields) {
                sql.append(orderField + ",");
            }
            sql.delete(sql.length() - 1, sql.length());
        }
        return sql.toString();
    }
}

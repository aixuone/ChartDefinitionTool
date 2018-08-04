package com.tygps.chart.tools;

public class ChartSQLInsert {

    //T_CHART_COLUMN_VALUE
    public static final String INSERT_COLUMN_VALUE = "INSERT INTO T_CHART_COLUMN_VALUE " +
            "(CHART_DEF_PK, COLUMN_VALUE_ID, COLUMN_VALUE_POLYMER) VALUES (?, ?, ?)";

    //t_chart_column_relation
    public final static String INSERT_RELATED_COLUMNS = "INSERT INTO t_chart_column_relation " +
            "(column_rel_name,column_rel_uuid) VALUES (?, ?)";

    //T_CHART_DEFINITION
    public final static String INSERT_CHART_DEFINITION = "INSERT INTO T_CHART_DEFINITION " +
            "(CHART_ID,  CHART_TYPE, CHART_THEME, CHART_DATASET)  VALUES  (?,?,?,?)";

    //T_CHART_COLUMN_AXIS
    public final static String INSERT_COLUMN_AXIS = "INSERT INTO T_CHART_COLUMN_AXIS " +
            "(CHART_DEF_PK,COLUMN_AIXS_ID, COLUMN_AIXS_POLYMER) VALUES (?,?,?)";

    //T_CHART_COLUMN_SERIES
    public static final String INSERT_COLUMN_SERIES = "INSERT INTO T_CHART_COLUMN_SERIES"+
            "(CHART_DEF_PK, COLUMN_SERIES_ID) VALUES (?,?)";
}

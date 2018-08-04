package com.tygps.chart.tools;

public class ChartSQLSelect {

    //t_chart_column_relation
    public final static String SELECT_COLUMNS_RELATED = "SELECT DISTINCT column_rel_name,column_rel_uuid"+
            " FROM t_chart_column_relation";
    public final static String SELECT_COLUMNS_RELATED_BY_CHARTID = "SELECT * "+
            " FROM t_chart_column_relation WHERE CHART_DEF_PK=?";

    //t_chart_column_value
    public final static String SELECT_COLUMN_VALUE_BY_CHARTID = "SELECT * FROM T_CHART_COLUMN_VALUE "
            + " WHERE CHART_DEF_PK=? ";

    //T_CHART_COLUMN_AXIS
    public final static String SELECT_COLUMN_AXIS_BY_CHARTID = "SELECT * FROM T_CHART_COLUMN_AXIS "+
            " WHERE CHART_DEF_PK=?";

    public final static String SELECT_COLUMN_SERIES_BY_CHARTID = "SELECT * FROM T_CHART_COLUMN_SERIES" +
            "WHERE CHART_DEF_PK=?";
}

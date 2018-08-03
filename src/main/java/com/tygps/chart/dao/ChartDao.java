package com.tygps.chart.dao;

import com.tygps.chart.domain.*;
import com.tygps.chart.tools.ChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ChartDao {

    private static final String INSERT_COLUMN_VALUE = "INSERT INTO T_CHART_COLUMN_VALUE " +
            "(CHART_DEF_PK, COLUMN_VALUE_ID, COLUMN_VALUE_POLYMER) VALUES (?, ?, ?)";
    private static final String UPDATE_COLUMN_RELATION = "UPDATE T_CHART_COLUMN_RELATION " +
            "SET COLUMN_REL_ID=?, CHART_DEF_PK=? WHERE COLUMN_REL_UUID=?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private final static String SELECT_RELATED_COLUMNS= "SELECT DISTINCT column_rel_name,column_rel_uuid"+
            " FROM t_chart_column_relation";
    private final static String INSERT_RELATED_COLUMNS = "INSERT INTO t_chart_column_relation " +
            "(column_rel_name,column_rel_uuid) VALUES (?, ?)";
    private final static String INSERT_CHART_DEFINITION = "INSERT INTO T_CHART_DEFINITION " +
            "(CHART_ID,  CHART_TYPE, CHART_THEME, CHART_DATASET)  VALUES  (?,?,?,?)";
    private final static String INSERT_COLUMN_AXIS = "INSERT INTO T_CHART_COLUMN_AXIS " +
            "(CHART_DEF_PK,COLUMN_AIXS_ID, COLUMN_AIXS_POLYMER) VALUES (?,?,?)";
    public int insert(Chart chart){
        return 0;
    }

    public List<RelatedColumn> selectRelatedColumn() {

        List<RelatedColumn> columns = new ArrayList<RelatedColumn>();
        List columnsTmp = jdbcTemplate.queryForList(SELECT_RELATED_COLUMNS);
        for (Object o : columnsTmp) {
            LinkedCaseInsensitiveMap tmp = (LinkedCaseInsensitiveMap) o;
            RelatedColumn column = new RelatedColumn();
            column.setRelatedColumnUUID((String) tmp.get("COLUMN_REL_UUID"));
            column.setRelatedColumnName((String) tmp.get("COLUMN_REL_NAME"));
            columns.add(column);
        }
        return columns;
    }

//    @Transactional
    public String insertRelatedColumn(String relatedColumnName) {

        final String uuid = ChartUtil.returnUUID();
        int rst = jdbcTemplate.update(INSERT_RELATED_COLUMNS, new Object[]{relatedColumnName, uuid});
        System.out.println("INSERT_RELATED_COLUMNS="+rst);
        return uuid;
    }

    public String insertChartDefinition(ChartDefinition chart) {

        String chartID = ChartUtil.returnUUID();

        for (ColumnAxis axis : chart.getColumnAxises()) {
            jdbcTemplate.update(INSERT_COLUMN_AXIS, new Object[]{chartID, axis.getColumnID(),
                    axis.getColumnPolymer()});
        }

        for (ColumnValue value : chart.getColumnValues()) {
            jdbcTemplate.update(INSERT_COLUMN_VALUE, new Object[]{chartID, value.getColumnID(),
                    value.getColumnPolymer().toString()});
        }

        for (ColumnRelation columnRelation : chart.getColumnRelations()) {
            jdbcTemplate.update(UPDATE_COLUMN_RELATION, new Object[]{columnRelation.getColumnID(),
            chartID, columnRelation.getRelatedColumnUUID()});
        }
        jdbcTemplate.update(INSERT_CHART_DEFINITION, new Object[]{chartID, chart.getChartType(),
                chart.getChartTheme(), chart.getDataSetID() });

        return chartID;
    }
}

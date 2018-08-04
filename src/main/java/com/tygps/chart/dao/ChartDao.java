package com.tygps.chart.dao;

import com.tygps.chart.domain.*;
import com.tygps.chart.tools.ChartSQLInsert;
import com.tygps.chart.tools.ChartSQLSelect;
import com.tygps.chart.tools.ChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;

import static com.tygps.chart.tools.ChartSQLSelect.*;

@Repository
public class ChartDao {

    private static final String UPDATE_COLUMN_RELATION = "UPDATE T_CHART_COLUMN_RELATION " +
            "SET COLUMN_REL_ID=?, CHART_DEF_PK=? WHERE COLUMN_REL_UUID=?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

  public int insert(Chart chart){
        return 0;
    }

    private List<ColumnRelation> selectRelatedColumn() {

        List<ColumnRelation> columns = new ArrayList<ColumnRelation>();
        List columnsTmp = jdbcTemplate.queryForList(ChartSQLSelect.SELECT_COLUMNS_RELATED);
        for (Object o : columnsTmp) {
            LinkedCaseInsensitiveMap tmp = (LinkedCaseInsensitiveMap) o;
            ColumnRelation column = new ColumnRelation();
            column.setRelatedColumnUUID((String) tmp.get("COLUMN_REL_UUID"));
            column.setRelatedColumnName((String) tmp.get("COLUMN_REL_NAME"));
            columns.add(column);
        }
        return columns;
    }

//    @Transactional
    public String insertRelatedColumn(String relatedColumnName) {

        final String uuid = ChartUtil.returnUUID();
        int rst = jdbcTemplate.update(ChartSQLInsert.INSERT_RELATED_COLUMNS, new Object[]{relatedColumnName, uuid});
        System.out.println("INSERT_RELATED_COLUMNS="+rst);
        return uuid;
    }

    public String insertChartDefinition(ChartDefinition chart) {

        String chartID = ChartUtil.returnUUID();

        for (ColumnAxis axis : chart.getColumnAxises()) {
            jdbcTemplate.update(ChartSQLInsert.INSERT_COLUMN_AXIS, new Object[]{chartID, axis.getColumnID(),
                    axis.getColumnPolymer()});
        }

        for (ColumnValue value : chart.getColumnValues()) {
            jdbcTemplate.update(ChartSQLInsert.INSERT_COLUMN_VALUE, new Object[]{chartID, value.getColumnID(),
                    value.getColumnPolymer().toString()});
        }

        for (ColumnRelation columnRelation : chart.getColumnRelations()) {
            jdbcTemplate.update(UPDATE_COLUMN_RELATION, new Object[]{columnRelation.getRelatedColumnID(),
            chartID, columnRelation.getRelatedColumnUUID()});
        }

        for (ColumnSeries columnSeries : chart.getColumnSerises()) {
            jdbcTemplate.update(ChartSQLInsert.INSERT_COLUMN_SERIES, new Object[]{columnSeries.getColumnID(),
                    chartID, columnSeries.getColumnID()});
        }
        jdbcTemplate.update(ChartSQLInsert.INSERT_CHART_DEFINITION, new Object[]{chartID, chart.getChartType(),
                chart.getChartTheme(), chart.getDataSetID() });

        return chartID;
    }

    public List<ColumnRelation> selectRelatedColumn(String chartID) {
        if(chartID==null){
            return selectRelatedColumn();
        }else{
            List<ColumnRelation> columns = new ArrayList<ColumnRelation>();
            List columnsTmp = jdbcTemplate.queryForList(SELECT_COLUMNS_RELATED_BY_CHARTID, chartID);
            for (Object o : columnsTmp) {
                LinkedCaseInsensitiveMap tmp = (LinkedCaseInsensitiveMap) o;
                ColumnRelation column = new ColumnRelation();
                column.setRelatedColumnUUID((String) tmp.get("COLUMN_REL_ID"));
                column.setRelatedColumnUUID((String) tmp.get("COLUMN_REL_UUID"));
                columns.add(column);
            }
            return columns;
        }
    }

    public List<ColumnValue> selectValueColumn(String chartID) {
        List<ColumnValue> columns = new ArrayList<ColumnValue>();
        List rstList = jdbcTemplate.queryForList(SELECT_COLUMN_VALUE_BY_CHARTID, chartID);
        for (Object o : rstList) {
            LinkedCaseInsensitiveMap tmp = (LinkedCaseInsensitiveMap) o;
            ColumnValue column = new ColumnValue();
            column.setColumnID((String) tmp.get("COLUMN_VALUE_ID"));
            column.setColumnPolymer((String) tmp.get("COLUMN_VALUE_POLYMER"));
            column.setColumnDispType((String) tmp.get("COLUMN_VALUE_DISP"));
            columns.add(column);
        }
        return columns;
    }

    public List<ColumnAxis> selectAxisColumn(String chartID) {
        List<ColumnAxis> columns = new ArrayList<ColumnAxis>();
        List rstList = jdbcTemplate.queryForList(SELECT_COLUMN_AXIS_BY_CHARTID, chartID);
        for (Object o : rstList) {
            LinkedCaseInsensitiveMap tmp = (LinkedCaseInsensitiveMap) o;
            ColumnAxis column = new ColumnAxis();
            column.setColumnID((String) tmp.get("COLUMN_VALUE_ID"));
            column.setColumnPolymer((String) tmp.get("COLUMN_VALUE_POLYMER"));
            columns.add(column);
        }
        return columns;
    }

    public List<ColumnSeries> selectSeriesColumn(String chartID) {
        List<ColumnSeries> columns = new ArrayList<ColumnSeries>();
        List rstList = jdbcTemplate.queryForList(SELECT_COLUMN_SERIES_BY_CHARTID, chartID);
        for (Object o : rstList) {
            LinkedCaseInsensitiveMap tmp = (LinkedCaseInsensitiveMap) o;
            ColumnSeries column = new ColumnSeries();
            column.setColumnID((String) tmp.get("COLUMN_VALUE_ID"));
            columns.add(column);
        }
        return columns;
    }
}

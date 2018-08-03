package com.tygps.chart.dao;

import com.tygps.chart.domain.Chart;
import com.tygps.chart.domain.RelatedColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ChartDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private final static String SELECT_RELATED_COLUMNS= "SELECT DISTINCT column_rel_name,column_rel_uuid"+
            " FROM t_chart_column_relation";
/*

    private final static String INSERT_RELATED_COLUMNS = "INSERT INTO t_chart_column_relation " +
            "(column_rel_name,column_rel_uuid) VALUES (";
*/
    private final static String INSERT_RELATED_COLUMNS = "INSERT INTO t_chart_column_relation " +
            "(column_rel_name,column_rel_uuid) VALUES (?, ?)";

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

        final String uuid = String.valueOf(UUID.randomUUID()).replaceAll("-", "");
//        final String uuid = String.valueOf(Math.random());
//        int rst = jdbcTemplate.update(INSERT_RELATED_COLUMNS, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                preparedStatement.setString(1, relatedColumnName);
//                preparedStatement.setString(2, uuid);
//            }
//        });

//        int rst = jdbcTemplate.update(INSERT_RELATED_COLUMNS+"'"+relatedColumnName+"','"+uuid+"')");

        int rst = jdbcTemplate.update(INSERT_RELATED_COLUMNS, new Object[]{relatedColumnName, uuid});

/*
        int rst = 0;

        Connection conn;
        PreparedStatement pstmt = null;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            String sql = INSERT_RELATED_COLUMNS+"'"+relatedColumnName+"','"+uuid+"')";
            System.out.println(sql);
            rst = statement.executeUpdate(sql);
            */
/*pstmt = conn.prepareStatement(INSERT_RELATED_COLUMNS);
            pstmt.setString(1, relatedColumnName);
            pstmt.setString(2, uuid);
            rst = pstmt.executeUpdate();
            *//*

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
        System.out.println("INSERT_RELATED_COLUMNS="+rst);
        return uuid;
    }
}

package com.tygps.chart.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ChartDataDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> selectChartData(String s) {
        return jdbcTemplate.queryForList(s);
    }

    public List<Map<String, Object>> select(String seriesResultSQL) {
        return jdbcTemplate.queryForList(seriesResultSQL);
    }
}

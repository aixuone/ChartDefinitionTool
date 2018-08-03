package com.tygps.chart.dao;

import com.tygps.chart.domain.DWFDataSet;
import com.tygps.chart.domain.DWFDataSetColumn;
import com.tygps.chart.domain.TYUser;
import com.tygps.chart.service.ChartDataSetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataSetDao {
    private JdbcTemplate jdbcTemplate;

    private final static String SELECT_SYS_COLUNM_SQL = "SELECT c.columnname FROM " +
            "sys.syscolumns c,sys.systables t " +
            "WHERE c.referenceid=t.tableid AND t.tablename=?";

    //保存登陆日志SQL
    private final static String INSERT_LOGIN_LOG_SQL= "INSERT INTO app.t_login_log(user_id,ip,login_datetime) VALUES(?,?,?)";

    public List<DWFDataSet> getDataSets(TYUser tyUser) {
        List<DWFDataSet> dataSets = new ArrayList<DWFDataSet>();

        DWFDataSet dataSet = new DWFDataSet();
        dataSet.setDataSetID("T_FAILURHANDLE");
        dataSet.setDataSetName("测试数据集-故障处理表");
        dataSet.setDataSetType(ChartDataSetType.DERBY);
        dataSets.add(dataSet);

        dataSet = new DWFDataSet();
        dataSet.setDataSetID("0002");
        dataSet.setDataSetName("开发环境数据集");
        dataSet.setDataSetType(ChartDataSetType.MYSQL);
        dataSets.add(dataSet);

        return dataSets;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DWFDataSetColumn> getColumns(DWFDataSet dataSet) {
        List<DWFDataSetColumn> columns = new ArrayList<DWFDataSetColumn>();

/*        List colTmp = jdbcTemplate.queryForList("SELECT c.columnname FROM sys.syscolumns c,sys.systables t WHERE c" +
                ".referenceid=t.tableid AND t.tablename='"+dataSet.getDataSetID()+"'");*/
        List colTmp = jdbcTemplate.queryForList(SELECT_SYS_COLUNM_SQL, dataSet.getDataSetID());

        for (Object o : colTmp) {
            LinkedCaseInsensitiveMap tmp = (LinkedCaseInsensitiveMap) o;
            DWFDataSetColumn column = new DWFDataSetColumn();
            column.setColumnID((String) tmp.get("COLUMNNAME"));
            column.setColumnName(getColumnName((String) tmp.get("COLUMNNAME")));
            columns.add(column);
        }

        return columns;
    }

    private String getColumnName(String columnID) {
        if(("ISVALID").equals(columnID)) return "审核";
        if(("MACHINEID").equals(columnID)) return "机号";
        if(("USERNAME").equals(columnID)) return "用户名称";
        if(("TASKNO").equals(columnID)) return "任务号";
        if(("REPORTTIME").equals(columnID)) return "报告时间";
        if(("REPORTAREA").equals(columnID)) return "区域";
        if(("SERVICEOPER").equals(columnID)) return "服务人员";
        if(("SUPPORTTYPE").equals(columnID)) return "是否支援";
        if(("DEADLINE").equals(columnID)) return "最迟完成日期";
        if(("SERVICEDATE").equals(columnID)) return "服务日期";
        if(("FAILUREDATE").equals(columnID)) return "故障发生日期";
        if(("BRAND").equals(columnID)) return "品牌";
        if(("REPORTWORKHOUR").equals(columnID)) return "工作小时(上报)";
        if(("MONWORKHOUR").equals(columnID)) return "工作小时(监控)";
        if(("DOWN").equals(columnID)) return "是否停机";
        if(("FAILURCONTENT").equals(columnID)) return "故障内容";
        if(("RESULT").equals(columnID)) return "结果内容";
        if(("UNFINISHRESULT").equals(columnID)) return "未完成原因";
        if(("REPAIR").equals(columnID)) return "是否修复";
        if(("UNREPAIRRESULT").equals(columnID)) return "未修复原因";
        if(("FAILURRESULT").equals(columnID)) return "故障原因";
        if(("FAILURCODE").equals(columnID)) return "故障代码";
        if(("FAILURTEXT").equals(columnID)) return "故障代码翻译后的文字";
        if(("WORKCODE").equals(columnID)) return "工作代码";
        if(("WORKTEXT").equals(columnID)) return "工作代码翻译后的文字";
        if(("SYSTEM.CHART").equals(columnID)) return "报表系统";
        if(("SYSTEM.SERVICE").equals(columnID)) return "服务系统";
        if(("SYSTEM.CREDIT").equals(columnID)) return "债务系统";
        if(("DATASOURCE").equals(columnID)) return "服务系统数据源";
        if(("PAID").equals(columnID)) return "是否有偿服务";

        return columnID;
    }
}

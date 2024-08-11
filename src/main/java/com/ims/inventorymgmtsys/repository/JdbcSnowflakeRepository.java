package com.ims.inventorymgmtsys.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcSnowflakeRepository implements SnowflakeRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcSnowflakeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//private static final String url = "jdbc:snowflake://koosbwr-bz61012.snowflakecomputing.com/?user=testmkasa&password=2wsx!QAZ&JDBC_QUERY_RESULT_FORMAT=JSON";
    private static final String url = System.getenv("SNOW_URL");

    @Override
    public List<Map<String, String >> findAll() throws SQLException, ClassNotFoundException {
        List<Map<String, String >> data = new ArrayList<>();
        Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM SNOWFLAKE_SAMPLE_DATA.TPCH_SF1.SUPPLIER")) {

            var metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i=1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = rs.getString(i);
                    row.put(columnName, columnValue);
                }
                data.add(row);
            }
        }
        return data;
    }

}

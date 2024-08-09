package com.ims.inventorymgmtsys.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SnowflakeService {

    List<Map<String, String>> getSnowflakeTestData() throws SQLException, ClassNotFoundException;
}

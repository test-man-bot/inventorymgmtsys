package com.ims.inventorymgmtsys.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SnowflakeRepository {
    List<Map<String, String >> findAll() throws SQLException, ClassNotFoundException;
}

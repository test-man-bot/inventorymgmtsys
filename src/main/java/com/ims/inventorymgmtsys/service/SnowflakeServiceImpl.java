package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.input.ConnectionDetails;
import com.ims.inventorymgmtsys.repository.JdbcSnowflakeRepository;
import com.ims.inventorymgmtsys.repository.SnowflakeRepository;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class SnowflakeServiceImpl implements SnowflakeService {

    private final SnowflakeRepository snowflakeRepository;

    public SnowflakeServiceImpl(SnowflakeRepository snowflakeRepository) {
        this.snowflakeRepository = snowflakeRepository;
    }

    @Override
    public List<Map<String, String>> getSnowflakeTestData() throws SQLException, ClassNotFoundException {
        return snowflakeRepository.findAll();
    }

    @Override
    public List<Map<String, String>> fetchData(ConnectionDetails connectionDetails) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        return snowflakeRepository.findData(connectionDetails);
    }


}

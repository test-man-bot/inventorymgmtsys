package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.service.SnowflakeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/external")
public class ExternalCollaboController {
    private final SnowflakeService snowflakeService;

    public ExternalCollaboController(SnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    @GetMapping("/snowflaketest")
    public String getSnowflakeTestData(Model model) throws SQLException, ClassNotFoundException {
        model.addAttribute("snowData", snowflakeService.getSnowflakeTestData());
        return "/admin/testSnowflake";
    }
}

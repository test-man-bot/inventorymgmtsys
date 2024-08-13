package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.input.ConnectionDetails;
import com.ims.inventorymgmtsys.service.SnowflakeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        return "admin/testSnowflake";
    }

    @GetMapping("/snowflakeinput")
    public String snowflakeInput(){
        return "admin/SnowflakeInput";
    }


    @PostMapping("/snowflake/connect")
    public String connectToSnowflake(@RequestParam String url, @RequestParam String username, @RequestParam String password, @RequestParam String dbname, @RequestParam String schemaname, @RequestParam String tablename ,Model model) throws UnsupportedEncodingException {
        ConnectionDetails connectionDetails = new ConnectionDetails(url, username, password, dbname, schemaname, tablename);

        try {
            model.addAttribute("snowData", snowflakeService.fetchData(connectionDetails));
            model.addAttribute("message", "データベースの接続に成功しました。");
        } catch (SQLException | ClassNotFoundException e) {
            model.addAttribute("error", "データベース接続エラー: " + e.getMessage());
        }
        return "admin/getSnowflake";
    }
}

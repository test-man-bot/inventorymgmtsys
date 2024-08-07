package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.repository.SalesRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SalesServiceImpl implements SalesService {
    private final SalesRepository salesRepository;

    public SalesServiceImpl(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public List<Map<String, Object>> getSalesData() {
//        return salesRepository.getSalesData();
        List<Map<String, Object>> salesData = salesRepository.getSalesData();
        salesData.forEach(System.out::println);
        return salesData;
    }

    public void generateSalesChart(HttpServletResponse httpServletResponse) throws IOException {
        List<Map<String, Object>> salesData = getSalesData();
        if (salesData == null || salesData.isEmpty()) {
            // デフォルトの画像などを返すことも可能です
            httpServletResponse.sendError(HttpServletResponse.SC_NO_CONTENT, "No sales data available");
            return;
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map<String, Object> sale : salesData) {
            String orderId = (String) sale.get("order_id");
            Number total = (Number) sale.get("total_yen");
            if (total != null) {
                dataset.addValue(total, "注文金額", orderId);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "注文別",
                "注文ID",
                "金額（円）",
                dataset
        );

        barChart.setBackgroundPaint(Color.white);
        httpServletResponse.setContentType("image/png");
        ChartUtils.writeChartAsPNG(httpServletResponse.getOutputStream(), barChart, 800, 600);
    }

    @Override
    public List<Map<String, Object>> getDailySalesData() {
        List<Map<String, Object>> salesData = salesRepository.getDailySalesData();
        salesData.forEach(System.out::println);
        return salesData;
    }

    @Override
    public void generateDailySalesChart(HttpServletResponse httpServletResponse) throws IOException {
        List<Map<String, Object>> salesData = getDailySalesData();
        if (salesData == null || salesData.isEmpty()) {
            // デフォルトの画像などを返すことも可能です
            httpServletResponse.sendError(HttpServletResponse.SC_NO_CONTENT, "No sales data available");
            return;
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map<String, Object> sale : salesData) {
            String date = (String) sale.get("date");
            Number total = (Number) sale.get("total_date_yen");
            if (total != null) {
                dataset.addValue(total, "売上金額（累積）", date);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "日次",
                "日付",
                "金額（円）",
                dataset
        );

        barChart.setBackgroundPaint(Color.white);
        httpServletResponse.setContentType("image/png");
        ChartUtils.writeChartAsPNG(httpServletResponse.getOutputStream(), barChart, 800, 600);
    }

}

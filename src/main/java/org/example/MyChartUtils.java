package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MyChartUtils {

    public static JFreeChart createBarChart(Map<String, Double> monthlyCosts) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Double> entry : monthlyCosts.entrySet()) {
            dataset.addValue(entry.getValue(), "Затраты", entry.getKey());
        }

        return ChartFactory.createBarChart(
                "Затраты по месяцам",
                "Месяц",
                "Сумма (руб.)",
                dataset
        );
    }

    public static void showBarChart(Map<String, Double> monthlyCosts) {
        JFreeChart chart = createBarChart(monthlyCosts);

        ApplicationFrame frame = new ApplicationFrame("График затрат");
        frame.setContentPane(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }

    public static void saveBarChartAsPNG(Map<String, Double> monthlyCosts, String filePath, int width, int height) {
        JFreeChart chart = createBarChart(monthlyCosts);
        try {
            ChartUtils.saveChartAsPNG(new File(filePath), chart, width, height);
            System.out.println("✅ График сохранён как: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении PNG: " + e.getMessage());
        }
    }
}

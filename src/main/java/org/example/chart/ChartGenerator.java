package org.example.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ChartGenerator {


    public static JFreeChart createBarChart(Map<String, Double> monthlyCosts) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Double> entry : monthlyCosts.entrySet()) {
            dataset.addValue(entry.getValue(), "Затраты", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Затраты по месяцам",
                "Месяц",
                "Сумма (руб.)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Полностью плоские колонки
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(63, 81, 181));
        renderer.setShadowVisible(false);
        renderer.setBarPainter(new StandardBarPainter()); // <--- отключает объём
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.1);
        renderer.setMaximumBarWidth(0.1);

        // Шрифты
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        chart.getTitle().setFont(titleFont);
        plot.getDomainAxis().setLabelFont(labelFont);
        plot.getDomainAxis().setTickLabelFont(labelFont);
        plot.getRangeAxis().setLabelFont(labelFont);

        return chart;
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
            System.out.println("✅ График сохранён в файл: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении PNG: " + e.getMessage());
        }
    }
}

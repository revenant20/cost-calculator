package org.example.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
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
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(63, 81, 181));
        renderer.setShadowVisible(false);
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.1);
        renderer.setMaximumBarWidth(0.1);

        // Увеличенные шрифты
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        chart.getTitle().setFont(titleFont);
        plot.getDomainAxis().setLabelFont(labelFont);
        plot.getDomainAxis().setTickLabelFont(labelFont);
        plot.getRangeAxis().setLabelFont(labelFont);

        return chart;
    }

    public static void saveChartAsPNG(JFreeChart chart, String filePath, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
        g2.dispose();

        try {
            ImageIO.write(image, "png", new File(filePath));
            System.out.println("✅ PNG сохранён: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении PNG: " + e.getMessage());
        }
    }

    public static void saveChartAsSVG(JFreeChart chart, String filePath, int width, int height) {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument(null, "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        svgGenerator.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        svgGenerator.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        svgGenerator.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        chart.draw(svgGenerator, new Rectangle2D.Double(0, 0, width, height));

        try (FileWriter out = new FileWriter(filePath)) {
            svgGenerator.stream(out, true);
            System.out.println("✅ SVG сохранён: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении SVG: " + e.getMessage());
        }
    }
}

package org.example;

import org.example.chart.ChartGenerator;
import org.example.model.Task;
import org.example.parser.TaskCSVParser;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        String filePath = "tasks.csv";

        List<Task> tasks = TaskCSVParser.parseTasks(filePath);

        tasks.forEach(System.out::println);

        Map<String, Double> monthlyCosts = new LinkedHashMap<>();
        for (Task task : tasks) {
            String key = task.getMonthYearKey();
            monthlyCosts.put(key, monthlyCosts.getOrDefault(key, 0.0) + task.getCost());
        }

        ChartGenerator.showBarChart(monthlyCosts);
        ChartGenerator.saveBarChartAsPNG(monthlyCosts, "monthly_costs_chart.png", 800, 600);
    }
}


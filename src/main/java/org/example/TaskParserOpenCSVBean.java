package org.example;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.*;

public class TaskParserOpenCSVBean {

    public static List<Task> parseCSV(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            CsvToBean<Task> csvToBean = new CsvToBeanBuilder<Task>(reader)
                    .withType(Task.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static Map<String, Double> calculateMonthlyCosts(List<Task> tasks) {
        Map<String, Double> monthlyCosts = new LinkedHashMap<>();

        for (Task task : tasks) {
            String key = task.getMonthYearKey();
            monthlyCosts.put(key, monthlyCosts.getOrDefault(key, 0.0) + task.getCost());
        }

        return monthlyCosts;
    }

    public static void main(String[] args) {
        String filePath = "tasks.csv";
        List<Task> tasks = parseCSV(filePath);

        for (Task task : tasks) {
            System.out.println(task);
        }

        System.out.println("\n💸 Потраченные деньги по месяцам:");
        Map<String, Double> monthlyCosts = calculateMonthlyCosts(tasks);
        for (Map.Entry<String, Double> entry : monthlyCosts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " руб.");
        }

        // Показать график
        MyChartUtils.showBarChart(monthlyCosts);

        // Сохранить график как PNG
        MyChartUtils.saveBarChartAsPNG(monthlyCosts, "monthly_costs_chart.png", 800, 600);
    }


}


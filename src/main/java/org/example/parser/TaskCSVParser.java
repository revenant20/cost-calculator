package org.example.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.example.model.Task;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class TaskCSVParser {

    public static List<Task> parseTasks(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return new CsvToBeanBuilder<Task>(reader)
                    .withType(Task.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (Exception e) {
            System.err.println("Ошибка при чтении CSV: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}


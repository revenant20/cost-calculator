package org.example.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Task {

    @CsvBindByName(column = "Название задачи")
    private String name;

    @CsvBindByName(column = "Стоимость")
    private double cost;

    @CsvBindByName(column = "Потраченное время")
    private double timeSpent;

    @CsvBindByName(column = "Дата начала")
    private String startDateStr;

    @CsvBindByName(column = "Дата начала")
    @CsvDate("yyyy-MM-dd")
    private Date startDate;

    public String getMonthYearKey() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String monthName = new DateFormatSymbols(new Locale("ru")).getMonths()[month];
        return monthName + " " + year;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Задача: " + name + ", Стоимость: " + cost +
                ", Потраченное время: " + timeSpent + ", Дата начала: " + startDateStr;
    }
}


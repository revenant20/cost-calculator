package org.example;

import com.opencsv.bean.CsvBindByName;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
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

    private Date startDate;

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.startDate = formatter.parse(startDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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


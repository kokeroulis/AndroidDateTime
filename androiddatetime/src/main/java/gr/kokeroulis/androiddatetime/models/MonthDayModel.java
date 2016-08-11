package gr.kokeroulis.androiddatetime.models;

import java.util.Date;
import java.util.List;

import gr.kokeroulis.androiddatetime.DataDateProvider;

public class MonthDayModel implements DateModel {
    private int dayOfYear;
    private int month;
    private int day;
    private final Date now = new Date();

    public MonthDayModel(int dayOfYear, int month, int day) {
        this.dayOfYear = dayOfYear;
        this.month = month;
        this.day = day;
    }

    @Override
    public String title() {
        return day + " " + MonthProvider.getMonth(month);
    }

    @Override
    public int value() {
        return dayOfYear;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return now.getYear() + 1900;
    }

    public static MonthDayModel fromMonthDay(int month, int day, DataDateProvider provider) {
        List<DateModel> days = provider.getMonthDay();
        for (DateModel dateModel : days) {
            MonthDayModel model = (MonthDayModel) dateModel;
            if (model.getMonth() == month && model.getDay() == day) {
                return model;
            }
        }

        return null;
    }
}

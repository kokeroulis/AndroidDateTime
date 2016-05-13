package gr.kokeroulis.androiddatetime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import gr.kokeroulis.androiddatetime.models.DateModel;
import gr.kokeroulis.androiddatetime.models.DayModel;
import gr.kokeroulis.androiddatetime.models.MonthModel;
import gr.kokeroulis.androiddatetime.models.YearModel;

public final class DataDateProvider {
    private static final List<DateModel> years = new ArrayList<>();
    private static final List<DateModel> months = new ArrayList<>();

    public static List<DateModel> getMonths() {
        if (months.size() == 0) {
            for (int i =1; i <=12; i++) {
                months.add(new MonthModel(i));
            }
        }

        return months;
    }

    public static List<DateModel> getYears() {
        if (years.size() == 0) {
            for (int i =1971; i <=2040; i++) {
                years.add(new YearModel(i));
            }
        }

        return years;
    }

    public static List<DateModel> getDaysForMonthAndYear(int month, int year) {
        int iYear = year;
        int iMonth = month - 1; //(months begin with 0)
        int iDay = 1;

        // Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

        // Get the number of days in that month
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<DateModel> days = new ArrayList<>();
        for (int i =1; i <= daysInMonth; i++) {
            days.add(new DayModel(i));
        }

        return days;
    }
}

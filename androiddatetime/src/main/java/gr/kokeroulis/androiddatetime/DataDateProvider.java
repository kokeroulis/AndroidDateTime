package gr.kokeroulis.androiddatetime;

import java.util.ArrayList;
import java.util.List;

import gr.kokeroulis.androiddatetime.models.DateModel;
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
}

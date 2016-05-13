package gr.kokeroulis.androiddatetime.models;

import java.util.HashMap;
import java.util.Map;

public final class  MonthProvider {
    private final Map<Integer, String> monthsMap = new HashMap();
    private static MonthProvider provider;

    private MonthProvider() {}

    public String getMonthTitle(int month) {
        if (monthsMap.size() == 0) {
            populateMap();
        }

        return monthsMap.get(month);
    }

    public static String getMonth(int month) {
        if (provider == null) {
            provider = new MonthProvider();
        }

       return provider.getMonthTitle(month);
    }

    private void populateMap() {
        monthsMap.put(1, "Jan");
        monthsMap.put(2, "Feb");
        monthsMap.put(3, "Mar");
        monthsMap.put(4, "Apr");
        monthsMap.put(5, "May");
        monthsMap.put(6, "June");
        monthsMap.put(7, "July");
        monthsMap.put(8, "Aug");
        monthsMap.put(9, "Sep");
        monthsMap.put(10, "Oct");
        monthsMap.put(11, "Nov");
        monthsMap.put(12, "Dec");
    }
}

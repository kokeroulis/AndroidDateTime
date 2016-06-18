package gr.kokeroulis.androiddatetime.models;

public class TimeModel implements DateModel {
    private int time;

    public TimeModel(int time) {
        this.time = time;
    }

    @Override
    public String title() {
        return String.valueOf(time);
    }

    @Override
    public int value() {
        return time;
    }
}

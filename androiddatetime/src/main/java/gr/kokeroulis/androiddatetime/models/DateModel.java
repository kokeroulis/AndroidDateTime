package gr.kokeroulis.androiddatetime.models;

public interface DateModel {

    String title();

    int value();

    DateModel fakeModelForPadding = new DateModel() {
        @Override
        public String title() {
            return "fake";
        }

        @Override
        public int value() {
            return -1;
        }
    };
}

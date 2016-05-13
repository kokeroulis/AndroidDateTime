package gr.kokeroulis.androiddatetime.models;

public interface DateModel {

    String title();

    DateModel fakeModelForPadding = new DateModel() {
        @Override
        public String title() {
            return "fake";
        }
    };
}

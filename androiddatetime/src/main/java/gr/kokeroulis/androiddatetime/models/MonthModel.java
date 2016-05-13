package gr.kokeroulis.androiddatetime.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class MonthModel implements Parcelable, DateModel {
    int monthDate;

    public MonthModel(int monthDate) {
        this.monthDate = monthDate;
    }

    protected MonthModel() {}


    @Override
    public String title() {
        return MonthProvider.getMonth(monthDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        MonthModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<MonthModel> CREATOR = new Creator<MonthModel>() {
        public MonthModel createFromParcel(Parcel source) {
            MonthModel target = new MonthModel();
            MonthModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public MonthModel[] newArray(int size) {
            return new MonthModel[size];
        }
    };
}

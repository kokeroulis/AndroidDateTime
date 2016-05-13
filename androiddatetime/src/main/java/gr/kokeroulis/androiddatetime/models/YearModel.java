package gr.kokeroulis.androiddatetime.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class YearModel implements Parcelable, DateModel {
    int year;

    public YearModel(int year) {
        this.year = year;
    }

    protected YearModel() {}

    @Override
    public String title() {
        return String.valueOf(year);
    }

    @Override
    public int value() {
        return year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        YearModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<YearModel> CREATOR = new Creator<YearModel>() {
        public YearModel createFromParcel(Parcel source) {
            YearModel target = new YearModel();
            YearModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public YearModel[] newArray(int size) {
            return new YearModel[size];
        }
    };
}

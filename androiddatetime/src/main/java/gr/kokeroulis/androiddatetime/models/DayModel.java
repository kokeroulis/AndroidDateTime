package gr.kokeroulis.androiddatetime.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class DayModel implements Parcelable, DateModel {
    int day;

    public DayModel(int day) {
        this.day = day;
    }

    protected DayModel() {}

    @Override
    public String title() {
        return String.valueOf(day);
    }

    @Override
    public int value() {
        return day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        DayModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<DayModel> CREATOR = new Creator<DayModel>() {
        public DayModel createFromParcel(Parcel source) {
            DayModel target = new DayModel();
            DayModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public DayModel[] newArray(int size) {
            return new DayModel[size];
        }
    };
}

package mx.mobilecoder.capturaalfugitivo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Swanros on 07/02/15.
 *
 * Cada Fugitivo tiene un nombre (mName) y un estado (mCaptured).
 *
 * En este caso, la clase Fugitive implementa la interfaz Parcelable para poder pasar este objeto
 * como un Extra dentro de un Intento.
 *
 * Parceable: http://developer.android.com/reference/android/os/Parcelable.html
 */
public class Fugitive implements Parcelable {
    public String mName;
    public int mCaptured;

    public Fugitive(String name) {
        mName = name;
        mCaptured = 0;
    }

    // Parcelable


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /**
         * Atención: Los elementos se deben de recuperar en el mismo orden en el que fueron insertados
         * en el Parcel.
         */
        dest.writeString(mName);
        dest.writeInt(mCaptured);
    }

    public static final Parcelable.Creator<Fugitive> CREATOR = new Parcelable.Creator<Fugitive>() {
        public Fugitive createFromParcel(Parcel in) { return new Fugitive(in); }

        public Fugitive[] newArray(int size) { return new Fugitive[size]; }
    };

    private Fugitive(Parcel in) {
        mName = in.readString();
        mCaptured = in.readInt();
    }
}

package com.temperoni.gymroutine.view.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Leandro Temperoni
 */
data class Group(var exercises: List<Exercise> = mutableListOf()) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Exercise))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(exercises)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Group> {
        override fun createFromParcel(parcel: Parcel): Group {
            return Group(parcel)
        }

        override fun newArray(size: Int): Array<Group?> {
            return arrayOfNulls(size)
        }
    }
}
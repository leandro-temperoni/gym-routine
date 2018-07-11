package com.temperoni.gymroutine.view.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Leandro Temperoni
 */
data class Routine(val id: String? = "",
                   val name: String? = "",
                   val groupCount: Int? = 0,
                   val groups: MutableList<Group> = mutableListOf()) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createTypedArrayList(Group.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(groupCount ?: 0)
        parcel.writeTypedList(groups)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Routine> {
        override fun createFromParcel(parcel: Parcel): Routine {
            return Routine(parcel)
        }

        override fun newArray(size: Int): Array<Routine?> {
            return arrayOfNulls(size)
        }
    }
}
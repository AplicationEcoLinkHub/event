package com.example.myapplication22.Models

import android.os.Parcel
import android.os.Parcelable

data class Task(
    val _id: String?, // Add the id property
    val title: String?,
    val time: String?,
    val date: String?,
    val description: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString() // Read the id from the parcel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id) // Write the id to the parcel
        parcel.writeString(title)
        parcel.writeString(time)
        parcel.writeString(date)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}

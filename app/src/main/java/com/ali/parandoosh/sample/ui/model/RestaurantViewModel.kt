package com.ali.parandoosh.sample.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.ali.parandoosh.sample.R
import com.ali.parandoosh.sample.base.Item

data class RestaurantViewModel(
    val name: String?,
    val coverUrl: String?,
    val description: String?,
    val status: String?
) : Item, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun getLayoutId(): Int = R.layout.item_restaurant

    override fun getSpanSize(): Int = 1
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(coverUrl)
        parcel.writeString(description)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantViewModel> {
        override fun createFromParcel(parcel: Parcel): RestaurantViewModel {
            return RestaurantViewModel(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantViewModel?> {
            return arrayOfNulls(size)
        }
    }

}

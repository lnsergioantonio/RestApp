package com.lnsergioantonio.restapp.ui.adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class HeadersItem(
        val key: String,
        val value: String
): Parcelable

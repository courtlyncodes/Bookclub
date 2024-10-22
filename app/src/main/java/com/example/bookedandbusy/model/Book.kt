package com.example.bookedandbusy.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book (
    @StringRes val title: Int,
    @StringRes val author: Int
): Parcelable
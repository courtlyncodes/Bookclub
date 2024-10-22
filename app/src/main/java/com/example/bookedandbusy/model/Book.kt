package com.example.bookedandbusy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book (
    val title: String,
    val author: String
): Parcelable
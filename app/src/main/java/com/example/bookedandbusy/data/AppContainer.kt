package com.example.bookedandbusy.data

import android.content.Context

interface AppContainer {
    val bookRepository : BookRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val bookRepository = WorkManagerBookRepository(context)
}

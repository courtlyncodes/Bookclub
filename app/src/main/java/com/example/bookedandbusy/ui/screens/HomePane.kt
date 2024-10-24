package com.example.bookedandbusy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookedandbusy.FIVE_SECONDS
import com.example.bookedandbusy.ONE_DAY
import com.example.bookedandbusy.ONE_HOUR
import com.example.bookedandbusy.ONE_MINUTE
import com.example.bookedandbusy.ONE_WEEK
import com.example.bookedandbusy.THIRTY_MINUTES
import com.example.bookedandbusy.data.BookDataSource
import com.example.bookedandbusy.data.Reminder
import com.example.bookedandbusy.model.Book
import com.example.bookedandbusy.ui.BookViewModel
import com.example.bookedandbusy.ui.theme.BookedAndBusyTheme
import com.example.bookedandbusy.ui.theme.Pink80
import com.example.bookedandbusy.R
import java.util.concurrent.TimeUnit

@Composable
fun BookedAndBusyApp(bookViewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)) {
    val layoutDirection = LocalLayoutDirection.current
    BookedAndBusyTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(
                    start = WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateStartPadding(layoutDirection),
                    end = WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateEndPadding(layoutDirection)
                )
        ) {
            BookListContent(
                books = bookViewModel.books,
                onScheduleReminder = { bookViewModel.scheduleReadReminder(it) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListContent(
    books: List<Book>,
    onScheduleReminder: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBook by rememberSaveable { mutableStateOf(books[0]) }
    var showReminderDialog by rememberSaveable { mutableStateOf(false) }
    var selectedTime by rememberSaveable { mutableLongStateOf(5000L) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) }
            )
        }) { it ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(it)
        ) {
            items(books) {
                BookListItem(
                    book = it,
                    onItemSelect = { book ->
                        selectedBook = book
                        showReminderDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (showReminderDialog) {
            ReminderDialogContent(
                onTimeSelected = { selectedTime = it },
                onDialogDismiss = { showReminderDialog = false },
                bookName = stringResource(selectedBook.title),
                onScheduleReminder = onScheduleReminder
            )
        }
    }
}

@Composable
fun BookListItem(book: Book, onItemSelect: (Book) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.clickable { onItemSelect(book) },
        colors = CardDefaults.cardColors(containerColor = Pink80),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = book.title),
                modifier = Modifier.fillMaxWidth(),
                style = typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(book.author),
                modifier = Modifier.fillMaxWidth(),
                style = typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ReminderDialogContent(
    onTimeSelected: (Long) -> Unit,
    onDialogDismiss: () -> Unit,
    bookName: String,
    onScheduleReminder: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val reminders = listOf(
        Reminder(R.string.five_seconds, FIVE_SECONDS, TimeUnit.SECONDS, bookName),
        Reminder(R.string.one_minute, ONE_MINUTE, TimeUnit.MINUTES, bookName),
        Reminder(R.string.thirty_mins, THIRTY_MINUTES, TimeUnit.MINUTES, bookName),
        Reminder(R.string.one_hr, ONE_HOUR, TimeUnit.HOURS, bookName),
        Reminder(R.string.one_day, ONE_DAY, TimeUnit.HOURS, bookName),
        Reminder(R.string.one_week, ONE_WEEK, TimeUnit.DAYS, bookName),
    )

    AlertDialog(
        onDismissRequest = onDialogDismiss,
        confirmButton = {},
        title = { Text(text = stringResource(R.string.remind_me_to_read, bookName)) },
        text = {
            Column {
                reminders.forEach {
                    Text(
                        text = stringResource(it.durationRes),
                        modifier = Modifier
                            .clickable {
                                onTimeSelected(it.duration)
                                onScheduleReminder(it)
                                onDialogDismiss()
                            }
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview() {
    BookedAndBusyTheme {
        BookListItem(BookDataSource.books[0], {})
    }
}

@Preview(showBackground = true)
@Composable
fun BookListContentPreview() {
    BookListContent(books = BookDataSource.books, onScheduleReminder = {})
}

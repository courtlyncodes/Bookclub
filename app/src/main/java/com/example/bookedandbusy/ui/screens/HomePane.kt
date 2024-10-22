package com.example.bookedandbusy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookedandbusy.ONE_DAY
import com.example.bookedandbusy.ONE_HOUR
import com.example.bookedandbusy.ONE_MINUTE
import com.example.bookedandbusy.ONE_WEEK
import com.example.bookedandbusy.THIRTY_MINUTES
import com.example.bookedandbusy.data.BookDataSource
import com.example.bookedandbusy.data.Reminder
import com.example.bookedandbusy.model.Book
import com.example.bookedandbusy.ui.theme.BookedAndBusyTheme
import com.example.bookedandbusy.ui.theme.Pink80
import com.example.myapplication.R
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

//@Composable
//fun BookListContent(
//    books: List<Book>,
//    onScheduleReminder: (Reminder) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var selectedBook by rememberSaveable { mutableStateOf(books[0])}
//    var showReminderDialog by rememberSaveable { mutableStateOf(false) }
//    var selectedTime by rememberSaveable { mutableLongStateOf(5000L) }
//
//    Lazy
//}

@Composable
fun BookListItem(book: Book, onItemSelect: (Book) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.clickable { onItemSelect(book) },
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
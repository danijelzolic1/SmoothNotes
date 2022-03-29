package se.zolda.smoothnotes.extensions

import android.text.format.DateUtils
import java.util.*

fun Calendar.timeSpanString(): String{
    val flags = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_ABBREV_RELATIVE
    return DateUtils.getRelativeTimeSpanString(this.time.time, Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS, flags).toString()
}

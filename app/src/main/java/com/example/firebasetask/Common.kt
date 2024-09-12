package com.example.firebasetask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.AttributeSet
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import java.util.Locale


fun decodeImage(encodedString: String): Bitmap {
    val imageBytes = decode(encodedString, DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun calculateAge(dob: String?): Int {
    if (dob.isNullOrEmpty())
        return 0
    else {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        val dobDate = dateFormat.parse(dob) ?: return -1

        val diff = currentDate.time - dobDate.time

        return (diff / (1000L * 60 * 60 * 24 * 365.25)).toInt()
    }
}

fun showToast(context: Context?, msg: String, length: Int) {
    Toast.makeText(context, msg, length).show()
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

fun showDatePicker(textDate: TextView, context: Context?) {
    if (context != null) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val formattedDate = dateFormat.format(selectedDate.time)

                textDate.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}

fun showTimePicker(textTime: TextView, context: Context?, is24Hour: Boolean) {
    if (context != null) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime[Calendar.HOUR_OF_DAY]
        val minute = currentTime[Calendar.MINUTE]

        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute -> // Handle time selection
                var h = hourOfDay.toString()
                if (hourOfDay < 10)
                    h = "0$hourOfDay"

                var min = minute.toString()
                if (minute < 10)
                    min = "0$minute"
                val selectedTime = "$h:$min:00"
                val formattedTime = selectedTime.format(selectedTime)

                textTime.text = formattedTime
            },
            hour,
            minute,
            is24Hour
        )
        timePickerDialog.show()
    }
}

class CurvedBottomNavigationView(context: Context, attrs: AttributeSet?) : BottomNavigationView(context, attrs) {

    private val path = Path()
    private val paint = Paint()

    private var curveRadius: Float = 128f

    init {
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.WHITE
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCurvedNavigationBar(canvas)
    }

    private fun drawCurvedNavigationBar(canvas: Canvas) {
        val width = width
        val height = height

        val centerX = width / 2f
        val curveDiameter = curveRadius * 2

        path.reset()
        path.moveTo(0f, 0f)
        path.lineTo(centerX - curveRadius, 0f)

        path.quadTo(centerX, curveDiameter, centerX + curveRadius, 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()

        canvas.drawPath(path, paint)
    }
}
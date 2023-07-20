package com.example.ageinminutesandhours

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var btnDatePicker : Button? = null
    private var tvDiffInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker = findViewById(R.id.btnDatePicker)
        btnDatePicker?.setOnClickListener {
            clickDatePicker()
        }

        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        tvDiffInMinutes = findViewById(R.id.tvDiffInMinutes)
    }

    private fun clickDatePicker() {

        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60_000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60_000

                        val diff = currentDateInMinutes - selectedDateInMinutes

                        tvDiffInMinutes?.text = diff.toString()
                    }
                }
            },
            year,
            month,
            day
            )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}
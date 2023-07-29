package com.vyarth.ageinminutes

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeinHours : TextView? = null
    private var tvAgeinMinutes : TextView? = null
    private var tvAgeinSeconds : TextView? = null
    private var tvAgeinDays : TextView? = null
    private var tvAgeInFormat: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnDatePicker: Button =findViewById(R.id.btnDatePicker)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeinHours =  findViewById(R.id.tvAgeInHours)
        tvAgeinMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeinSeconds = findViewById(R.id.tvAgeInSeconds)
        tvAgeinDays = findViewById(R.id.tvAgeInDays)
        tvAgeInFormat = findViewById(R.id.tvAgeInFormat)


        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){

        val myCalendar= Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _,selectedYear,selectedMonth,selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                tvSelectedDate?.setText(selectedDate)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                    val selectedDateInDays = theDate.time/ 86400000
                    val selectedDateInMinutes = theDate.time/ 60000
                    val selectedDateInHours = theDate.time/ 3600000
                    val selectedDateInSeconds = theDate.time/ 1000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let{
                        val currentDateInDays = currentDate.time/ 86400000
                        val currentDateInMinutes = currentDate.time/ 60000
                        val currentDateInHours = currentDate.time/ 3600000
                        val currentDateInSeconds = currentDate.time/ 1000

                        val differenceInDays = currentDateInDays-selectedDateInDays
                        val differenceInMinutes = currentDateInMinutes-selectedDateInMinutes
                        val differenceInHours = currentDateInHours-selectedDateInHours
                        val differenceInSeconds = currentDateInSeconds-selectedDateInSeconds

                        val ageYears = differenceInDays / 365
                        val ageMonths = (differenceInDays % 365) / 30
                        val ageDays = (differenceInDays % 365) % 30

                        tvAgeInFormat?.text = "$ageYears Years | $ageMonths Months | $ageDays Days"


                        tvAgeinDays?.text = differenceInDays.toString()
                        tvAgeinMinutes?.text = differenceInMinutes.toString()
                        tvAgeinHours?.text = differenceInHours.toString()
                        tvAgeinSeconds?.text = differenceInSeconds.toString()
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
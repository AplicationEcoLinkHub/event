package com.example.myapplication22


//import MyBottomSheetDialogFragment
import MyBottomSheetDialogFragment
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.text.SimpleDateFormat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateTextView: TextView = findViewById(R.id.dateTextView)
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        val bottomRightButton: Button = findViewById(R.id.bottomRightButton)




        // Set up click listener for CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedSelectedDate = dateFormat.format(selectedDate.time)
            dateTextView.text = "Date: $formattedSelectedDate"
            val bottomSheetFragment = MyBottomSheetDialogFragment(formattedSelectedDate)

            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        bottomRightButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }
}
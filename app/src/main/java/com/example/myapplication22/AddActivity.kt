package com.example.myapplication22

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication22.Service.ApiService
import com.example.myapplication22.Service.EventService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AddActivity  : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var descriptionEditText: EditText
    var btnAdd: Button? = null

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_form)

        titleEditText = findViewById(R.id.titleEditText)
        dateEditText = findViewById(R.id.dateEditText)
        timeEditText = findViewById(R.id.timeEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        btnAdd = findViewById(R.id.addButton)

        // Set up click listener for the date field
        dateEditText.setOnClickListener {
            showDatePicker()
        }

        // Set up click listener for the time field
        timeEditText.setOnClickListener {
            showTimePicker()
        }

        btnAdd!!.setOnClickListener {

            Log.d("path",  titleEditText.text.toString()+  dateEditText.text.toString()+  timeEditText.text.toString()+
                descriptionEditText.text.toString(),)
            ApiService.EVENTSERVICE.addPost(
               EventService.TaskBody(
                   titleEditText.text.toString(),timeEditText.text.toString(),dateEditText.text.toString(), descriptionEditText.text.toString()
               )

            ).enqueue(
                object : Callback<EventService.EventResponse> {
                    override fun onResponse(
                        call: Call<EventService.EventResponse>,
                        response: Response<EventService.EventResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Handle successful response
                            val addedEvent = response.body()?.evenement
                            Toast.makeText(
                                this@AddActivity,
                                "Event Added Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            titleEditText.setText("")
                            timeEditText.setText("")
                            dateEditText.setText("")
                            descriptionEditText.setText("")


                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(
                                this@AddActivity,
                                "Failed to add event. HTTP ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<EventService.EventResponse>, t: Throwable) {
                        // Handle failure
                        Toast.makeText(this@AddActivity, "Failed to add event. ${t.message}", Toast.LENGTH_SHORT).show()

                        Log.d("test", t.message!!)
                    }
                }
            )
        }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                updateDateField()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                updateTimeField()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // 24-hour format
        )

        timePicker.show()
    }

    private fun updateDateField() {
        val  dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dateEditText.setText(dateFormat.format(calendar.time))
    }

    private fun updateTimeField() {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        timeEditText.setText(timeFormat.format(calendar.time))
    }


}
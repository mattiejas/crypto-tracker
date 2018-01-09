package io.github.mattiejas.crypto_tracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_trade.*
import java.util.*
import java.text.DateFormat

class AddTradeActivity : AppCompatActivity() {

    val calendar = Calendar.getInstance()
    val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateField()
    }
    val time = TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
        updateTimeField(hours, minutes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trade)

        supportActionBar?.title = "Add Trade"

        add_trade_date.setOnClickListener {
            setDate()
        }

        add_trade_time.setOnClickListener {
            setTime()
        }
    }

    private fun setDate() {
        DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun setTime() {
        Log.d("CryptoTracker", Date().toString())
        TimePickerDialog(this, time, 0, 0, true).show()
    }

    private fun updateDateField() {
        val dateFormat = DateFormat.getDateInstance()
        Log.d("CryptoTracker", dateFormat.format(calendar.time))
        add_trade_date.setText(dateFormat.format(calendar.time))
    }

    private fun updateTimeField(hours: Int, minutes: Int) {
        Log.d("CryptoTracker", "" + String.format("%02d", hours) + ":" + String.format("%02d", minutes))
        add_trade_time.setText("" + String.format("%02d", hours) + ":" + String.format("%02d", minutes))
    }
}

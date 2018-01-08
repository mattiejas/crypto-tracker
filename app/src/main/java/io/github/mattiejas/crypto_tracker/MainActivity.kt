package io.github.mattiejas.crypto_tracker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.github.mattiejas.crypto_tracker.helpers.CoinsDBHelper
import io.github.mattiejas.crypto_tracker.models.CoinModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var coinsDBHelper : CoinsDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        coinsDBHelper = CoinsDBHelper(this)

        add_coin.setOnClickListener {
            openAddTradeView()
        }
    }

    fun openAddTradeView() {
        Log.d("Main", "Open Add Trade View")
        Toast.makeText(this, "Test", Toast.LENGTH_LONG).show()

        val intent = Intent(this, AddTradeActivity::class.java)
        startActivity(intent)
    }

}

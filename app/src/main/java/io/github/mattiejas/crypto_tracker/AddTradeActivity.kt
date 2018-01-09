package io.github.mattiejas.crypto_tracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AddTradeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trade)

        supportActionBar?.title = "Add Trade"
    }
}

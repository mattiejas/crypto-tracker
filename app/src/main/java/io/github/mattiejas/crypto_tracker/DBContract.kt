package io.github.mattiejas.crypto_tracker

import android.provider.BaseColumns

/**
 * Created by matthias on 08/01/2018.
 */

class DBContract {
    class CoinEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "coins"
            val COLUMN_SYMBOL = "symbol"
            val COLUMN_NAME = "name"
        }
    }
}
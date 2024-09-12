
package com.example.firebasetask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasetask.R

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val keyword = intent.getStringExtra("SEARCH_KEYWORD")

        // Use the keyword to query your product data source
        // For example, if you are using a ViewModel and LiveData, you can do:
        val viewModel: Unit = // initialize your ViewModel

            if (keyword != null) {
            } else {

            }

        // Observe the LiveData and update your UI with the results

    }
}
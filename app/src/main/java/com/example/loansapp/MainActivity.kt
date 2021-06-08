package com.example.loansapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loansapp.ui.enter.EnterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, EnterFragment.newInstance())
                    .commitNow()
        }
    }
}
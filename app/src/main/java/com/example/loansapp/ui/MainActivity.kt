package com.example.loansapp.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.presentation.MainViewModel
import com.example.loansapp.ui.authorization.EnterFragment
import com.example.loansapp.utils.LocaleManager
import com.example.loansapp.worker.NotifyWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val HOURS_PERIOD_OF_REPEATING_NOTIFICATION_WORK = 3L
    }

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.mainComponent().create().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setUserPreferenceTheme()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EnterFragment.newInstance())
                .commit()
        }
    }

    private fun setUserPreferenceTheme() {
        if (viewModel.getTheme() == ThemeType.Dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleManager.setLocale(it) })
    }

    override fun onStop() {
        super.onStop()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val notifyWork = PeriodicWorkRequest.Builder(
            NotifyWorker::class.java,
            HOURS_PERIOD_OF_REPEATING_NOTIFICATION_WORK,
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInitialDelay(HOURS_PERIOD_OF_REPEATING_NOTIFICATION_WORK, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(notifyWork)
    }

    override fun onStart() {
        super.onStart()
        WorkManager.getInstance(this).cancelAllWork()
    }
}
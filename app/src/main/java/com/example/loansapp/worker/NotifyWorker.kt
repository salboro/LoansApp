package com.example.loansapp.worker

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.loansapp.R
import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.usecase.GetLoansUseCase
import com.example.loansapp.utils.sendNotification
import io.reactivex.Single
import javax.inject.Inject


class NotifyWorker(
    private val context: Context, workerParameters: WorkerParameters
) : RxWorker(context, workerParameters) {

    @Inject
    lateinit var getLoansUseCase: GetLoansUseCase

    override fun createWork(): Single<Result> {
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)

        return getLoansUseCase()
            .map { result ->
                if (result is ResultType.Success) {
                    sendOnSuccessNotification(notificationManager, result.data)
                } else {

                    sendOnErrorNotification(notificationManager)
                }
                Result.success()
            }
            .onErrorReturn {
                sendOnErrorNotification(notificationManager)
                Result.success()
            }
    }

    private fun sendOnSuccessNotification(
        notificationManager: NotificationManager?,
        loans: List<Loan>
    ) {

        notificationManager?.sendNotification(
            context.resources.getString(
                R.string.loans_count_template,
                loans.filter { it.state == "APPROVED" }.count()
            ),
            context
        )
    }

    private fun sendOnErrorNotification(notificationManager: NotificationManager?) {
        notificationManager?.sendNotification(
            context.resources.getString(
                R.string.do_not_forget_about_loans,
            ),
            context
        )
    }

}
package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)

        val data: Data = Data.Builder().putString("key1", "hello").build()

        val constraints = Constraints.Builder().setRequiresCharging(true).build()

        val worker = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val worker2 = OneTimeWorkRequestBuilder<MyWorker2>().build()
        val worker3 = PeriodicWorkRequestBuilder<MyWorker>(1,TimeUnit.MINUTES).build()

        // раз в 15 минут в течение 1 часа
        val worker4 = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.HOURS, 15, TimeUnit.MINUTES).build()
        // задержка
        val worker5 = OneTimeWorkRequestBuilder<MyWorker>()
            .addTag("workeralias")
            .setInitialDelay(10, TimeUnit.MINUTES).build()

        val list: ArrayList<WorkRequest> = ArrayList()
        list.add(worker)
        list.add(worker2)

        btn.setOnClickListener {
            // параллельно
           // WorkManager.getInstance(this).enqueue(list)

            // последовательно
            WorkManager.getInstance(this)
                .beginWith(worker)
                .then(worker2)
                .enqueue()
        }

        WorkManager.getInstance(this).getWorkInfosByTagLiveData("workeralias").observe()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(worker2.id)
            .observe(this, Observer { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    Log.d("RRR",workInfo.state.toString())
                    workInfo?.outputData?.getString("from")?.let { Log.d("RRR", it) }
                }
            })
    }
}
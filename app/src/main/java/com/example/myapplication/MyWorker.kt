package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay


class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("RRR","Myworker 1 started!")
        Thread.sleep(3000)
        Log.d("RRR","Myworker 1 finished!")

        // val str = inputData.getString("key1")

        return Result.success()
    }
}
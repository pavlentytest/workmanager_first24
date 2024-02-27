package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker2(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("RRR","Myworker 2 started!")
        Thread.sleep(3000)
        Log.d("RRR","Myworker 2 finished!")
        val data: Data = Data.Builder().putString("from","hello from worker2").build()
        return Result.success(data)
    }
}
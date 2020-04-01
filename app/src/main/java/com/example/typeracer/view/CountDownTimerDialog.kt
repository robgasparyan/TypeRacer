package com.example.typeracer.view

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.typeracer.R
import java.util.concurrent.TimeUnit

class CountDownTimerDialog(private val context: Activity, private val durationSeconds: Long) :
    AppCompatDialog(context), LifecycleObserver {

    private var countDownTimer: CountDownTimer? = null
    private var completedListener: CompletedListener? = null
    private lateinit var timeTv: TextView
    private var lastTextRes: Int? = null

    init {
        attachView()
    }

    fun setCompletedListener(listener: CompletedListener?): CountDownTimerDialog {
        completedListener = listener
        return this
    }

    fun setBackgroundDim(@FloatRange(from = 0.0, to = 1.0) dim: Float): CountDownTimerDialog {
        val lp = window?.attributes
        lp?.dimAmount = dim
        window?.attributes = lp
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return this
    }

    fun addLastText(@StringRes strId: Int): CountDownTimerDialog {
        lastTextRes = strId
        return this
    }

    fun safeShow(): CountDownTimerDialog {
        if (!isShowing) {
            startTimer()
            show()
        }
        return this
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun safeDismiss() {
        if (isShowing) {
            dismiss()
            stopTimer()
        }
    }

    private fun startTimer() {
        val mlsInFuture = TimeUnit.SECONDS.toMillis(durationSeconds)
        countDownTimer = object : CountDownTimer(mlsInFuture, TimeUnit.SECONDS.toMillis(1)) {
            override fun onTick(mlsUntilFinished: Long) {
                updateText(mlsUntilFinished)
            }

            override fun onFinish() {
                completedListener?.onCompleted()
                safeDismiss()
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
    }

    @SuppressLint("InflateParams")
    private fun attachView() {
        val view: View = context.layoutInflater.inflate(R.layout.count_down_timer, null)
        timeTv = view.findViewById(R.id.message)
        this.setContentView(view)
        this.setCancelable(false)
    }

    private fun updateText(mlsUntilFinished: Long) {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(mlsUntilFinished)
        val lastStr = lastTextRes
        if (seconds == 0L && lastStr != null) {
            timeTv.setText(lastStr)
        } else {
            timeTv.text = seconds.toString()
        }
    }

    interface CompletedListener {
        fun onCompleted()
    }

}
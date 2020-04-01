package com.example.typeracer.viewModel

import android.app.Application
import android.os.CountDownTimer
import android.text.SpannableString
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.typeracer.R
import com.example.typeracer.model.Dialog
import com.example.typeracer.model.TextPaint
import com.example.typeracer.model.TimeoutDialog
import com.example.typeracer.model.WinDialog
import com.example.typeracer.repo.RaceRepo
import com.example.typeracer.repo.ResponseListener
import com.example.typeracer.repo.model.Race
import com.example.typeracer.repo.model.RaceResult
import com.example.typeracer.repo.TextRepo
import com.example.typeracer.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.min

class TypeVM(
    app: Application,
    private val raceRepo: RaceRepo,
    private val textRepo: TextRepo
) : BaseVM(app) {

    //Live data, for observe view(fragment) changes
    private val _showDialog = MutableLiveData<Dialog>()
    val showDialog: LiveData<Dialog>
        get() = _showDialog
    val lifecycleLiveData = MutableLiveData<Lifecycle.Event>()
    private val observer = Observer<Lifecycle.Event> {
        when (it) {
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            else -> {
            }
        }
    }

    //Observables, for observe view(xml) changes
    val wpm = ObservableDouble(0.0)
    val source = ObservableField(SpannableString(""))
    val progress = ObservableInt(0)
    val isLoading = ObservableBoolean(true)
    val typedText = ObservableField("")
    val isStarted = ObservableBoolean(false)
    val countDownTime = ObservableField<String>()
    val countDownProgress = ObservableInt(100)

    //locale props
    private var startTime: Long = 0
    private var correctCharCount = 0
    private var countDownTimer: CountDownTimer? = null

    init {
        lifecycleLiveData.observeForever(observer)
    }

    override fun onCleared() {
        lifecycleLiveData.removeObserver(observer)
        stopCountDownTimer()
        super.onCleared()
    }

    fun textChanged(newText: String) {
        if (!isStarted.get()) return
        viewModelScope.launch(Dispatchers.IO) {
            correctCharCount = updateCorrectCharCount(newText)
            updateWpm(correctCharCount)
            updateSourceTextSpannable(correctCharCount, newText.length)
            updateProgress(correctCharCount)
            if (isFinished(progress.get())) {
                showFinishDialog()
            }
        }
    }

    fun saveRaceResult(race: Race) {
        isLoading.set(true)
        raceRepo.addRace(race, object : ResponseListener<Map<String, RaceResult>?> {
            override fun onResponse(response: Map<String, RaceResult>?) {
                isLoading.set(false)
            }

            override fun onFailure() {
                isLoading.set(false)
            }
        })
    }

    fun prepareNewRace() {
        resetProperties()
        loadSourceText()
    }

    fun onActionClick() {
        if (!isStarted.get()) {
            onStart()
        } else {
            prepareNewRace()
        }
    }

    //lifecycle state
    private fun onResume() {
        prepareNewRace()
    }

    //lifecycle state
    private fun onPause() {
        resetProperties()
    }

    private fun onStart() {
        isStarted.set(true)
        isStarted.notifyChange()
        startTime = System.currentTimeMillis()
        startCountDownTimer()
    }

    private fun isFinished(progress: Int) = progress >= 100

    private fun showFinishDialog() {
        _showDialog.postValue(WinDialog(Race(wpm.get().toDecimal2(), System.currentTimeMillis())))
    }

    private fun showTimeoutDialog() {
        val maxTime = DateTimeHelper.getCountDownTime(Constants.COUNT_DOWN_TIME)
        _showDialog.postValue(TimeoutDialog(maxTime))
    }

    private fun startCountDownTimer() {
        val countDownInterval = TimeUnit.SECONDS.toMillis(1)
        countDownTimer = object : CountDownTimer(Constants.COUNT_DOWN_TIME, countDownInterval) {
            override fun onTick(mlsUntilFinished: Long) {
                updateTimerUI(mlsUntilFinished)
            }

            override fun onFinish() {
                showTimeoutDialog()
                prepareNewRace()
            }
        }.start()
    }

    private fun updateTimerUI(mlsUntilFinished: Long) {
        val s = mlsUntilFinished percentFrom Constants.COUNT_DOWN_TIME
        countDownProgress.set(s.toInt())
        countDownTime.set(DateTimeHelper.getCountDownTime(mlsUntilFinished))
    }

    private fun stopCountDownTimer() {
        countDownTimer?.cancel()
    }

    private fun updateCorrectCharCount(newText: String): Int {
        val source = source.get()
        return when {
            newText.length < correctCharCount -> newText.length
            !source.isNullOrEmpty() && newText.length > source.length -> correctCharCount
            !source.isNullOrEmpty() && isCorrect(newText) -> min(
                correctCharCount.inc(),
                source.length
            )
            else -> correctCharCount
        }
    }

    private fun updateWpm(correctCharCount: Int) {
        val spentTimeInMls = System.currentTimeMillis() - startTime
        val newWpm = calculateGrossWpm(correctCharCount, spentTimeInMls)
        wpm.set(newWpm.toDecimal2())
    }

    private fun calculateGrossWpm(correctInputs: Int, spentTimeInMls: Long): Double {
        val spentMinutes = TimeUnit.MILLISECONDS.toSeconds(spentTimeInMls) / 60.0
        return correctInputs / spentMinutes
    }

    private fun updateProgress(correctCharCount: Int) {
        source.get()?.let {
            val percent = correctCharCount percentFrom it.length
            progress.set(percent)
        }
    }

    private fun updateSourceTextSpannable(correctCharCount: Int, newTextLength: Int) {
        source.get()?.apply {
            if (newTextLength > length) return //ignore user infinity typing ))
            val correctTp = TextPaint(0, correctCharCount, getColor(R.color.colorGreen))
            val incorrectTp = TextPaint(
                correctCharCount.coerceAtMost(newTextLength),
                correctCharCount.coerceAtLeast(newTextLength),
                getColor(android.R.color.holo_red_dark)
            )
            val tailTp = TextPaint(newTextLength, length, getColor(R.color.colorSecondary))
            paintCharacters(correctTp, incorrectTp, tailTp)
        }
        source.notifyChange()
    }

    private fun isCorrect(newText: String): Boolean {
        return newText.isNotEmpty() &&
                newText.length <= source.get()?.length ?: 0 &&
                source.get()?.substring(0, newText.length).equals(newText)
    }

    private fun loadSourceText() {
        isLoading.set(true)
        textRepo.getNextText(listener = object : ResponseListener<String?> {
            override fun onResponse(response: String?) {
                isLoading.set(false)
                source.set(SpannableString(response))
            }

            override fun onFailure() {
                isLoading.set(false)
                Toast.makeText(getApplication(), R.string.error_msg, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun resetProperties() {
        correctCharCount = 0
        wpm.set(0.0)
        startTime = 0

        progress.set(0)
        progress.notifyChange()

        typedText.set("")
        typedText.notifyChange()

        isStarted.set(false)
        isStarted.notifyChange()

        source.set(SpannableString(""))
        source.notifyChange()

        countDownTime.set(DateTimeHelper.getCountDownTime(Constants.COUNT_DOWN_TIME))
        countDownProgress.set(100)
        stopCountDownTimer()
    }

}
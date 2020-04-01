package com.example.typeracer.model

import com.example.typeracer.view.CountDownTimerDialog
import com.example.typeracer.repo.model.Race

sealed class Dialog

data class WinDialog(val race: Race) : Dialog()

class TimeoutDialog(val maxTime: String) : Dialog()

class CountDownDialog(val time: Long,
                      val completedListener: CountDownTimerDialog.CompletedListener) : Dialog()
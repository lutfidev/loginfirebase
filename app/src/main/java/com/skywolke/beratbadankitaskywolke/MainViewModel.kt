package com.skywolke.beratbadankitaskywolke

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val authState = FirebaseUserLiveData()
}
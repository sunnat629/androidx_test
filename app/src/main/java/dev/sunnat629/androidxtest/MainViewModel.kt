package dev.sunnat629.androidxtest

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainViewModel @ViewModelInject constructor(
    private val themeUtil: ThemeUtil
) : ViewModel() {

    val switchTheme = themeUtil.switchTheme.asLiveData()

    val isDark: ThemeEnum = runBlocking { themeUtil.switchTheme.first() }

    fun setThemeMode(themeEnum: ThemeEnum) {
        viewModelScope.launch {
            themeUtil.setTheme(themeEnum)
        }
    }
}
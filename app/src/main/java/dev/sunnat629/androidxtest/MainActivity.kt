package dev.sunnat629.androidxtest

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
    private var isDarkMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeColor(viewModel.isDark)
        initButtons()
        initObservers()
    }

    private fun initButtons() {
        root?.setOnClickListener {
            Log.w("QWER", "isDarkMode: $isDarkMode")

            when (isDarkMode) {
                true -> viewModel.setThemeMode(ThemeEnum.DARK)
                false -> viewModel.setThemeMode(ThemeEnum.LIGHT)
            }
        }
    }

    private fun initObservers() {
        viewModel.switchTheme.observe(this) {
            when (it) {
                ThemeEnum.DARK -> {
                    isDarkMode = false
                    changeColor(ThemeEnum.DARK)
                    Log.w("QWER", ThemeEnum.DARK.name)
                }
                ThemeEnum.LIGHT -> {
                    isDarkMode = true
                    changeColor(ThemeEnum.LIGHT)
                    Log.i("QWER", ThemeEnum.LIGHT.name)
                }
                else -> Log.d("QWER", "Others")
            }
        }
    }

    private fun changeColor(isDark: ThemeEnum) {
        root?.background = ContextCompat.getDrawable(
            this@MainActivity,
            if (isDark == ThemeEnum.DARK) R.color.black else R.color.white
        )
    }
}
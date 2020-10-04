package dev.sunnat629.androidxtest

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

enum class ThemeEnum {
    DARK, LIGHT
}

@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
class ThemeUtil @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setTheme(themeEnum: ThemeEnum) {
        dataStore.edit { pref ->
            pref[THEME_UI] = when (themeEnum) {
                ThemeEnum.DARK -> true
                ThemeEnum.LIGHT -> false
            }
        }
    }

    val switchTheme: Flow<ThemeEnum> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { pref ->
            when (pref[THEME_UI] ?: false) {
                true -> ThemeEnum.DARK
                false -> ThemeEnum.LIGHT
            }
        }

    companion object {
        private const val DARK_MODE = "darkMode"
        private val THEME_UI = preferencesKey<Boolean>(DARK_MODE)
    }
}
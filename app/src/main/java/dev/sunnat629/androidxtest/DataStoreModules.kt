package dev.sunnat629.androidxtest

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
class DataStoreModules {

    @Provides
    fun setupDataStore(
        @ActivityContext context: Context,
        ): DataStore<Preferences> = context.createDataStore(
        name = context.getString(R.string.app_name)
    )
}
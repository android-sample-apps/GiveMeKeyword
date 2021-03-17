package com.mashup.data.source.preference

import android.content.Context
import com.mashup.domain.repositories.PreferenceRepository
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class PreferenceRepositoryImpl  @Inject constructor(
        @ActivityContext context: Context
) : PreferenceRepository {

}
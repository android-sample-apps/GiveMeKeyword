package com.mut_jaeryo.givmkeyword.data.source.preference

import android.content.Context
import com.mut_jaeryo.givmkeyword.domain.repositories.PreferenceRepository
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class PreferenceRepositoryImpl  @Inject constructor(
        @ActivityContext context: Context
) : PreferenceRepository {

}
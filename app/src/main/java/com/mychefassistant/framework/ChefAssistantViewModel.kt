package com.mychefassistant.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class ChefAssistantViewModel(
    application: Application,
    protected val interactors: Interactors
) :
    AndroidViewModel(application) {

    protected val application: ChefAssistantApplication = getApplication()

}
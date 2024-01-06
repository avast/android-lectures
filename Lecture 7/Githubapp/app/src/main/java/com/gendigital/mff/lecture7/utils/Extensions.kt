package com.gendigital.mff.lecture7.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Collects given flow [flow] on started state.
 */
fun <T> LifecycleOwner.collectOnStarted(flow: Flow<T>, block: (T) -> Unit) =
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(block)
        }
    }

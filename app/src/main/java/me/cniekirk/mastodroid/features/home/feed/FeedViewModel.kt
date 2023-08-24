package me.cniekirk.mastodroid.features.home.feed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(

) : ViewModel(), ContainerHost<FeedState, FeedEffect> {

    override val container = container<FeedState, FeedEffect>(FeedState()) {
        // Load feed
    }
}
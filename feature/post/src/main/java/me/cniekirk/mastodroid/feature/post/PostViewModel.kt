package me.cniekirk.mastodroid.feature.post

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(

) : ViewModel(), ContainerHost<PostState, PostEffect> {

    override val container = container<PostState, PostEffect>(PostState())

    
}
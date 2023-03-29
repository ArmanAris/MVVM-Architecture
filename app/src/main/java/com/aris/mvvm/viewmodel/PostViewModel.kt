package com.aris.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.mvvm.data.model.Post
import com.aris.mvvm.data.remote.ApiInterface
import com.aris.mvvm.repository.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: RetrofitRepository,
) : ViewModel() {

    val postList = MutableLiveData<List<Post>>()
    val postListFlow = MutableStateFlow<List<Post>>(emptyList())
    val postListError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun getAllPostsRequest() {
        loading.value = true

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getAllPosts()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { allPost ->

                        postList.value = allPost
                        postListFlow.value = allPost
                        postListFlow.emit(allPost)

                        postListError.value = null
                        loading.value = false
                    }
                } else {
                    postListError.value = response.message()
                    loading.value = false
                }
            }
        }
    }
}
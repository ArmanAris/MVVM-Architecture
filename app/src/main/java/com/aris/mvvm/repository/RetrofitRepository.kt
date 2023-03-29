package com.aris.mvvm.repository

import com.aris.mvvm.data.remote.ApiInterface
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val api: ApiInterface,
) {
    suspend fun getAllPosts() = api.getAllPosts()
}
package com.aris.mvvm.data.remote

import com.aris.mvvm.data.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    // GET: /Posts  All
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>

    // GET: /Posts/1  one
    @GET("/posts/{postId}")
    suspend fun getPostById(
        @Path(value = "postId",
            encoded = true) postId: String,
    ): Response<Post>

/*    // GET: /comments?postId=1   Group comment
    @GET("/comments")
    suspend fun getAllPostComments(
        @Query("postId") postId: Int,
    ): Response<List<Comments>>*/


    // POST: /posts   one
    @POST("/posts")
    suspend fun createNewPost(
        @Body newPost: Post,
    ): Response<Post>

}
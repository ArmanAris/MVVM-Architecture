package com.aris.mvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aris.mvvm.data.model.Post
import com.aris.mvvm.ui.theme.MVVMTheme
import com.aris.mvvm.viewmodel.DataStoreViewModel
import com.aris.mvvm.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    //   ObservePostViewModel()
                    TestDataStore()


                }

            }
        }
    }

    @Composable
    fun TestDataStore(viewModel: DataStoreViewModel = hiltViewModel()) {
        //  val viewModel by viewModels<DataStoreViewModel>()

        // set
        viewModel.saveUserPhone("091212345678")


        // get type 1
        viewModel.getUserPhone()
        LaunchedEffect(Dispatchers.Main) {
            viewModel.userPhone.collectLatest {
                Log.e("7171", "1 :::: $it")
            }
        }

        // get type 2
        LaunchedEffect(Dispatchers.Main) {
            val value = viewModel.getUserPhone2()
            Log.e("7171", "2 :::: $value")
        }

    }

    @Composable
    private fun ObservePostViewModel() {


        var postList by remember { mutableStateOf(emptyList<Post>()) }

        Column() {
            ShowPostList(postList = postList)
        }


        LaunchedEffect(key1 = Unit) {
            try {
                val viewModel by viewModels<PostViewModel>()

                viewModel.getAllPostsRequest()

                viewModel.postList.observe(this@MainActivity) { posts ->
                    postList = posts
                    Log.e("7171", "hi")
                }

                viewModel.postListFlow.collectLatest { posts ->
                    postList = posts
                }

                viewModel.postListError.observe(this@MainActivity) { message ->
                    message?.let {
                        Log.e("7171", message)
                    }
                }

                viewModel.loading.observe(this@MainActivity) {
                    Log.e("7171", it.toString())
                    // if true show animation else stop animation
                }
            } catch (ex: Exception) {
                Log.e("7171", ex.message.toString())
                Log.e("7171", ex.toString())
            }

        }
    }
}

@Composable
fun ShowPostList(postList: List<Post>) {
    LazyColumn() {
        items(postList) { post ->
            Column() {
                Text(text = post.body)
            }
        }
    }
}


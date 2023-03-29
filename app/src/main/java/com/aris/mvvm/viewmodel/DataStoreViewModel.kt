package com.aris.mvvm.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.mvvm.data.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val repository: DataStoreRepository) :
    ViewModel() {

    companion object {
        const val USER_PHONE_KEY = "USER_PHONE_KEY"
    }

    fun saveUserPhone(phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putString(USER_PHONE_KEY, phone)
        }
    }

    // type 1
    val userPhone = MutableStateFlow("")
    fun getUserPhone() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getString(USER_PHONE_KEY)?.let { value ->
                userPhone.emit(value)
            }
        }
    }

    // type 2
    suspend fun getUserPhone2(): String? = repository.getString(USER_PHONE_KEY)
}
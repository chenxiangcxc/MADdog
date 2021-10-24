package com.demo.MADdog.viewmodel

import androidx.lifecycle.*
import com.demo.MADdog.App
import com.demo.MADdog.repo.DogRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

class DogViewModel : ViewModel() {
    private var _dogNameList: MutableLiveData<List<String>> = MutableLiveData()
    val dogNameList: LiveData<List<String>> = _dogNameList

    val dogImageUrl = MutableSharedFlow<String>()

    private val dogRepo = DogRepo(App.getContext())

    fun getDogNameList() {
        viewModelScope.launch {
            dogRepo.getDogNameList().collectLatest {
                _dogNameList.postValue(it)
            }
        }
    }

    fun getDogImageUrl(name: String) {
        viewModelScope.launch {
            dogRepo.getDogImageUrl(name).collectLatest {
                dogImageUrl.emit(it)
            }
        }
    }
}
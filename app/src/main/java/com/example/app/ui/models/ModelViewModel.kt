package com.example.app.ui.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.app.data.ModelDao
import com.example.app.data.repository.ModelRepository
import com.example.app.extensions.disposeIfNotAlready
import com.example.app.models.SomeModel
import com.example.app.network.exceptions.NetworkExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ModelViewModel @Inject constructor(
    private val modelDao: ModelDao,
    private val state: SavedStateHandle,
    private val modelRepository: ModelRepository,
    private val networkExceptionHandler: NetworkExceptionHandler
) : ViewModel() {

    val modelList: MutableLiveData<List<SomeModel>> = MutableLiveData()
    val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    var modelDisposable: Disposable? = null
    val searchQuery = state.getLiveData("searchQuery", "")

    private val modelFlow =
        searchQuery.asFlow().map { query ->
             modelDao.getAllModels()
        }.map {
            it.map { catEntity -> catEntity.toModel() }
        }

    val modelsFiltered = modelFlow.asLiveData()

    fun getModelsList() {
        showProgress.postValue(true)
        modelDisposable = modelRepository.fetchModels()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .doAfterTerminate { showProgress.postValue(false) }
            .subscribeBy(
                onNext = {
                    modelList.postValue(it)
                },
                onError = {
                    showError.postValue(networkExceptionHandler.map(it).errorMessage)
                }
            )
    }

    override fun onCleared() {
        modelDisposable?.disposeIfNotAlready()
        super.onCleared()
    }
}
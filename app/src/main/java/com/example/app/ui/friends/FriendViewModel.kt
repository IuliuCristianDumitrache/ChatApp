package com.example.app.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.app.data.FriendDao
import com.example.app.data.repository.FriendRepository
import com.example.app.extensions.disposeIfNotAlready
import com.example.app.models.FriendModel
import com.example.app.network.exceptions.NetworkExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendDao: FriendDao,
    private val friendRepository: FriendRepository,
    private val networkExceptionHandler: NetworkExceptionHandler
) : ViewModel() {

    val friendList: MutableLiveData<List<FriendModel>> = MutableLiveData()
    val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    private var friendDisposable: Disposable? = null
    private var firstListDisposable: Disposable? = null

    fun getModelsList() {
        firstListDisposable = Observable.fromCallable {
            friendRepository.fetchFriends()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    friendList.postValue(it)
                },
                onError = {
                }
            )
    }

    fun addFriend(name: String) {
        //TODO - check if friend already exists, some validations
        friendDisposable = Observable.fromCallable {
            friendRepository.addFriend(FriendModel(UUID.randomUUID().toString(), name))
            friendRepository.fetchFriends()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    friendList.postValue(it)
                },
                onError = {
                }
            )
    }

    override fun onCleared() {
        friendDisposable?.disposeIfNotAlready()
        firstListDisposable?.disposeIfNotAlready()
        super.onCleared()
    }
}
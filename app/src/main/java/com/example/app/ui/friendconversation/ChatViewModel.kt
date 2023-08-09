package com.example.app.ui.friendconversation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.data.repository.ChatRepository
import com.example.app.extensions.disposeIfNotAlready
import com.example.app.models.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    val chatLiveData: MutableLiveData<MutableList<ChatMessage>> =
        MutableLiveData<MutableList<ChatMessage>>()
    val isFriendTyping = MutableLiveData(false)
    private var chatDisposable: Disposable? = null
    private var messageDisposable: Disposable? = null

    init {
        chatLiveData.value = ArrayList()
    }

    fun fetchHistory(friendId: String) {
        chatDisposable = Observable.fromCallable {
            chatRepository.fetchMessages(friendId)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    chatLiveData.postValue(it.toMutableList())
                },
                onError = {
                }
            )
    }

    fun sendMessage(friendId: String, friendName: String, message: String) {
        val chatMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            message = message,
            friendId = friendId,
            name = friendName,
            isSentByMe = true,
            date = System.currentTimeMillis()
        )

        messageDisposable = Observable.fromCallable {
            chatRepository.addMessage(chatMessage)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    chatLiveData.value?.add(chatMessage)
                    chatLiveData.value = chatLiveData.value

                    mockFriendResponse(friendId, friendName)
                },
                onError = {
                }
            )
    }

    private fun mockFriendResponse(friendId: String, friendName: String) {
        isFriendTyping.value = true

        val chatMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            message = "Salut sunt $friendName",
            friendId = friendId,
            name = friendName,
            isSentByMe = false,
            date = System.currentTimeMillis()
        )

        messageDisposable = Observable.fromCallable {
            chatRepository.addMessage(chatMessage)
        }.delay(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    isFriendTyping.value = false
                    chatLiveData.value?.add(chatMessage)
                    chatLiveData.value = chatLiveData.value
                },
                onError = {
                }
            )
    }

    override fun onCleared() {
        messageDisposable?.disposeIfNotAlready()
        chatDisposable?.disposeIfNotAlready()
        super.onCleared()
    }
}
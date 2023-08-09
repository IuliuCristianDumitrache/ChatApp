package com.example.app.models

import android.os.Parcelable
import com.example.app.data.FriendEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendModel(
    val id: String,
    val name: String,
    var lastMessage: String? = null
): Parcelable {
    fun mapToEntity(): FriendEntity {
        return FriendEntity(
            id = id,
            name = name,
        )
    }
}

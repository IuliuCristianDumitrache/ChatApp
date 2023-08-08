package com.example.app.models

import android.os.Parcelable
import com.example.app.data.ModelEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SomeModel(
    val id: String?,
    val name: String?
): Parcelable {
    fun mapToEntity(): ModelEntity {
        return ModelEntity(
            id = id ?: "",
            name = name ?: "",
        )
    }
}

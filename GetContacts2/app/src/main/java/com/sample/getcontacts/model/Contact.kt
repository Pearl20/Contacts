package com.sample.getcontacts.model

import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@Entity
class Contact {
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String = ""
    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String? = null
    @ColumnInfo(name = "photoUri")
    var photoUri: String? = null
    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean? = false


    companion object {
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view)
        }
    }
}
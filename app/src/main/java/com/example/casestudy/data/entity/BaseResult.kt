package com.example.casestudy.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "search")
@Parcelize
data class BaseResult(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @SerializedName("artistName")
    @ColumnInfo(name = "artistName") val artistName: String? = null,

    @SerializedName("artworkUrl100")
    @ColumnInfo(name = "artworkUrl100") val artworkUrl100: String? = null,

    @SerializedName("currency")
    @ColumnInfo(name = "currency") val currency: String? = null,

    @SerializedName("trackName")
    @ColumnInfo(name = "trackName") val trackName: String? = null,

    @SerializedName("kind")
    @ColumnInfo(name = "kind") val kind: String? = null,

    @SerializedName("releaseDate")
    @ColumnInfo(name = "releaseDate") val releaseDate: String? = null,

    @SerializedName("collectionPrice")
    @ColumnInfo(name = "collectionPrice") val collectionPrice: String? = null,

    @SerializedName("price")
    @ColumnInfo(name = "price") val price: String? = null,

    @SerializedName("collectionName")
    @ColumnInfo(name = "collectionName") val collectionName: String? = null,

    @SerializedName("trackId")
    @ColumnInfo(name = "trackId") val trackId: Int? = null
) : Parcelable
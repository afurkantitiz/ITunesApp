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
    @ColumnInfo(name = "artistName") val artistName: String?,

    @SerializedName("artworkUrl100")
    @ColumnInfo(name = "artworkUrl100") val artworkUrl100: String?,

    @SerializedName("currency")
    @ColumnInfo(name = "currency") val currency: String?,

    @SerializedName("trackName")
    @ColumnInfo(name = "trackName") val trackName: String?,

    @SerializedName("kind")
    @ColumnInfo(name = "kind") val kind: String?,

    @SerializedName("releaseDate")
    @ColumnInfo(name = "releaseDate") val releaseDate: String?,

    @SerializedName("collectionPrice")
    @ColumnInfo(name = "collectionPrice") val collectionPrice: String?,

    @SerializedName("price")
    @ColumnInfo(name = "price") val price: String?,

    @SerializedName("collectionName")
    @ColumnInfo(name = "collectionName") val collectionName: String?,
) : Parcelable
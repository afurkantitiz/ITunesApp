package com.example.casestudy.utils

class ImageResizer {
    companion object {
        fun getBigImage(url: String): String {
            return try {
                url.replace("100x100bb.jpg", "300x300bb.jpg")
            } catch (e: Exception) {
                url
            }
        }

        fun getMediumImage(url: String): String {
            return try {
                url.replace("100x100bb.jpg", "200x200bb.jpg")
            } catch (e: Exception) {
                url
            }
        }
    }
}
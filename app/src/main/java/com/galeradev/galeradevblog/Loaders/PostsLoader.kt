package com.galeradev.galeradevblog.Loaders

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL

class PostsLoader {
    companion object {
        fun getPostsLoaderObservable(url: String): Observable<String> {
            val observable = Observable.create<String> {
                val connection = URL(url).openConnection() as HttpURLConnection
                try {
                    connection.connect()
                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        it.onError(RuntimeException(connection.responseMessage))
                    } else {
                        val response = connection.inputStream.bufferedReader().readText()
                        it.onNext(response)
                    }
                } finally {
                    connection.disconnect()
                }
            }
            return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

        fun getImageLoaderObservable(url: String): Observable<Bitmap> {
            val observable = Observable.create<Bitmap> {
                val connection = URL(url).openConnection() as HttpURLConnection
                try {
                    connection.connect()
                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        it.onError(RuntimeException(connection.responseMessage))
                    } else {
                        val bmp = BitmapFactory.decodeStream(connection.inputStream)
                        it.onNext(bmp)
                    }
                } finally {
                    connection.disconnect()
                }
            }
            return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
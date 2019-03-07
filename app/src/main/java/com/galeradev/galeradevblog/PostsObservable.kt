package com.galeradev.galeradevblog

import io.reactivex.Observable
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL

class PostsObservable {
    companion object {
        fun getPosts(url: String): Observable<String> {
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
            return observable
        }
    }
}
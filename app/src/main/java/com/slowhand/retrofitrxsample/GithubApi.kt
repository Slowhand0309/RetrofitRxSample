package com.slowhand.retrofitrxsample

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    @GET("users/{username}")
    fun getUser(@Path("username") user: String): Single<User>
}
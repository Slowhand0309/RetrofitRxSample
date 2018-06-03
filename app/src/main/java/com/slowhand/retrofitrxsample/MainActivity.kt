package com.slowhand.retrofitrxsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

        val httpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)

        // log
        val logInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        if (!httpClientBuilder.interceptors().contains(logInterceptor)) {
            httpClientBuilder.addInterceptor(logInterceptor)
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(GithubApi.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClientBuilder.build())
                .build()

        val service = retrofit.create(GithubApi::class.java)
        service.getUser("Slowhand0309").subscribe({ ret ->
            Log.d(TAG, "ret $ret")
        }, { error ->
            Log.e(TAG, error.message)
        })
    }
}

package com.slowhand.retrofitrxsample

import com.squareup.moshi.Json

data class User(
    @Json(name = "name")
    var name: String,
    @Json(name = "login")
    var login: String,
    @Json(name = "blog")
    var blog: String,
    @Json(name = "type")
    var type: String
)
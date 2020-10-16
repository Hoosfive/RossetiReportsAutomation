package ru.unknowncoder.rossetireportsautomation.model.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.unknowncoder.rossetireportsautomation.domain.AuthBody
import ru.unknowncoder.rossetireportsautomation.model.response.LoginResponseBody
import ru.unknowncoder.rossetireportsautomation.model.response.MemesResponseBody

interface MemeApi {

    @POST("/auth/login")
    fun login(@Body auth: AuthBody): Call<LoginResponseBody>

    @GET("/memes")
    fun getMemes(): Call<List<MemesResponseBody>>
}

package ru.unknowncoder.rossetireportsautomation.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.unknowncoder.rossetireportsautomation.domain.AuthBody
import ru.unknowncoder.rossetireportsautomation.model.response.LoginResponseBody
import ru.unknowncoder.rossetireportsautomation.model.response.MemesResponseBody

class NetworkService private constructor() {
    companion object {
        private const val BASE_URL = "https://demo2407529.mockable.io"

        private val interceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        private val MemeApi = retrofit.create(ru.unknowncoder.rossetireportsautomation.model.api.MemeApi::class.java)

        fun auth(
            login: String,
            password: String,
            onSuccess: (LoginResponseBody) -> Unit,
            onError: (Throwable) -> Unit = { }
        ) {
            MemeApi
                .login(AuthBody(login, password))
                .enqueue(
                    RetrofitCallback(
                        { data -> onSuccess(data) },
                        { error -> onError(error) }
                    )
                )
        }

        fun getMemes(
            onSuccess: (List<MemesResponseBody>) -> Unit,
            onError: (Throwable) -> Unit = { }
        ) {
            MemeApi
                .getMemes()
                .enqueue(
                    RetrofitCallback(
                        { data -> onSuccess(data) },
                        { error -> onError(error) }
                    )
                )
        }
    }
}
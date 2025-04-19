package com.alaishat.mohammad.clean.docdoc.data

import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
class RequestInterceptor(
    private val userLocalDataRepo: UserLocalDataRepo,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var token = runBlocking { userLocalDataRepo.getTokenFlow().firstOrNull() }
        if (token == null) {
            token = AuthenticationCredentialsRepo.guestToken
        }
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}

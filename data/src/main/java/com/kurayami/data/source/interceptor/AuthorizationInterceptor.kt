package com.kurayami.data.source.interceptor

import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import com.kurayami.data.repository.PreferencesDataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor(private val preferencesDataStoreRepository: PreferencesDataStoreRepository) :
    HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain,
    ): HttpResponse {
        return runBlocking {
            val newRequest = request.newBuilder().apply {
                preferencesDataStoreRepository.getAccessToken().first()?.let {
                    addHeader(
                        "Authorization",
                        "Bearer $it"
                    )
                }
            }.build()
            chain.proceed(newRequest)
        }
    }
}

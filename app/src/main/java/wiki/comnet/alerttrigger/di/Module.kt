package wiki.comnet.alerttrigger.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import wiki.comnet.alerttrigger.data.local.prefs.PrefsManager
import wiki.comnet.alerttrigger.data.remote.ApiService
import wiki.comnet.alerttrigger.domain.repository.AccessTokenRepository
import wiki.comnet.alerttrigger.presentation.home.HomeViewModel
import wiki.comnet.alerttrigger.presentation.login.LoginViewModel
import wiki.comnet.alerttrigger.presentation.root.RootViewModel

val appModule = module {
    single(named("ApplicationScope")) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    single { PrefsManager(androidContext()) }
    viewModel {
        RootViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
}

val networkModule = module {
    single {
        val accessTokenRepository: AccessTokenRepository = get()

        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    explicitNulls = false
                })
            }
            install(Logging) {
                level = LogLevel.INFO
            }
            defaultRequest {
                accept(ContentType.Application.Json)
                val token = accessTokenRepository.accessToken().value
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }
        }
    }

    single { ApiService(get()) }
}
package wiki.comnet.alerttrigger.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import wiki.comnet.alerttrigger.data.repository.AccessTokenRepositoryImpl
import wiki.comnet.alerttrigger.data.repository.AuthRepositoryImpl
import wiki.comnet.alerttrigger.data.repository.ShellyRepositoryImpl
import wiki.comnet.alerttrigger.domain.repository.AccessTokenRepository
import wiki.comnet.alerttrigger.domain.repository.AuthRepository
import wiki.comnet.alerttrigger.domain.repository.ShellyRepository


val repositoryModule = module {
    single<AccessTokenRepository> {
        AccessTokenRepositoryImpl()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get(), get())
    }
    single<ShellyRepository> {
        ShellyRepositoryImpl(get(), get())
    }
}
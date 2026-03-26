package wiki.comnet.alerttrigger.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import wiki.comnet.alerttrigger.data.repository.AccessTokenRepositoryImpl
import wiki.comnet.alerttrigger.data.repository.AuthRepositoryImpl
import wiki.comnet.alerttrigger.data.repository.DeviceIdRepositoryImpl
import wiki.comnet.alerttrigger.data.repository.ShellyRepositoryImpl
import wiki.comnet.alerttrigger.data.repository.UserMessageRepositoryImpl
import wiki.comnet.alerttrigger.domain.repository.AccessTokenRepository
import wiki.comnet.alerttrigger.domain.repository.AuthRepository
import wiki.comnet.alerttrigger.domain.repository.DeviceIdRepository
import wiki.comnet.alerttrigger.domain.repository.ShellyRepository
import wiki.comnet.alerttrigger.domain.repository.UserMessageRepository


val repositoryModule = module {
    single<DeviceIdRepository> {
        DeviceIdRepositoryImpl(androidContext())
    }
    single<AccessTokenRepository> {
        AccessTokenRepositoryImpl()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get(), get(), get())
    }
    single<ShellyRepository> {
        ShellyRepositoryImpl(get(), get(), get())
    }
    single<UserMessageRepository> {
        UserMessageRepositoryImpl(get(), get())
    }
}
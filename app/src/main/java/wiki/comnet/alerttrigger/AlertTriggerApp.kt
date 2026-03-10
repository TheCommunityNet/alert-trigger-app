package wiki.comnet.alerttrigger

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import wiki.comnet.alerttrigger.di.appModule
import wiki.comnet.alerttrigger.di.databaseModule
import wiki.comnet.alerttrigger.di.networkModule
import wiki.comnet.alerttrigger.di.repositoryModule

class AlertTriggerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AlertTriggerApp)
            androidLogger()
            modules(appModule, networkModule, databaseModule, repositoryModule)
        }
    }
}
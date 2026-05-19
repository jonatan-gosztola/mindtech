package hu.gjonatan.mindtech

import android.app.Application
import hu.gjonatan.mindtech.di.dataModule
import hu.gjonatan.mindtech.di.networkingModule
import hu.gjonatan.mindtech.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(viewModelModule, dataModule, networkingModule)
        }
    }
}
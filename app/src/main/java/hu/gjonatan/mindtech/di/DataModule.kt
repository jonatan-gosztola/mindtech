package hu.gjonatan.mindtech.di

import hu.gjonatan.mindtech.data.DataRepository
import org.koin.dsl.module

val dataModule = module {
    single { DataRepository() }
}

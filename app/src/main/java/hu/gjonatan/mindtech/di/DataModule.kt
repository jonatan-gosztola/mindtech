package hu.gjonatan.mindtech.di

import hu.gjonatan.mindtech.data.ApiDataSource
import hu.gjonatan.mindtech.data.DataRepository
import hu.gjonatan.mindtech.data.DefaultDataRepository
import org.koin.dsl.module

val dataModule = module {
    single { ApiDataSource(client = get()) }
    single<DataRepository> { DefaultDataRepository(apiDataSource = get()) }
}

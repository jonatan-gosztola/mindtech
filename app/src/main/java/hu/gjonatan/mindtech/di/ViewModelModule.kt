package hu.gjonatan.mindtech.di

import hu.gjonatan.mindtech.ui.screens.details.DetailsScreenViewModel
import hu.gjonatan.mindtech.ui.screens.main.MainScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::DetailsScreenViewModel)
}
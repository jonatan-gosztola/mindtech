package hu.gjonatan.mindtech.di

import hu.gjonatan.mindtech.ui.screens.details.DetailsScreenViewModel
import hu.gjonatan.mindtech.ui.screens.main.MainScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val viewModelModule = module {
    viewModelOf(::MainScreenViewModel)
    viewModel { params -> 
        DetailsScreenViewModel(
            name = params.get(),
            dataRepository = get()
        ) 
    }
}
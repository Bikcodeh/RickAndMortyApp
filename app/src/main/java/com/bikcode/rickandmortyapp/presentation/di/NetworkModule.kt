package com.bikcode.rickandmortyapp.presentation.di

import com.bikcode.rickandmortyapp.presentation.api.APIService
import com.bikcode.rickandmortyapp.presentation.api.RetrofitClient
import org.koin.dsl.module

val networkModule = module {
    single { RetrofitClient().createWebService().create(APIService::class.java) }
}
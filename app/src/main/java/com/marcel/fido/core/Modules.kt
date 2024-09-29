package com.marcel.fido.core

import org.koin.dsl.module

val coreModule = module {
    single {
        retrofit
    }
}
package com.marcel.fido

import android.app.Application
import com.marcel.fido.articles.articleModule
import com.marcel.fido.core.coreModule
import com.marcel.fido.sources.sourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FidoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FidoApplication)
            modules(
                listOf(
                    coreModule,
                    articleModule,
                    sourceModule
                )
            )
        }
    }
}
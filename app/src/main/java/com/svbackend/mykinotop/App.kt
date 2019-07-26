package com.svbackend.mykinotop

import android.app.Application
import com.svbackend.mykinotop.api.ApiService
import com.svbackend.mykinotop.api.UserDataSource
import com.svbackend.mykinotop.api.UserDataSourceImpl
import com.svbackend.mykinotop.db.Db
import com.svbackend.mykinotop.db.UserRepository
import com.svbackend.mykinotop.db.UserRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { Db(instance()) }
        bind() from singleton { instance<Db>().userDao() }
        bind() from singleton { ApiService() }
        bind<UserDataSource>() with singleton { UserDataSourceImpl(instance()) }
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }
        // todo bind Repositories with dependencies
    }
}
package com.example.moviedbappwithmap.presentation.di

import android.util.Log
import com.example.moviedbappwithmap.data.CinemaDataBase.CinemaDBRoom
import com.example.moviedbappwithmap.data.network.MovieApi
import com.example.moviedbappwithmap.data.repository.MovieRepositoryImpl
import com.example.moviedbappwithmap.data.repository.UserRepositoryImpl
import com.example.moviedbappwithmap.domain.repository.MovieRepository
import com.example.moviedbappwithmap.domain.repository.UserRepository
import com.example.moviedbappwithmap.presentation.login.LoginViewModel
import com.example.moviedbappwithmap.presentation.map.CinemaViewModel
import com.example.moviedbappwithmap.presentation.movie.details.MovieDetailViewModel
import com.example.moviedbappwithmap.presentation.movie.favorite.FavoriteMoviesViewModel
import com.example.moviedbappwithmap.presentation.movie.list.MovieListViewModel
import com.example.moviedbappwithmap.utils.AppConstants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single(named("api_key")) { provideApiKey() }
    single(named("base_url")) { provideBaseUrl() }
    single { provideHttpLoggingInterceptor() }
    single { provideStethoInterceptor() }
    single { provideAuthInterceptor(apiKey = get(named("api_key"))) }
    single { provideOkHttp(
        loggingInterceptor = get(),
        stethoInterceptor = get(),
        authInterceptor = get()
    )}
    single { provideCallAdapterFactory() }
    single { provideConverterFactory() }
    single { provideRetrofit(
        baseUrl = get(named("base_url")),
        okHttpClient = get(),
        gsonConverterFactory = get(),
        callAdapterFactory = get()
    )}
    single { provideMovieApi(retrofit = get()) }
}


val roomModule = module {
    single { CinemaDBRoom.getDatabase(
        context = androidApplication(),
        scope = get()
    ) }

    single(createdAtStart = false) {
        get<CinemaDBRoom>().daoCinema()
    }

    factory { SupervisorJob() }
    factory { CoroutineScope(Dispatchers.IO) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(userRepository = get()) }
    viewModel { MovieListViewModel(movieRepository = get()) }
    viewModel { MovieDetailViewModel(movieRepository = get()) }
    viewModel { FavoriteMoviesViewModel(movieRepository = get()) }
    viewModel { CinemaViewModel(daoCinemas = get()) }
}

//network
fun provideApiKey(): String = AppConstants.API_KEY

fun provideBaseUrl(): String = AppConstants.BASE_URL

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message)}
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()

fun provideAuthInterceptor(apiKey: String): Interceptor {
    return Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}

fun provideOkHttp(
    loggingInterceptor: HttpLoggingInterceptor,
    stethoInterceptor: StethoInterceptor,
    authInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(stethoInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

fun provideCallAdapterFactory(): CallAdapter.Factory = CoroutineCallAdapterFactory()

fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: Converter.Factory,
    callAdapterFactory: CallAdapter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
}

fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

//repositories
val repositoryModule = module {
    single {provideMovieRepository(movieApi = get()) }
    single {provideUserRepository(movieApi = get()) }
}

fun provideMovieRepository(movieApi: MovieApi): MovieRepository = MovieRepositoryImpl(movieApi)

fun provideUserRepository(movieApi: MovieApi): UserRepository = UserRepositoryImpl(movieApi)

val appModule = listOf(networkModule, repositoryModule, viewModelModule, roomModule)
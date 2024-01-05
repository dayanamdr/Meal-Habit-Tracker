package com.example.mealhabittracker.di

import android.app.Application
import androidx.room.Room
import com.example.mealhabittracker.feature_meal.data.client.MealService
import com.example.mealhabittracker.feature_meal.data.data_source.MealDatabase
import com.example.mealhabittracker.feature_meal.data.repository.MealRepositoryImpl
import com.example.mealhabittracker.feature_meal.data.repository.ServerMealRepositoryImpl
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository
import com.example.mealhabittracker.feature_meal.domain.repository.ServerMealRepository
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.AddMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.DeleteMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.server_use_case.DeleteMealServer
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.GetMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.GetMeals
import com.example.mealhabittracker.feature_meal.domain.use_case.MealUseCases
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.SynchronizeData
import com.example.mealhabittracker.feature_meal.domain.use_case.server_use_case.AddMealServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.128:4023")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesMoviesService(retrofit: Retrofit): MealService {
        return retrofit.create(MealService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealDatabase(app: Application): MealDatabase {
        return Room.databaseBuilder(
            app,
            MealDatabase::class.java,
            MealDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMealRepository(db: MealDatabase, mealService: MealService): MealRepository {
        return MealRepositoryImpl(db.mealDao, mealService)
    }

    @Provides
    @Singleton
    fun provideServerMealRepository(mealService: MealService): ServerMealRepository {
        return ServerMealRepositoryImpl(mealService)
    }

    @Provides
    @Singleton
    fun provideMealUseCases(repository: MealRepository, serverRepository: ServerMealRepository): MealUseCases {
        return MealUseCases(
            getMeals = GetMeals(repository),
            deleteMeal = DeleteMeal(repository),
            addMeal = AddMeal(repository),
            getMeal = GetMeal(repository),
            synchronizeData = SynchronizeData(repository),
            addMealServer = AddMealServer(serverRepository),
            deleteMealServer = DeleteMealServer(serverRepository)
        )
    }
}
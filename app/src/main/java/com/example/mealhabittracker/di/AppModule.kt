package com.example.mealhabittracker.di

import android.app.Application
import androidx.room.Room
import com.example.mealhabittracker.feature_meal.data.data_source.MealDatabase
import com.example.mealhabittracker.feature_meal.data.repository.MealRepositoryImpl
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository
import com.example.mealhabittracker.feature_meal.domain.use_case.AddMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.DeleteMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.GetMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.GetMeals
import com.example.mealhabittracker.feature_meal.domain.use_case.MealUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideMealRepository(db: MealDatabase): MealRepository {
        return MealRepositoryImpl(db.mealDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: MealRepository): MealUseCases {
        return MealUseCases(
            getMeals = GetMeals(repository),
            deleteMeal = DeleteMeal(repository),
            addMeal = AddMeal(repository),
            getMeal = GetMeal(repository)
        )
    }
}
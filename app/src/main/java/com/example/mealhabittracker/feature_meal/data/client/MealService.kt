package com.example.mealhabittracker.feature_meal.data.client

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MealService {
    @GET("/meal/{id}")
    suspend fun retrieveMeal(@Path("id") id: Int) : Call<Meal>

    @GET("/meals")
    suspend fun retrieveAllMeals() : Response<List<Meal>>

    @DELETE("/meal/{id}")
    suspend fun deleteMeal(@Path("id") id: Int?) : Response<Meal>

    @HTTP(method = "DELETE", path = "/meals", hasBody = true)
    suspend fun deleteMeals(@Body meals: List<Meal>)

    @POST("/meal")
    suspend fun createMeal(@Body meal: Meal) : Response<Meal>

    @POST("/meals")
    suspend fun addMeals(@Body meals: List<Meal>)

    @PUT("/meal/{id}")
    suspend fun updateMeal(@Path("id") id: Int, @Body meal: Meal) : Response<Meal>

    @PUT("/meals")
    suspend fun updateAll(@Body meals: List<Meal>)
}
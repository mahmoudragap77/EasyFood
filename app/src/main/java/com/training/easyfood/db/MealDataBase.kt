package com.training.easyfood.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.training.easyfood.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealDbConverter::class)
abstract class MealDataBase() : RoomDatabase() {

    abstract fun dao() : MealDao

    companion object{
        @Volatile
        var INSTANCE : MealDataBase? =null

        @Synchronized
        fun getInstance(context: Context): MealDataBase{
            if (INSTANCE==null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDataBase
        }
    }
}
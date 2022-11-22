package com.rmtz.weather.data

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [User::class, Location::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun AppDao(): AppDao
}
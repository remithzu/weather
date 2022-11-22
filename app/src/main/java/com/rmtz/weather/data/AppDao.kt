package com.rmtz.weather.data

import androidx.room.*

@Dao
interface AppDao {
    @Query("SELECT * FROM user Limit 1")
    fun getListUser(): List<User>

    @Insert
    fun insertUser(vararg user: User)

    @Update
    fun updateUser(vararg user: User)

    @Query("SELECT * FROM location")
    fun getListLocation(): List<Location>

    @Insert
    fun insertLocation(vararg location: Location)

    @Update
    fun updateLocation(vararg location: Location)

    @Delete
    fun deleteLocation(location: Location)
}
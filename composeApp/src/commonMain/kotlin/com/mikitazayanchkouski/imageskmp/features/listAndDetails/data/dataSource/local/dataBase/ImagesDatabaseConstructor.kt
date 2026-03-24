package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase

import androidx.room.RoomDatabaseConstructor

/** Need for Room to generate this constructor for us,
 * actual implementations of the constructor will be generated
 * by the Room KSP compiler plugin.
 * So we don't need to implement it ourselves explicitly.
 *
 * In traditional Android development, Room uses reflection
 * to find the generated implementation of the database.
 * When we call Room.databaseBuilder(...).build(), Room would look for a class
 * named (YourDatabase_Impl) at runtime and instantiate it.
 *
 * But on platforms like iOS (Kotlin/Native), reflection is highly restricted
 * or non-existent for this type of use case.
 * We can't just "look up" a class string at runtime.
 *
 * To solve the "no reflection" problem - Room KMP
 * introduced the RoomDatabaseConstructor interface.
 * It acts as a static factory.
 * Instead of Room "guessing" where the implementation is, we explicitly tell Room:
 * "Hey, use this object to create the database instance."
 *
 * That's why we need this constructor class.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ImagesDatabaseConstructor: RoomDatabaseConstructor<ImagesDatabase> {
    override fun initialize(): ImagesDatabase
}
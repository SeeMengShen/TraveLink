package my.edu.tarc.travelink.ui.wallet.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Trip::class), version = 1, exportSchema = false)
@TypeConverters(TripConverter::class)
abstract class TripDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    companion object {
        //singleton
        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getDatabase(context: Context): TripDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripDatabase::class.java,
                    "db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
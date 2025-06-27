package hr.fer.carpulse.data.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE trip_start_info ADD COLUMN driverEmail TEXT NOT NULL DEFAULT ''")
    }
}
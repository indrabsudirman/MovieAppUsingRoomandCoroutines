package id.indrasudirman.movieappusingroomandcoroutines.database

import androidx.room.*
import id.indrasudirman.movieappusingroomandcoroutines.database.Director.Companion.FULL_NAME
import id.indrasudirman.movieappusingroomandcoroutines.database.Director.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [FULL_NAME], unique = true)]
)
data class Director(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "did") var id: Long = 0,
    @ColumnInfo(name = FULL_NAME) var fullName: String,
    @Ignore var age: Int = 0) {
    constructor() : this (0L, "", 0)

    companion object {
        const val TABLE_NAME = "director"
        const val FULL_NAME = "full_name"
    }
}
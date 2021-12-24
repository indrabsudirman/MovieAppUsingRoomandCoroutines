package id.indrasudirman.movieappusingroomandcoroutines.database

import androidx.room.*
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie.Companion.TABLE_NAME
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie.Companion.DIRECTOR_ID
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie.Companion.TITLE

@Entity(
    tableName = TABLE_NAME,
foreignKeys = [ForeignKey(
    entity = Director::class,
    parentColumns = ["did"],
    childColumns = [DIRECTOR_ID],
    onDelete = ForeignKey.CASCADE
)],
indices = [Index(TITLE), Index(DIRECTOR_ID)])

data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid") var id: Long = 0,
    @ColumnInfo(name = TITLE) var title: String,
    @ColumnInfo(name = DIRECTOR_ID) var directorId: Long) {

    companion object {
        const val TABLE_NAME = "movie"
        const val TITLE = "title"
        const val DIRECTOR_ID = "directorId"
    }
}
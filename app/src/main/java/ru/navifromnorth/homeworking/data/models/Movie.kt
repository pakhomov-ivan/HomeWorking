package ru.navifromnorth.homeworking.data.models

import android.os.Parcel
import android.os.Parcelable
import ru.navifromnorth.homeworking.R
import java.util.*

data class Movie(
    val titleId: Int,
    val previewImageId: Int,
    var hasLike: Boolean,
    val PG: Int,
    val tags: Set<Int>,
    val rating: Float,
    val countReviews: Int,
    val runtimeInMinutes: Int,
) : Parcelable {
    var storylineId: Int? = null
    var cast: Set<Actor>? = null
    var backgroundPosterId: Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.createIntArray()?.toSet() ?: setOf<Int>(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt()
    ) {
        storylineId = parcel.readValue(Int::class.java.classLoader) as? Int
        backgroundPosterId = parcel.readValue(Int::class.java.classLoader) as? Int
        cast = parcel.readParcelableArray(Actor::class.java.classLoader) as? Set<Actor>?
    }


    fun initializeDetails() {
        when (titleId) {
            R.string.avengers_end_game -> {
                storylineId = R.string.avengers_end_game_storyline
                backgroundPosterId = R.drawable.avengers_end_game_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.robert_downey_jr,
                        avatarId = R.drawable.actor_robert_downey_jr
                    ),
                    Actor(
                        nameId = R.string.chris_evans,
                        avatarId = R.drawable.actor_chris_evans,
                    ),
                    Actor(
                        nameId = R.string.mark_ruffalo,
                        avatarId = R.drawable.actor_mark_ruffalo
                    ),
                    Actor(
                        nameId = R.string.chris_hemsworth,
                        avatarId = R.drawable.actor_chris_hemsworth
                    )
                )
            }

            R.string.tenet -> {
                storylineId = R.string.tenet_storyline
                backgroundPosterId = R.drawable.tenet_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.john_david_washington,
                        avatarId = R.drawable.actor_john_david_washington
                    ),
                    Actor(
                        nameId = R.string.robert_pattinson,
                        avatarId = R.drawable.actor_robert_pattinson
                    )
                )
            }

            R.string.black_widow -> {
                storylineId = R.string.black_widow_storyline
                backgroundPosterId = R.drawable.black_widow_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.scarlett_johansson,
                        avatarId = R.drawable.actor_scarlett_johansson
                    )
                )
            }

            R.string.wonder_women_1984 -> {
                storylineId = R.string.wonder_women_1984_storyline
                backgroundPosterId = R.drawable.wonder_women_1984_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.gal_gadot,
                        avatarId = R.drawable.actor_gal_gadot
                    )
                )
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(titleId)
        parcel.writeInt(previewImageId)
        parcel.writeByte(if (hasLike) 1 else 0)
        parcel.writeInt(PG)
        parcel.writeFloat(rating)
        parcel.writeInt(countReviews)
        parcel.writeInt(runtimeInMinutes)
        parcel.writeValue(storylineId)
        parcel.writeValue(backgroundPosterId)
        parcel.writeIntArray(tags.toIntArray())
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
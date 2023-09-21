package me.cniekirk.mastodroid.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import me.cniekirk.mastodroid.datastore.Preferences
import java.io.InputStream
import java.io.OutputStream

object PreferencesSerializer : Serializer<Preferences> {

    override val defaultValue: Preferences = Preferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Preferences {
        try {
            return Preferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Preferences, output: OutputStream) = t.writeTo(output)
}
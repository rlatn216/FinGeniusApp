package com.skim.core.datastore.serializer

import androidx.datastore.core.Serializer
import com.skim.core.datastore.AccessToken
import java.io.InputStream
import java.io.OutputStream

object AccessTokenSerializer : Serializer<AccessToken> {

    override val defaultValue: AccessToken = AccessToken.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AccessToken {
        return AccessToken.parseFrom(input)
    }

    override suspend fun writeTo(t: AccessToken, output: OutputStream) = t.writeTo(output)

}
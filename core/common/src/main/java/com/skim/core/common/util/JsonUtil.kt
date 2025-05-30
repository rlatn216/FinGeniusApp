package com.skim.core.common.util

import com.skim.core.common.di.CommonModule
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class JsonUtil @Inject constructor(
    val json: Json,
    @CommonModule.PrettyJson val prettyJson: Json
) {

    fun <T> toJson(serializer: SerializationStrategy<T>, value: T) =
        json.encodeToString(serializer, value)

    inline fun <reified T> toJson(value: T, pretty: Boolean = false): String = if (pretty)
        prettyJson.encodeToString(value)
    else
        json.encodeToString(value)

    fun <T> toJsonBytes(serializer: SerializationStrategy<T>, stream: OutputStream) =
        json.encodeToStream(serializer, stream)

    inline fun <reified T> toJsonBytes(value: T, stream: OutputStream) =
        json.encodeToStream(value, stream)

    fun <T> fromJson(serializer: DeserializationStrategy<T>, jsonStr: String) =
        json.decodeFromString(serializer, jsonStr)

    inline fun <reified T> fromJson(jsonStr: String): T = json.decodeFromString(jsonStr)

    fun <T> fromJson(serializer: DeserializationStrategy<T>, stream: InputStream) =
        json.decodeFromStream(serializer, stream)

    inline fun <reified T> fromJson(stream: InputStream): T = json.decodeFromStream(stream)
}

fun JsonObject.toMap(): Map<String, Any> = mapValues { entry ->
    entry.value.extractedContent() ?: ""
}

fun JsonObject.toStringValueMap(): Map<String, String> = mapValues { entry ->
    entry.value.extractedContent()?.toString() ?: ""
}

fun JsonElement.extractedContent(): Any? {
    return when (this) {
        is JsonPrimitive -> if (this.jsonPrimitive.isString) {
            return jsonPrimitive.content
        } else {
            return jsonPrimitive.booleanOrNull ?: jsonPrimitive.intOrNull
            ?: jsonPrimitive.longOrNull ?: jsonPrimitive.floatOrNull ?: jsonPrimitive.doubleOrNull
            ?: jsonPrimitive.contentOrNull
        }

        is JsonArray -> jsonArray.map {
            it.extractedContent
        }

        is JsonObject -> jsonObject.entries.associate {
            it.key to it.value.extractedContent
        }

        else -> null
    }
}

private val JsonElement.extractedContent: Any?
    get() {
        when (this) {
            is JsonPrimitive -> {
                if (this.jsonPrimitive.isString) {
                    return this.jsonPrimitive.content
                }
                return this.jsonPrimitive.booleanOrNull ?: this.jsonPrimitive.intOrNull
                ?: this.jsonPrimitive.longOrNull ?: this.jsonPrimitive.floatOrNull
                ?: this.jsonPrimitive.doubleOrNull ?: this.jsonPrimitive.contentOrNull
            }

            is JsonArray -> {
                return this.jsonArray.map {
                    it.extractedContent
                }
            }

            is JsonObject -> {
                return this.jsonObject.entries.associate {
                    it.key to it.value.extractedContent
                }
            }

            else -> return null
        }
    }


fun Any.toJsonElement(): JsonElement = when (this) {
    is Int -> JsonPrimitive(this)
    is Double -> JsonPrimitive(this)
    is Float -> JsonPrimitive(this)
    is Boolean -> JsonPrimitive(this)
    else -> JsonPrimitive(this.toString())
}
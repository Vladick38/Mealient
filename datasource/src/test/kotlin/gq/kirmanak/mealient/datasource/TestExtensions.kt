package gq.kirmanak.mealient.datasource

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

fun String.toJsonResponseBody() = toResponseBody("application/json".toMediaType())


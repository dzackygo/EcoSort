package com.app.ecosort.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UploadResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("messages")
	val messages: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("image_detail")
	val imageDetail: List<ImageDetailItem>
)

data class ImageDetailItem(

	@field:SerializedName("confidence")
	val confidence: Double,

	@field:SerializedName("sorting")
	val sorting: String,

	@field:SerializedName("classification")
	val classification: String
) : Serializable

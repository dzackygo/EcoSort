package com.app.ecosort.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("messages")
	val messages: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("image_detail")
	val imageDetail: Any
)

package com.app.ecosort.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("messages")
	val messages: String,

	@field:SerializedName("status")
	val status: Int
)

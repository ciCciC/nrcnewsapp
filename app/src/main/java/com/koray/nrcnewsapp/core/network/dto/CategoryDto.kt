package com.koray.nrcnewsapp.core.network.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.koray.nrcnewsapp.core.abstraction.Category

data class CategoryDto(
    @JsonProperty("display")
    override var display: String? = "",
    @JsonProperty("topic")
    override var topic: String? = ""
) : Category
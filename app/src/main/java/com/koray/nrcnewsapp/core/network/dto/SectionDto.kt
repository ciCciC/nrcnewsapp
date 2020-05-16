package com.koray.nrcnewsapp.core.network.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.koray.nrcnewsapp.core.abstraction.Section

class SectionDto(

    @JsonProperty("title")
    override var title: String?,

    @JsonProperty("contentBody")
    override var contents: Array<ContentBodyDto>?

) : Section {
}
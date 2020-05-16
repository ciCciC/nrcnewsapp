package com.koray.nrcnewsapp.core.network.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.koray.nrcnewsapp.core.abstraction.ContentBody

class ContentBodyDto(

    @JsonProperty("content")
    override var content: String?,

    @JsonProperty("cType")
    override var cType: String?

): ContentBody {

    override fun equals(other: Any?): Boolean {
        return if(other is ContentBodyDto)
            other.cType.equals(this.cType) && other.content.equals(this.content)
        else
            false
    }

    override fun hashCode(): Int {
        return this.cType.hashCode() + this.content.hashCode()
    }
}
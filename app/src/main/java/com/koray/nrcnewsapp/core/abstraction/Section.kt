package com.koray.nrcnewsapp.core.abstraction

import com.koray.nrcnewsapp.core.network.dto.ContentBodyDto

interface Section {
    var title: String?
    var contents: Array<ContentBodyDto>?
}
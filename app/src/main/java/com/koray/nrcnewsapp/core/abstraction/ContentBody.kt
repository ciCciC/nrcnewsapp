package com.koray.nrcnewsapp.core.abstraction

interface ContentBody {
    var content: String?
    var cType: String?
}

enum class Ctype {
    h2,
    p,
    img
}
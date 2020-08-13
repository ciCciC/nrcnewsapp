package com.koray.nrcnewsapp.core.network.caching

interface Cache<Key, Value> {
    fun add(key: Key, value: Value)
    fun get(key: Key) : Value?
    fun remove(key: Key)
    fun removeAll()
}
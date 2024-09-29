package com.marcel.fido.core

import kotlin.reflect.KProperty1

class Cache<K, T> {
    private var cache: MutableList<T> = mutableListOf()

    fun addAll(items: List<T>){
        cache.addAll(items)
    }

    fun clear(){
        cache.clear()
    }

    fun get(key: K, property: KProperty1<T, K>): T?{
        return cache.find{
            val id = property.get(it)
            (id == key)
        }
    }

    fun getAll() = cache.toList()
}

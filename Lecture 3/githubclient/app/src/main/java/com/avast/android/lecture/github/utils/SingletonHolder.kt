package com.avast.android.lecture.github.utils

/**
 * Generic singleton holder. Which allow to have [getInstance] method with parameter.
 *
 * Link to the medium article [here](https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e)
 */
open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
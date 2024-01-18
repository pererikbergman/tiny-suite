package com.rakangsoftware.tiny.navigation

class TinyBundle {
    private val data = mutableMapOf<String, Any>()

    // Helper function for error handling
    private inline fun <reified T> getValue(key: String, defaultValue: T): T {
        return try {
            data[key] as? T ?: defaultValue
        } catch (e: ClassCastException) {
            println("Error retrieving $key: ${e.message}")
            defaultValue
        }
    }

    // Integer
    fun putInt(key: String, value: Int) {
        data[key] = value
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return getValue(key, defaultValue)
    }

    // String
    fun putString(key: String, value: String) {
        data[key] = value
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return getValue(key, defaultValue)
    }

    // Boolean
    fun putBoolean(key: String, value: Boolean) {
        data[key] = value
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return getValue(key, defaultValue)
    }

    // Float
    fun putFloat(key: String, value: Float) {
        data[key] = value
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return getValue(key, defaultValue)
    }

    // Long
    fun putLong(key: String, value: Long) {
        data[key] = value
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return getValue(key, defaultValue)
    }

    // Double
    fun putDouble(key: String, value: Double) {
        data[key] = value
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return getValue(key, defaultValue)
    }

    // Short
    fun putShort(key: String, value: Short) {
        data[key] = value
    }

    fun getShort(key: String, defaultValue: Short = 0): Short {
        return getValue(key, defaultValue)
    }

    // Byte
    fun putByte(key: String, value: Byte) {
        data[key] = value
    }

    fun getByte(key: String, defaultValue: Byte = 0): Byte {
        return getValue(key, defaultValue)
    }

    // Char
    fun putChar(key: String, value: Char) {
        data[key] = value
    }

    fun getChar(key: String, defaultValue: Char = '\u0000'): Char {
        return getValue(key, defaultValue)
    }

    // Optional: Clear and Remove
    fun clear() {
        data.clear()
    }

    fun remove(key: String) {
        data.remove(key)
    }

    fun add(map: Map<String, Any>) {
        data.putAll(map)
    }
}

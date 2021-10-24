package com.demo.MADdog.repo

data class Response<T>(val message: T, val status: String, val code: Int)


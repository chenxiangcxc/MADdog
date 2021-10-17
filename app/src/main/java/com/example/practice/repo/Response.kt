package com.example.practice.repo

data class Response<T>(val message: T, val status: String, val code: Int)


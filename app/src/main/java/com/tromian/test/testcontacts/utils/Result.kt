package com.tromian.test.testcontacts.utils

sealed class Result<T>
class Loading<T>: Result<T>()
class Error<T>(val exception: Throwable) : Result<T>()
class Success<T>(val data: T) : Result<T>()

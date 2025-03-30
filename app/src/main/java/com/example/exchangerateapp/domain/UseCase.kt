package com.example.exchangerateapp.domain

interface UseCase<P, R> {

    suspend fun execute(param: P): R
}
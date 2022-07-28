package com.example.newsapp.network
//
//abstract class NetworkState {
//}
//
//class Loading : NetworkState(){
//
//}
//
//class Failure(val error: String?) : NetworkState(){
//
//}
//
//class Success<T>(val data: T) : NetworkState(){
//
//}
//




enum class NetworkState {
LOADING,SUCCESS,FAILURE
}
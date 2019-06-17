package com.example.memories.repository

data class Photo(var id: String, var title: String, var url: String, var time: String, var info: String){

    constructor():this("", "", "", "", "")
}
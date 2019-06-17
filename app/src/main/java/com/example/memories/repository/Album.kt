package com.example.memories.repository


data class Album(var id: String = "", var title: String = "", var time: String = "", var cover:String="", var messaage: String = "", var photos: Map<String, Photo> = HashMap())
package com.example.memories.repository

data class User(var id: String="", var name: String="", var url: String="", var albums: Map<String, Album> = HashMap(), var timeline: HashMap<String ,Photo> = java.util.HashMap())
package com.example.firebasetask.model

data class NoteDataClass(
    val title: String,
    val description: String,
    val date: String,
    val noteId: String
) {
    constructor() : this("", "", "", "")

}

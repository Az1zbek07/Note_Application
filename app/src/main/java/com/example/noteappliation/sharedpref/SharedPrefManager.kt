package com.example.noteappliation.sharedpref

import android.content.Context
import com.example.noteappliation.model.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPrefManager(context: Context) {
    private val pref = context.getSharedPreferences("My Pref", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveNotes(notes: ArrayList<Note>){
        val edit = pref.edit()
        edit.putString("note", gson.toJson(notes))
        edit.apply()
    }

    fun getNotes(): ArrayList<Note>?{
        val json = pref.getString("note", null)
        val type: Type = object : TypeToken<ArrayList<Note>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clear(){
        pref.edit().clear().apply()
    }
}
package com.example.pal.ui.homeScreens.settings.terms

data class TermsItem(val title:String,  val description:String ) {
    object ItemManager {
        val items : MutableList<TermsItem> = mutableListOf()
        fun add(item:TermsItem){
            items.add(item)
        }
        fun remove(index:Int){
            items.removeAt(index)
        }
    }
}
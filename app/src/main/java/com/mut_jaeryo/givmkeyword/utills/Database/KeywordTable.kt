package com.mut_jaeryo.givmkeyword.utills.Database

class KeywordTable{

    companion object{

        const val Table_Name = "MyKeyword"

        const val column1 = "Type"
        const val column2 = "keyword"
        const val _create = "create table $Table_Name( $column1 text, $column2 keyword);"

    }
}
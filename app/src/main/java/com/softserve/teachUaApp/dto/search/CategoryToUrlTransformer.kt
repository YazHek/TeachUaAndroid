package com.softserve.teachUaApp.dto.search

class CategoryToUrlTransformer {

    fun toUrlEncode(categoryName: String): String {

        return if (categoryName.contains(", ")) {
            categoryName.replace(", ", ";+")
        } else
            return categoryName


    }
}
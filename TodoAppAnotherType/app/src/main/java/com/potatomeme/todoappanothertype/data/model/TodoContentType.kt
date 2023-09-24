package com.potatomeme.todoappanothertype.data.model

enum class TodoContentType {
    ADD, EDIT, REMOVE;

    companion object {
        fun from(name: String?): TodoContentType? {
            return TodoContentType.values().find {
                it.name.uppercase() == name?.uppercase()
            }
        }
    }
}
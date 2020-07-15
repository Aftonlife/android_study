package com.zx.app.study_notes.kotlin.combat.entity

/**
 * author Afton
 * date 2020/6/26
 * {
"admin": false,
"chapterTops": [],
"collectIds": [],
"email": "",
"icon": "",
"id": 67599,
"nickname": "afton",
"password": "",
"publicName": "afton",
"token": "",
"type": 0,
"username": "afton"
}
 */
data class LoginResponseBean(val admin: Boolean,
                             val chapterTops: List<*>,
                             val collectIds: List<*>,
                             val email: String?,
                             val icon: String?,
                             val id: String?,
                             val nickname: String?,
                             val password: String?,
                             val publicName: String?,
                             val token: String?,
                             val type: Int,
                             val username: String?
)
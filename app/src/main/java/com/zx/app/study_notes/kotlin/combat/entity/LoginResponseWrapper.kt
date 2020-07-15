package com.zx.app.study_notes.kotlin.combat.entity

/**
 * author Afton
 * date 2020/6/26
 * 包装类
 */

/**
 * {
"data": {
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
},
"errorCode": 0,
"errorMsg": ""
}

{
"data": null,
"errorCode": -1,
"errorMsg": "账号密码不匹配！"
}
 */
data class LoginResponseWrapper<T>(val data:T,val errorCode:String,val errorMsg:String)

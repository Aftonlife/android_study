package com.zx.app.study_notes.kotlin.combat

import android.app.Dialog
import android.content.Context
import com.zx.app.study_notes.R

/**
 * author Afton
 * date 2020/6/27
 * 加载框
 */
object LoadingDialog {
    @JvmStatic
    fun staticShow(){}

    private var dialog: Dialog? = null

    fun show(context: Context) {
        cancel()

        dialog = Dialog(context)
        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        dialog?.show()

    }

    fun cancel(){
        dialog?.dismiss()
    }
}
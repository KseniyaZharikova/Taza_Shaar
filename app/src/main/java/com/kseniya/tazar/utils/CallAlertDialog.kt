package com.kseniya.tazar.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import com.kseniya.tazar.R

class CallAlertDialog() {

    companion object {

         fun getCall(context: Context, phones: String) {
            val builder = AlertDialog.Builder(context)
            val telList = getDataForDialogCall(phones)
            builder.setTitle(context.resources.getString(R.string.call_title))
                    .setItems(telList) { _, i ->
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel: " + telList[i])

                        context.startActivity(intent)
                    }
            val alert = builder.create()
            alert.show()
        }

       private fun getDataForDialogCall(phones: String): Array<String> {
            var tel = phones
            return if (tel.contains(",")) {
                tel = tel.replace(" ", "")
                tel.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            } else {
                arrayOf(tel.replace(".", ""))
            }
        }

    }


}
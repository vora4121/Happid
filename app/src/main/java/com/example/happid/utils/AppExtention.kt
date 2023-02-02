package com.example.happid.utils

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.alert(message: String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}


fun Fragment.showOptionAlert(title: String, message: String, pbName: String, listener: () -> Unit){
    val alertDialog = AlertDialog.Builder(requireContext())
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setCancelable(false)

    alertDialog.setNegativeButton("Cancel") { dialogInterface, _ ->
        dialogInterface.dismiss()
    }.setPositiveButton(pbName) { dialogInterface, _ ->
        listener.invoke()
        dialogInterface.dismiss()
    }
    alertDialog.show()
}
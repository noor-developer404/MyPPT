package com.developer404.myppt

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.developer404.myppt.databinding.ActivityMainBinding
import com.developer404.myppt.others.modal

class MainActivity : AppCompatActivity() {
    lateinit var pptx_list:ArrayList<modal>
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.main_frag)
//        val navController = Navigation.findNavController(this,R.id.main_frag)
        NavigationUI.setupWithNavController(binding.mainBtmbar,navController)


//        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
//        {
//            Toast.makeText(this,"access",Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show()
//        }



        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_permission)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.setData(Uri.fromParts("package", packageName, null))
        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Environment.isExternalStorageManager()) {
                dialog.cancel()

            } else {
                dialog.show()
            }
        }

        if (Environment.isExternalStorageManager()) {
            pptx_list = fetch_pdf()
//           Toast.makeText(this,"granted",Toast.LENGTH_SHORT).show()
        } else {
//            dialog.show()
            result.launch(intent)
        }
        for (pptxFile in pptx_list) {
            Log.d("MainActivity", "PPTX File URI: ${pptxFile.name}")
            // Do something with the PPTX file URI
        }

        val btn = dialog.findViewById<Button>(R.id.dialog_btn)
        btn.setOnClickListener {
            result.launch(intent)
        }


    }

    fun fetch_pdf(): ArrayList<modal> {
        var local_pdf_list: ArrayList<modal> = ArrayList()
        val mime = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
        val arg = arrayOf("application/vnd.openxmlformats-officedocument.presentationml.presentation")
        val order = MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        val proj =
            arrayOf(MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DISPLAY_NAME)
        var cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            proj,
            mime,
            arg,
            order
        )
//        MediaStore.Files.getContentUri("external")

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val obj = modal(cursor.getString(0), cursor.getString(1))
                local_pdf_list.add(obj)
//                Toast.makeText(this,""+cursor.getString(1),Toast.LENGTH_SHORT).show()
//                print(cursor.getString(1))
            }

        } else {
            Toast.makeText(this, "null cursor", Toast.LENGTH_SHORT).show()
        }
        return local_pdf_list
    }

}
package com.developer404.myppt.fragments

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.developer404.myppt.R
import org.apache.poi.xslf.usermodel.SlideLayout
import org.apache.poi.xslf.usermodel.XMLSlideShow
import java.io.File
import java.io.FileOutputStream

class CreatePptFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_create_ppt, container, false)

        val createBtn = v.findViewById<Button>(R.id.create_btn)
        createBtn.setOnClickListener {
            if (Environment.isExternalStorageManager()) {
                generatePPT(v.findViewById<EditText>(R.id.create_fileName).text.toString(),v.findViewById<EditText>(
                    R.id.create_content
                ).text.toString())

            } else {
                Toast.makeText(context,"not not granted",Toast.LENGTH_SHORT).show()

            }
         }

        return v
    }

    fun generatePPT(fileName:String, content:String){
        val ppt = XMLSlideShow()
        val slideMaster = ppt.slideMasters[0]
        val titleLayout = slideMaster.getLayout(SlideLayout.TITLE)
        val slide = ppt.createSlide(titleLayout)
        val title = slide.getPlaceholder(0)
        title.text=content
        val filePath:File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

        val fileOut= FileOutputStream(File(filePath,fileName+".pptx"))
        ppt.write(fileOut)
        fileOut.close()
        Toast.makeText(context,"PPT Generated",Toast.LENGTH_SHORT).show()

    }

}
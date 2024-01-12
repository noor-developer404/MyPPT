package com.developer404.myppt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xslf.usermodel.XSLFSlide
import org.apache.poi.xslf.usermodel.XSLFTextShape
import java.io.FileInputStream

class PptViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ppt_viewer)

        val path = intent.getStringExtra("path")
        Toast.makeText(this,path,Toast.LENGTH_SHORT).show()
        Log.e("path", "onCreate: "+ path )

        val fileInputStream = FileInputStream(path)
        val ppt = XMLSlideShow(fileInputStream)

        for (slide in ppt.slides) {
            val text = extractTextFromSlide(slide)
            findViewById<TextView>(R.id.ppt_viewer_title).text=text
            fileInputStream.close()
        }
    }

    private fun extractTextFromSlide(slide: XSLFSlide?): String {
        val titleShape = slide?.getPlaceholder(0) as? XSLFTextShape
        return titleShape?.text ?: ""
    }
}


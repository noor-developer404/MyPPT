package com.developer404.myppt.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer404.myppt.others.ListAdapter
import com.developer404.myppt.PptViewer
import com.developer404.myppt.R
import com.developer404.myppt.others.RVitem
import com.developer404.myppt.others.modal

class ListFragment : Fragment(), RVitem {
    lateinit var pptx_list:ArrayList<modal>
    lateinit var v:View

    override fun onResume() {
        val adapter = context?.let { ListAdapter(pptx_list, it,this) }
        v.findViewById<RecyclerView>(R.id.list_rv).adapter=adapter
        super.onResume()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_list, container, false)
        if (Environment.isExternalStorageManager()) {
            pptx_list = fetch_pdf()
//            Toast.makeText(context,"granted",Toast.LENGTH_SHORT).show()
        } else {
//            dialog.show()
        }

        v.findViewById<RecyclerView>(R.id.list_rv).layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { ListAdapter(pptx_list, it,this) }
        v.findViewById<RecyclerView>(R.id.list_rv).adapter=adapter
        return v
    }


    fun fetch_pdf(): ArrayList<modal> {
        var local_pdf_list: ArrayList<modal> = ArrayList()
        val mime = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
        val arg = arrayOf("application/vnd.openxmlformats-officedocument.presentationml.presentation")
        val order = MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        val proj =
            arrayOf(MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DISPLAY_NAME)
        var cursor = context?.contentResolver?.query(
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
            Toast.makeText(context, "null cursor", Toast.LENGTH_SHORT).show()
        }
        return local_pdf_list
    }

    override fun onclick(position: Int) {
//        Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
//        findNavController().navigate(R.id.pptxFragment)
        val intent = Intent(context, PptViewer::class.java)
        intent.putExtra("path",pptx_list[position].path)
        startActivity(intent)
    }
}
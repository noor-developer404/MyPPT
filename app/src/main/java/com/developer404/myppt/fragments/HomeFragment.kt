package com.developer404.myppt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.developer404.myppt.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        val add_btn = view?.findViewById<ImageButton>(R.id.home_add_btn)
        add_btn?.setOnClickListener {
            Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.createPptFragment)
        }

        return view

    }

}
package com.example.firebasetask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasetask.adapter.ChatAdapter
import com.example.firebasetask.databinding.FragmentChatBinding


class ChatFragment : Fragment() {
    private lateinit var binding:FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding=FragmentChatBinding .inflate(inflater, container, false)

        binding.recyclerChat.adapter=ChatAdapter(requireContext())
        binding.recyclerChat.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }


}
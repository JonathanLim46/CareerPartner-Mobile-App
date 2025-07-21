package com.fufufafaAI.careerpartner.main.home.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fufufafaAI.careerpartner.databinding.FragmentChatBotBinding
import com.fufufafaAI.careerpartner.main.home.adapter.ChatAdapter
import com.fufufafaAI.careerpartner.main.home.data.ChatMessage
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import kotlinx.coroutines.launch

class ChatBotFragment : Fragment() {

    private var _binding: FragmentChatBotBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChatAdapter
    private lateinit var messages: MutableList<ChatMessage>
    private lateinit var generativeModel: GenerativeModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messages = mutableListOf()
        adapter = ChatAdapter(messages)
        binding.rvChatBot.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChatBot.adapter = adapter

        generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSend.setOnClickListener {
            val input = binding.etMessage.text.toString().trim()
            if (input.isNotEmpty()){
                binding.etMessage.text.clear()
                addMessage(ChatMessage(input, true))

                val history = content {
                    text("You are a helpful career development assistant. You give brief, practical advice based on the user's needs. Keep your answers clear and concise.")
                    for (message in messages){
                        if (message.isUser){
                            text("User: ${message.message}")
                        } else {
                            text("Assistant: ${message.message}")
                        }
                    }
                    text("User: $input")
                }

                lifecycleScope.launch {
                    try {
                        val result = generativeModel.generateContent(history)
                        val reply = result.text ?: "[No response]"
                        addMessage(ChatMessage(reply, false))
                    } catch (e: Exception) {
                        addMessage(ChatMessage("[Error: ${e.localizedMessage}]", false))
                    }
                }
            }
        }
    }

    private fun addMessage(message: ChatMessage) {
        messages.add(message)
        adapter.notifyItemInserted(messages.size - 1)
        binding.rvChatBot.scrollToPosition(messages.size - 1)
    }
}
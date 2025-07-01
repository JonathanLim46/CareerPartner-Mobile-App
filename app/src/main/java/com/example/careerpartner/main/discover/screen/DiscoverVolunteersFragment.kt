package com.example.careerpartner.main.discover.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.databinding.FragmentDiscoverVolunteersBinding
import com.example.careerpartner.main.discover.adapter.DiscoverDataAdapter
import com.example.careerpartner.main.discover.data.DiscoverData

class DiscoverVolunteersFragment : Fragment() {

    private var _binding: FragmentDiscoverVolunteersBinding? = null
    private val binding: FragmentDiscoverVolunteersBinding get() = _binding!!

    private lateinit var adapter: DiscoverDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var discoverData: List<DiscoverData>
    private lateinit var rawData: List<List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rawData = listOf(
            listOf("Judul 1 Volunteer", "Deskripsi A", getString(R.string.lorem)),
            listOf("Judul 2 Volunteer", "Deskripsi B", getString(R.string.lorem)),
            listOf("Judul 3 Volunteer", "Deskripsi C", getString(R.string.lorem)),
            listOf("Judul 4 Volunteer", "Deskripsi D", getString(R.string.lorem)),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverVolunteersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvDiscoverVolunteers
        getDataRv()
    }

    private fun getDataRv(){
        discoverData = rawData.map{
            DiscoverData(it[0], it[1], R.drawable.dummyimg, it[2])
        }
        adapter = DiscoverDataAdapter(discoverData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}
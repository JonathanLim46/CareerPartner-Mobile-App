package com.example.careerpartner.main.discover.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.VolunteerViewModel
import com.example.careerpartner.databinding.FragmentDiscoverVolunteersBinding
import com.example.careerpartner.main.discover.adapter.DiscoverDataAdapter
import com.example.careerpartner.main.discover.data.DiscoverData

class DiscoverVolunteersFragment : Fragment() {

    private var _binding: FragmentDiscoverVolunteersBinding? = null
    private val binding: FragmentDiscoverVolunteersBinding get() = _binding!!

    private lateinit var adapter: DiscoverDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var discoverData: List<DiscoverData>

    private val viewModel: VolunteerViewModel by activityViewModels<VolunteerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        viewModel.getVolunteerDataResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Success -> {
                    discoverData = it.data?.data?.map {
                        DiscoverData(
                            title = it.title,
                            subTitle = it.description,
                            image = it.imageCover,
                            content = it.description,
                            status = it.status
                        )
                    }?.filter { it.status == "completed" } ?: listOf()
                    getDataRv()
                }
                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    discoverData = listOf()
                    getDataRv()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    discoverData = listOf()
                    getDataRv()
                }
            }
        }

        viewModel.getVolunteerData(requireActivity())
    }

    private fun getDataRv(){
        adapter = DiscoverDataAdapter(requireContext(), discoverData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}
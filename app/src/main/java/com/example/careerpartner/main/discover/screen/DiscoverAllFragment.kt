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
import com.example.careerpartner.data.viewmodel.InternshipViewModel
import com.example.careerpartner.data.viewmodel.VolunteerViewModel
import com.example.careerpartner.databinding.FragmentDiscoverAllBinding
import com.example.careerpartner.main.discover.adapter.DiscoverDataAdapter
import com.example.careerpartner.main.discover.data.DiscoverData

class DiscoverAllFragment : Fragment() {

    private var _binding: FragmentDiscoverAllBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DiscoverDataAdapter
    private lateinit var recyclerView: RecyclerView
    private var discoverData: List<DiscoverData> = emptyList()
    private var internshipsData: List<DiscoverData> = emptyList()
    private var volunteersData: List<DiscoverData> = emptyList()

    private val viewModelInternship: InternshipViewModel by activityViewModels<InternshipViewModel>()
    private val viewModelVolunteer: VolunteerViewModel by activityViewModels<VolunteerViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverAllBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvDiscoverAll

        setupInternshipsVolunteerData()

        viewModelInternship.getInternshipsData(requireActivity())
        viewModelVolunteer.getVolunteerData(requireActivity())
    }

    private fun setupInternshipsVolunteerData(){
        viewModelInternship.getInternshipsDataResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    internshipsData = it.data?.data?.map {
                        DiscoverData(
                            title = it.title,
                            subTitle = it.location,
                            image = it.imageCover,
                            content = "Status: ${it.status}",
                            status = it.status
                        )
                    }?.filter { it.status == "open" } ?: listOf()
                    updateRv()
                }
                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    discoverData = listOf()
                    getDataRv()
                }
                else -> {
                    discoverData = listOf()
                    getDataRv()
                }
            }
        }

        viewModelVolunteer.getVolunteerDataResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Success -> {
                    volunteersData = it.data?.data?.map {
                        DiscoverData(
                            title = it.title,
                            subTitle = it.description,
                            image = it.imageCover,
                            content = it.description,
                            status = it.status
                        )
                    }?.filter { it.status == "open" } ?: listOf()
                    updateRv()
                }
                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    discoverData = listOf()
                    getDataRv()
                }
                else -> {
                    discoverData = listOf()
                    getDataRv()
                }
            }
        }

        viewModelInternship.getInternshipsData(requireActivity())
        viewModelVolunteer.getVolunteerData(requireActivity())
    }

    private fun updateRv(){
        discoverData = (internshipsData + volunteersData).distinct()
        getDataRv()
    }

    private fun getDataRv(){
        adapter = DiscoverDataAdapter(requireContext(),discoverData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}
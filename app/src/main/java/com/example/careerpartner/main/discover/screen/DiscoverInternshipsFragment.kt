package com.example.careerpartner.main.discover.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.InternshipViewModel
import com.example.careerpartner.databinding.FragmentDiscoverInternshipsBinding
import com.example.careerpartner.main.discover.adapter.DiscoverDataAdapter
import com.example.careerpartner.main.discover.data.DiscoverData

class DiscoverInternshipsFragment : Fragment() {

    private var _binding: FragmentDiscoverInternshipsBinding? = null
    private val binding: FragmentDiscoverInternshipsBinding get() = _binding!!

    private lateinit var adapter: DiscoverDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var discoverData: List<DiscoverData>

    private val viewModel: InternshipViewModel by activityViewModels<InternshipViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverInternshipsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvDiscoverInternships


        viewModel.getInternshipsDataResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.rvDiscoverInternships.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()
                }
                is BaseResponse.Success -> {
                    discoverData = it.data?.data?.map {
                        DiscoverData(
                            id = it.id,
                            title = it.title,
                            subTitle = it.location,
                            image = it.imageCover,
                            content = "Status: ${it.status}",
                            status = it.status,
                            type = "internship"
                        )
                    }?.filter { it.status == "open" } ?: listOf()
                    getDataRv()
                    binding.rvDiscoverInternships.visibility = View.VISIBLE
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
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

        viewModel.getInternshipsData(requireActivity())
    }

    private fun getDataRv(){
        adapter = DiscoverDataAdapter(requireActivity(),discoverData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.onItemClick = {
            val bundle = Bundle().apply {
                putString("detail", it.type)
                putInt("detailID", it.id)
            }
            findNavController().navigate(R.id.action_discoverFragment_to_discoverDetailFragment, bundle)
        }
    }
}
package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentProfileBinding
import com.example.careerpartner.main.profile.adapter.ProfileTabAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding : FragmentProfileBinding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ProfileTabAdapter

    private val viewmodel: UserViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = binding.viewPagerProfile
        adapter = ProfileTabAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        viewmodel.userResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.contentUserLayout.visibility = View.INVISIBLE
                    binding.shimmerUserLayout.startShimmer()
                }
                is BaseResponse.Success -> {
                    binding.contentUserLayout.visibility = View.VISIBLE
                    binding.shimmerUserLayout.stopShimmer()
                    binding.shimmerUserLayout.visibility = View.GONE
                    binding.tvProfileName.text = it.data?.data?.talent?.full_name
                    binding.tvProfileLastDegree.text = it.data?.data?.talent?.talent?.currentEducation
                    Glide.with(requireContext())
                        .load(it.data?.data?.talent?.profile_picture)
                        .placeholder(R.drawable.img_default_profile)
                        .error(R.drawable.img_default_profile)
                        .into(binding.ivProfilePicture)
                }
                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg.toString(), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Something went wrong, check your internet connection !", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }
        setupTabLayout()
    }

    private fun setupTabLayout(){
        val tabTitles = listOf("My Career", "Educational", "Achievements", "Projects")

        TabLayoutMediator(binding.tabLayoutProfile, viewPager2) { tab, position ->
            val tabView = LayoutInflater.from(binding.root.context).inflate(R.layout.item_custom_tab_item, null)
            tabView.findViewById<TextView>(R.id.tabText).text = tabTitles[position]
            tab.customView = tabView
        }.attach()
    }
}
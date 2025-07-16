package com.example.careerpartner.main.discover.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.InternshipViewModel
import com.example.careerpartner.data.viewmodel.VolunteerViewModel
import com.example.careerpartner.databinding.FragmentDiscoverDetailBinding

class DiscoverDetailFragment : Fragment() {

    private var _binding: FragmentDiscoverDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModelIntern: InternshipViewModel by activityViewModels<InternshipViewModel>()
    private val viewModelVolunteer: VolunteerViewModel by activityViewModels<VolunteerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detail = arguments?.getString("detail")
        val detailID = arguments?.getInt("detailID")

        if (detail == "internship") {
            viewModelIntern.getInternshipDetail(requireActivity(), detailID!!)

            viewModelIntern.getInternshipsDetailResult.observe(viewLifecycleOwner) {
                when (it) {
                    is BaseResponse.Success -> {
                        val url = it.data?.data?.company?.website.toString()
                        binding.tvDetailTitle.text = it.data?.data?.title.toString()
                        binding.tvDetailOneDesc.text = it.data?.data?.company?.description.toString()
                        binding.tvDetailTwoDesc.text = it.data?.data?.responsibilities.toString()
                        binding.tvDetailThreeDesc.text = it.data?.data?.requirements.toString()
                        binding.tvDetailFourDesc.text = it.data?.data?.offer.toString()
                        binding.tvStatus.text = "Status: ${it.data?.data?.status.toString()}"
                        binding.tvLocation.text = "Location: ${it.data?.data?.location.toString()}"
                        binding.tvEmailContact.text = "Email Contact: ${it.data?.data?.company?.contactEmail.toString()}"
                        binding.tvPhoneContact.text = "Phone Contact: ${it.data?.data?.company?.contactPhone.toString()}"
                        binding.btnMoreDetail.setOnClickListener {
                            navigateToBrowser(url)
                        }
                    }
                    is BaseResponse.Error -> {
                        Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            uiVolunteer()

            viewModelVolunteer.getVolunteerDetail(requireActivity(), detailID!!)

            viewModelVolunteer.getVolunteerDetailResult.observe(viewLifecycleOwner) {
                when (it) {
                    is BaseResponse.Success -> {
                        binding.tvDetailTitle.text = it.data?.data?.title.toString()
                        binding.tvDetailOneDesc.text = it.data?.data?.description.toString()
                        binding.tvDetailTwoDesc.text = it.data?.data?.detailActivity.toString()
                        binding.tvStatus.text = "Status: ${it.data?.data?.status.toString()}"
                        binding.tvLocation.text = "Location: ${it.data?.data?.location.toString()}"
                        binding.tvEmailContact.text = "Email Contact: ${it.data?.data?.organization?.contactEmail.toString()}"
                        binding.tvPhoneContact.text = "Phone Contact: ${it.data?.data?.organization?.contactPhone.toString()}"
                    }
                    is BaseResponse.Error -> {
                        Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navigateToBrowser(url: String){
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private fun uiVolunteer(){
        binding.tvDetailOne.text = "About Volunteer"
        binding.tvDetailTwo.text = "Detail Activity"
        binding.tvDetailThree.visibility = View.GONE
        binding.tvDetailThreeDesc.visibility = View.GONE
        binding.tvDetailFour.visibility = View.GONE
        binding.tvDetailFourDesc.visibility = View.GONE
    }
}
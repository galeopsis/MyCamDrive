package com.galeopsis.mycamdrive.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.galeopsis.mycamdrive.R
import com.galeopsis.mycamdrive.databinding.FragmentProfileBinding
import com.galeopsis.mycamdrive.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class Profile : Fragment() {
    companion object {
        fun newInstance() = Profile()
    }

    private val mainViewModel by viewModel<MainViewModel>()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.data.observe(viewLifecycleOwner, {
            it?.forEach { cameraData ->
                with(binding) {
                    profile.text = cameraData.profile.name
                    userEmail.text = cameraData.profile.email

                }
            }
        })
    }

    private fun goToSearchFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, CameraSearchFragment.newInstance())
            ?.commitNow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
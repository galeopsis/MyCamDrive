package com.galeopsis.mycamdrive.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.galeopsis.mycamdrive.R
import com.galeopsis.mycamdrive.databinding.MainFragmentBinding
import com.galeopsis.mycamdrive.model.data.Camera
import com.galeopsis.mycamdrive.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CameraDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CameraDetailsFragment()
    }

    private val mainViewModel by viewModel<MainViewModel>()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            goToSearchFragment()
        }

        mainViewModel.data.observe(viewLifecycleOwner, {

            if (it != null) {
                setCameraDetails(it)
            }
        })
    }

    private fun setCameraDetails(it: List<Camera>?) {

        val index = arguments?.getInt("POS")
        if (index != null) {
            val data = it?.get(index)
            with(binding) {

                if (data != null) {
                    cameraModel.text = data.cameraModel
                }
            }
        }
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

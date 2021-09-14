package com.galeopsis.mycamdrive.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.galeopsis.mycamdrive.R
import com.galeopsis.mycamdrive.databinding.FragmentAutorizationBinding
import com.galeopsis.mycamdrive.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : Fragment() {

    companion object {
        fun newInstance() = AuthorizationFragment()
    }

    private lateinit var communicator: Communicator
    private val mainViewModel by viewModel<MainViewModel>()
    private var _binding: FragmentAutorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            enterBtn.setOnClickListener {
                val login = loginEt.text.toString()
                val password = passwordEt.text.toString()
                goToSearchFragment()
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

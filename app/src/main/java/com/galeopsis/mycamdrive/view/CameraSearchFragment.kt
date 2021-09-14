package com.galeopsis.mycamdrive.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galeopsis.mycamdrive.R
import com.galeopsis.mycamdrive.databinding.CameraSearchFragmentBinding
import com.galeopsis.mycamdrive.model.data.Camera
import com.galeopsis.mycamdrive.utils.LoadingState
import com.galeopsis.mycamdrive.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CameraSearchFragment : Fragment() {

    companion object {
        fun newInstance() = CameraSearchFragment()
    }

    private lateinit var communicator: Communicator
    private val mainViewModel by viewModel<MainViewModel>()
    private var _binding: CameraSearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CameraSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.MyRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this.requireActivity(),
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        communicator = activity as Communicator
                        communicator.passData(position)
                        goToDetailsFragment()
                    }
                })
        )
        setHasOptionsMenu(true)

        validate(recyclerView)
    }

    private fun validate(recyclerView: RecyclerView) {
        context?.let { isOnline(it) }
        if (context?.let { isOnline(it) } == true) {
//            mainViewModel.fetchData()

            val login = "test_task"
            val password = "test_task"

            mainViewModel.login(login, password)


            val cameras = ArrayList<Camera>()
            cameras.clear()
            initCameras(cameras, recyclerView)
        } else {
            Toast.makeText(context, getString(R.string.check_connection), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                goToProfile()
                Log.d("API123", "done")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToProfile() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, Profile.newInstance())
            ?.commitNow()
    }

    private fun initCameras(
        cams: ArrayList<Camera>,
        recyclerView: RecyclerView
    ) {
        mainViewModel.data.observe(viewLifecycleOwner, {
            it?.forEach { cameraData ->

                val adapter = RecycleViewAdapter(cams)
                recyclerView.adapter = adapter
                cams.add(cameraData)
                adapter.notifyDataSetChanged()
            }

        })
        mainViewModel.loadingState.observe(viewLifecycleOwner,
            {
                when (it.status) {
                    LoadingState.Status.FAILED ->
                        Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()

                    LoadingState.Status.RUNNING ->
                        with(binding) {
                            loadingLayout.visibility = View.VISIBLE
                        }
                    LoadingState.Status.SUCCESS ->
                        with(binding) {
                            loadingLayout.visibility = View.GONE
                        }
                }
            })
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
//                    Toast.makeText(context, "mobile", Toast.LENGTH_SHORT).show()
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
//                    Toast.makeText(context, "wifi", Toast.LENGTH_SHORT).show()
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
//                    Toast.makeText(context, "ethernet", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        }
        Toast.makeText(context, "Проверьте подключение к интернет!", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun goToDetailsFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, CameraDetailsFragment.newInstance())
            ?.commitNow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
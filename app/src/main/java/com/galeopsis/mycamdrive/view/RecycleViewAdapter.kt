package com.galeopsis.mycamdrive.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galeopsis.mycamdrive.R
import com.galeopsis.mycamdrive.model.data.Camera

class RecycleViewAdapter(
    private val camList: ArrayList<Camera>
) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cameras: Camera = camList[position]

        holder.searchFragmentCameraName.text = cameras.cameraName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return camList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchFragmentCameraName =
            itemView.findViewById(R.id.searchFragmentCameraName) as TextView
    }
}

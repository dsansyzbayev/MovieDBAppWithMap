package com.example.moviedbappwithmap.presentation.map.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedbappwithmap.R
import com.example.moviedbappwithmap.base.BaseViewHolder
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema
import com.example.moviedbappwithmap.presentation.movie.list.MovieAdapter

class CinemaListAdapter (
    private val context: Context?,
    private val itemClickListener: ItemClickListener
): RecyclerView.Adapter<CinemaListAdapter.CinemaViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cinemas = emptyList<Cinema>()

    inner class CinemaViewHolder(itemView: View): BaseViewHolder(itemView) {
        val cinemaName: TextView = itemView.findViewById(R.id.cinema_name)
        val cinemaPhone: TextView = itemView.findViewById(R.id.phoneNumber)
        val cinemaAddress: TextView = itemView.findViewById(R.id.address)
        val cinemaPic: ImageView = itemView.findViewById(R.id.cinema_pic)

        fun setItemClick(item: Cinema) {
            itemView.setOnClickListener{
                itemClickListener.onItemClick(item)
            }
        }

        override fun clear() { }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val itemView = inflater.inflate(R.layout.row_item_cinema, parent, false)
        return CinemaViewHolder(itemView)
    }

    override fun getItemCount(): Int =
        cinemas.size

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        val current = cinemas[position]
        holder.cinemaName.text = current.name
        holder.cinemaPhone.text = current.phoneNumber
        holder.cinemaAddress.text = current.address
        holder.setItemClick(current)


        val imageUrl = current.poster
        Log.d("Cinema: imageUrl", imageUrl)

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.cinemaPic)
    }

    internal fun setCinemas(cinemas: List<Cinema>) {
        this.cinemas = cinemas
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(item: Cinema)
    }
}
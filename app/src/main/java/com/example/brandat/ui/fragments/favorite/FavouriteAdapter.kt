package com.example.brandat.ui.fragments.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.FavouriteItemBinding
import com.example.brandat.models.Favourite
import com.example.brandat.utils.FavouriteDiffUtil

class FavouriteAdapter (var onClickedListener: OnclickListener) : RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>() {

        private var products = emptyList<Favourite>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            return ProductViewHolder(
                com.example.brandat.databinding.FavouriteItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val currentProduct = products[position]
          holder.binding.ivProduct.setImageBitmap(currentProduct.productImage)
          holder.binding.tvProductName.text = currentProduct.productName
            holder.binding.tvProductPrice.text = currentProduct.productPrice
            holder.itemView.setOnClickListener {

                onClickedListener.onItemClicked(currentProduct.productId)

            }
            holder.binding.ivFavorite.setOnClickListener {
                onClickedListener.onRemoveClicked(currentProduct)

            }
        }

        override fun getItemCount():Int {
            return products.size
        }

        fun setData(newData: List<Favourite>) {
            val favouriteDiffUtil= FavouriteDiffUtil(products,newData)
            val favDiffUtilResult= DiffUtil.calculateDiff(favouriteDiffUtil)
            products = ArrayList(newData)
            favDiffUtilResult.dispatchUpdatesTo(this)

        }

        //============================================================
        class ProductViewHolder(val binding: FavouriteItemBinding) :
            RecyclerView.ViewHolder(binding.root)


    }

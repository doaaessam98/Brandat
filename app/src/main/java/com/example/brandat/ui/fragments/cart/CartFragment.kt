package com.example.brandat.ui.fragments.cart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.AlertDialogBinding
import com.example.brandat.databinding.FragmentCartBinding
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.utils.Constants.Companion.count
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class CartFragment : Fragment(), CartOnClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartRvAdapter
    private lateinit var builder: AlertDialog.Builder
    private lateinit var bindingDialog: AlertDialogBinding
    private lateinit var dialog: AlertDialog
    private lateinit var bageCountI: IBadgeCount

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(context), container, false)

        bindingDialog =
            AlertDialogBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Paper.init(requireContext())
        setUpRecyclerView()
        cartViewModel.getAllCartProduct()
        cartViewModel.getAllPrice()
        bageCountI = requireActivity() as MainActivity

        binding.buyButn.setOnClickListener {
            if (Paper.book().read<String>("email") == null) {
                //  showDialog()
            } else {
                startActivity(Intent(requireContext(), OrderStatus::class.java))
            }
        }

        cartViewModel.cartProduct.observe(viewLifecycleOwner) {
            Log.e("Cart", "============${it.size} ")
            cartAdapter.setData(it)
            checkEmptyList(it)
            binding.tvConut.text = "(${it.size} item)"
            bageCountI.updateBadgeCount(it.size)
            cartAdapter.notifyDataSetChanged()
            count = it.size
            Paper.book().write("countFromCart", count)

        }
        cartViewModel.allPrice.observe(viewLifecycleOwner) {
            binding.tvTprice.text = "$it $"
        }
    }
//=====================================================
    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Login Now") { _, _ ->
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        builder.setNegativeButton("cancel") { _, _ ->
        }
        builder.setTitle("please register or login to add item in cart")
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    private fun setUpRecyclerView() {
        cartAdapter = CartRvAdapter(requireContext(), requireActivity(), this)
        binding.rvCart.apply {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            //layoutManager.stackFromEnd = true
            // layoutManager.reverseLayout = true
            setLayoutManager(layoutManager)

            adapter = cartAdapter
        }
    }

    override fun onClicked(order: Cart) {
       // showDialoge(order)
        cartViewModel.removeProductFromCart(order)
        cartViewModel.getAllCartProduct()
          requireActivity().recreate()
    }

    override fun onPluseMinusClicked(count: Int, pId: Long, price: String) {
        val priceChange = price.toDouble()
        val _price = (count * priceChange)
        val currentOrder = Cart(pQuantity = count, pId = pId, tPrice = _price)
        cartViewModel.updateOrder(currentOrder)
        cartViewModel.getAllCartProduct()
        cartViewModel.getAllPrice()
        cartAdapter.notifyDataSetChanged()
    }

    private fun checkEmptyList(list: List<Cart>) {
        if (list.isEmpty()) {
            binding.ivPlaceholder.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
        } else {
            binding.ivPlaceholder.visibility = View.GONE
            binding.rvCart.visibility = View.VISIBLE

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // binding = null
        cartAdapter.clearContextualActionMode()
    }

}
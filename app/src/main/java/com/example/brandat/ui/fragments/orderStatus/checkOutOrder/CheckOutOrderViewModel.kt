package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.orderModel.*
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.Constants
import com.example.brandat.utils.ResponseResult
import com.example.brandat.utils.toLineItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class CheckOutOrderViewModel @Inject constructor(private val repository: IProductsRepository) :ViewModel() {
    var orderProduct :List<Cart> = emptyList()
    val loading = MutableLiveData<Boolean>()
    private val _createOrderResponse= MutableLiveData<ResponseResult<OrderResponse>>()
    val createOrderResponse: LiveData<ResponseResult<OrderResponse>?> = _createOrderResponse
    var selectedPaymentMethods:String = "Paypal"

    //fun getTotalPrice() = (discount ?: 0.0) + orderProduct.getPrice() + deliveryCoast

    fun createOrder() {
        Log.e(TAG, "createOrder: ${Constants.user}")
        val order = CustomerOrder(
              billing_address= addd(),
              shipping_address = addd(),
              email = "doaaessam@gmail.com" ,
              line_items=orderProduct.toLineItem(),
               gateway= selectedPaymentMethods ,
//               customer = Constants.user

        )

        viewModelScope.launch (Dispatchers.IO){
            var res = repository.createOrder(OrderModel(order = order))
                    _createOrderResponse.postValue(res)
                }

    }


    init {
        viewModelScope.launch {
            var result =  repository.getAllCartProducts()
            orderProduct = result

        }



    }

    private fun  add ():com.example.brandat.models.orderModel.BillingAddress{
        return com.example.brandat.models.orderModel.BillingAddress("Ap #417-5876 Mus. St.","","Neuruppin","" ,
            "Pakistan","PK","Steven","Ewing"
            ,0.0,0.0,
            "Steven Ewing","+92515761234","Brandenburg", "","82623"
        )

    }
    private fun  addd (): ShippingAddress {
        return ShippingAddress("Ap #417-5876 Mus. St.","","Neuruppin","" ,
            "Pakistan","PK","Steven","Ewing"
            ,0.0,0.0,
            "Steven Ewing","+92515761234","Brandenburg", "","82623"
        )

    }
    private  fun ss(): ArrayList<LineItem> {
        var list = ArrayList<LineItem>()
        list.add(LineItem(42845086056706,1))
//        val order = Order()
//        order.line_items = list
//        order.email = "doaaessam2021@gmail.com"
//        order.fulfillment_status = "fulfilled"


//        var orderList = listOf<Order>(
//            order
//        )
        return list
    }

}
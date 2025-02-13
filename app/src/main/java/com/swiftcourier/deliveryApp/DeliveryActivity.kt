package com.swiftcourier.deliveryApp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class DeliveryActivity : ComponentActivity() {
    private lateinit var orderContainer: LinearLayout
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delivery)

        orderContainer= findViewById(R.id.orderContainer)
        sharedPreferences = getSharedPreferences("DeliveryPrefs", Context.MODE_PRIVATE)

        displayOrders()

    }

    private fun displayOrders() {
        val ordersList = getOrdersList()

        orderContainer.removeAllViews()  // Clear previous views

        for (order in ordersList) {
            val orderView = TextView(this)
            orderView.text = "OrderID: ${order[0]}\nAddress: ${order[1]}\nStatus: ${order[2]}\n"
            orderView.textSize = 20f
            orderView.setOnClickListener {
                openOrderDetails(order)
            }
            orderContainer.addView(orderView)
        }
    }

    private fun openOrderDetails(delivery: Array<String>) {
        val intent = Intent(this, DeliveryDetailsActivity::class.java)
        intent.putExtra("order_id", delivery[0])
        intent.putExtra("delivery_address", delivery[1])
        intent.putExtra("status", delivery[2])
        startActivity(intent)
    }


    private fun getOrdersList(): ArrayList<Array<String>> {
        val storedData = sharedPreferences.getString("orders", "") ?: ""

        val ordersList = ArrayList<Array<String>>()

        if (storedData.isNotEmpty()) {
            val orderEntries = storedData.split("|")

            for (entry in orderEntries) {
                val orderData = entry.split(",").toTypedArray()
                if (orderData.size == 3) {
                    ordersList.add(orderData)
                }
            }
        }

        return ordersList
    }
}

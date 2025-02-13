package com.swiftcourier.deliveryApp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var etOrderId: EditText = findViewById(R.id.etOrderId)
        var etDeliveryAddress: EditText = findViewById(R.id.etDeliveryAddress)
        var etStatus: EditText = findViewById(R.id.etStatus)
        var btnSaveUpdate: Button = findViewById(R.id.btnSaveUpdate)
        var btnViewOrders: Button = findViewById(R.id.btnViewOrders)

        sharedPreferences = getSharedPreferences("DeliveryPrefs", MODE_PRIVATE)

        btnSaveUpdate.setOnClickListener{
            val orderId = etOrderId.text.toString().trim()
            val deliveryAddress = etDeliveryAddress.text.toString().trim()
            val status = etStatus.text.toString().trim()

            if (orderId.isEmpty() || deliveryAddress.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveDelivery(orderId, deliveryAddress, status)
            etOrderId.text.clear()
            etDeliveryAddress.text.clear()
            etStatus.text.clear()
        }

        btnViewOrders.setOnClickListener{
            var intent = Intent(this, DeliveryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveDelivery(id: String, address: String, status: String) {
        val ordersList = getOrdersList()

        // Convert ArrayList to MutableList to allow updates
        val updatedList = ArrayList<Array<String>>()

        var orderUpdated = false

        // Check if order ID already exists
        for (order in ordersList) {
            if (order[0] == id) {
                updatedList.add(arrayOf(id, address, status)) // Update existing order
                orderUpdated = true
            } else {
                updatedList.add(order)
            }
        }

        // If order ID was not found, add a new entry
        if (!orderUpdated) {
            updatedList.add(arrayOf(id, address, status))
        }

        // Convert list to string and save in SharedPreferences
        val stringOrders = updatedList.joinToString("|") { it.joinToString(",") }
        sharedPreferences.edit().putString("orders", stringOrders).apply()
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
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
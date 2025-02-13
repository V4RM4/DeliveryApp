package com.swiftcourier.deliveryApp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.swiftcourier.deliveryApp.ui.theme.DeliveryAppTheme

class DeliveryDetailsActivity : ComponentActivity() {
    private lateinit var orderIdText: TextView
    private lateinit var orderAddressText: TextView
    private lateinit var orderStatusText: TextView
    private var deliveryAddress: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delivery_details)

        orderIdText = findViewById(R.id.orderIdText)
        orderAddressText = findViewById(R.id.orderAddressText)
        orderStatusText = findViewById(R.id.orderStatusText)

        val orderId = intent.getStringExtra("order_id") ?: ""
        deliveryAddress = intent.getStringExtra("delivery_address") ?: ""
        val orderStatus = intent.getStringExtra("status") ?: ""

        orderIdText.text = "Order ID: $orderId"
        orderAddressText.text = "Address: $deliveryAddress"
        orderStatusText.text = "Status: $orderStatus"

        // Open Google Maps when address is clicked
        orderAddressText.setOnClickListener {
            openGoogleMaps(deliveryAddress)
        }
    }

    private fun openGoogleMaps(address: String) {
        val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


}

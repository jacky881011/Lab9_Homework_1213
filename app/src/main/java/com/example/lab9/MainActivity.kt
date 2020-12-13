package com.example.lab9

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var flag=false
    private val receiver:BroadcastReceiver=object:BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {  //廣播後要執行的動作

            intent.extras?.let {
                tv_clock?.text = "%02d:%02d:%02d".format(it.getInt("H"), it.getInt("M"), it.getInt("S"))
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent_filter=IntentFilter("MyMessage")
        //sign in Receiver
        registerReceiver(receiver,intent_filter)
        flag=MyService.flag
        if(flag){btn_start.text="暫停"} else {btn_start.text="開始"}
        btn_start.setOnClickListener {
            flag=!flag
            if(flag){btn_start.text="暫停"} else {btn_start.text="開始"}
            //Service ON
            startService(Intent(this,MyService::class.java).putExtra("flag",flag))
            Toast.makeText(this,if(flag)"計時開始" else "計時暫停",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}

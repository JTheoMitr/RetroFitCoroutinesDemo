package android.example.retrofitcoroutinesdemo

import android.example.retrofitcoroutinesdemo.api.CatJson
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//const val BASE_URL = "https://cat-fact.herokuapp.com"
const val BASE_URL = "http://34.134.148.105/"

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrentData()

        layout_generate_new_fact.setOnClickListener {
            getCurrentData()
        }


    }

    private fun getCurrentData()  {

        tv_textView.visibility = View.INVISIBLE
        tv_timeStamp.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        MainScope().launch(Dispatchers.IO) {

            try {
                val response = api.getCatFacts().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    Log.d(TAG, data.content.toString())

                    withContext(Dispatchers.Main) {
                        tv_textView.visibility = View.VISIBLE
                        tv_timeStamp.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE

                        // data.content is your nested array of POSTS to iterate for the ADAPTER
                        tv_textView.text = data.content[0].message.toString()
                        tv_timeStamp.text = data.content[0].createdOn.toString()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "seems like no net", Toast.LENGTH_LONG).show()
                }
            }
        }





    }
}
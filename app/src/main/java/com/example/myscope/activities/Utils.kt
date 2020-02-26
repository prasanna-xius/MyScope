package com.example.myscope.activities

object Utils {
      
       val BASE_URL = "Add your backend end point"

       val apiService: APIService
       get() = RetrofitClient1.getClient(BASE_URL)!!.create(APIService::class.java)
}
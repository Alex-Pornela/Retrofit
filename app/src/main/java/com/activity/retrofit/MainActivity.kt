package com.activity.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.activity.retrofit.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var retService : AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //fetch albums data from api
        //get the service interface and use the retrofit instance class created
        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        getRequestWithQueryParameters()
        getRequestWithPathParameters()

    }

    //function for get request with Query Parameters
    private fun getRequestWithQueryParameters(){
        //using coroutine live data builder we will get the retrofit response as live data
        val responseLiveData:LiveData<Response<Album>> = liveData{
            //get the response object using interface
            val response :Response<Album> = retService.getAlbums()
            //for having a query parameter
            val responseForQueryParameter :Response<Album> = retService.getSortedAlbums(3)
            //emit to return from live data builder block as live data
            emit(response)
        }
        //observe live data
        responseLiveData.observe(this, Observer {
            //get list of albums item object
            //litIterator() return a list iterator over elements in proper sequence
            val albumList = it.body()?.listIterator()
            //to get the album objects
            if(albumList!=null){
                while (albumList.hasNext()){
                    val albumItem : AlbumItem = albumList.next()
                    //show data on textView
                    val result:String = " "+"Album id: ${albumItem.title}"+"\n"+
                            " "+"Album id: ${albumItem.id}"+"\n"+
                            " "+"Album id: ${albumItem.userId}"+"\n\n\n"
                    binding.textView.append(result)

                }
            }
        })
    }

    //function for get request with Path Parameters
    private fun getRequestWithPathParameters(){
        //path parameters example
        val pathResponse : LiveData<Response<AlbumItem>> = liveData {
            val response : Response<AlbumItem> = retService.getAlbum(3)
            emit(response)
        }
        pathResponse.observe(this, Observer {
            val title : String? = it.body()?.title
            Toast.makeText(applicationContext,title,Toast.LENGTH_LONG).show()
        })
    }

}
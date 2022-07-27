package com.activity.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumService {
    //get annotation for http request and put the url end point where to get the data
    @GET("/albums")
    //abstract function that returns retrofit response object of type albums
   //suspend for kotlin coroutine with retrofit
    //if not use coroutine then no suspend modifier
    //:Response<Album> is the return type the name of the data class (Album)
    suspend fun getAlbums() :Response<Album>

    //function to get a list of sorted by user id albums
    //Query parameter
    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId : Int ) :Response<Album>

    //Path Parameters
    @GET("/albums/{id}")
    suspend fun getAlbum(@Path(value = "id")albumID:Int) : Response<AlbumItem>

}
package com.example.memories.afterlogin.album

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.provider.MediaStore
import android.widget.Toast
import java.net.URL

class DownloadAsyncTask(var downloadListener: IDownloadListener) : AsyncTask<String, Int, Bitmap>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): Bitmap {
        val url=params[0]
        var inputStream=URL(url).openStream()
        val bitmap=BitmapFactory.decodeStream(inputStream)
        return bitmap
    }


    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        if (result != null) {
            downloadListener.onDownloadComplete(result)
        }
    }

    interface IDownloadListener{
        fun onDownloadComplete(bitmap:Bitmap)
    }


}
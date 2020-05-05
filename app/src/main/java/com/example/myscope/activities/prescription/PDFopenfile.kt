package com.example.myscope.activities.prescription

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.myscope.R
import com.github.barteksc.pdfviewer.PDFView

class PDFopenfile : AppCompatActivity() {

    lateinit   var pdfview: PDFView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfopenfile)
        pdfview = findViewById(R.id.pdfView)


        val name = intent.getStringExtra("pdf")
        var pureBase64Encoded = name.substring(name.indexOf(",") + 1);

        val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

        val buf: ByteArray = decodedBytes
        val s: String = String(buf);
        var uri: Uri = Uri.parse(s)
       pdfview.fromUri(uri)
    }
}

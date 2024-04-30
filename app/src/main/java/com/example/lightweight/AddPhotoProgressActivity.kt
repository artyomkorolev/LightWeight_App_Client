package com.example.lightweight

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide

class AddPhotoProgressActivity : AppCompatActivity() {
    private lateinit var image : ImageView
    private lateinit var backbutton:Button
    private lateinit var addPhoto:ImageView
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo_progress)
        image = findViewById(R.id.addedImage)
        Glide.with(applicationContext).load(R.drawable.fat).into(image)
        image.visibility = View.GONE

        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this,GalleryActivity::class.java)
            startActivity(backIntent)
        }

        saveButton=findViewById(R.id.saveButton)
        saveButton.setOnClickListener {

            val saveIntent = Intent(this,GalleryActivity::class.java)
            startActivity(saveIntent)
            Toast.makeText(applicationContext,"Прогресс сохранен",Toast.LENGTH_SHORT).show()

        }

        addPhoto = findViewById(R.id.addphoto)
        addPhoto.setOnClickListener {
            image.visibility=View.VISIBLE
        }
    }


}
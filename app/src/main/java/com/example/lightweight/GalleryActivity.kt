package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {
    private lateinit var profileName:TextView
    private lateinit var addPhoto: ImageView
    private lateinit var rvPhotos:RecyclerView
    //BOTTOMBAR
    private lateinit var buttonFk: Button
    private lateinit var buttonFood: Button
    private lateinit var buttonLK: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        buttonFk=findViewById(R.id.buttonFK)
        buttonFood=findViewById(R.id.buttonFood)
        buttonLK = findViewById(R.id.buttonLK)
        addPhoto = findViewById(R.id.addphoto)
        rvPhotos = findViewById(R.id.rvPhotos)


        buttonFk.setOnClickListener{
            val fkIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(fkIntent)
        }
        buttonFood.setOnClickListener{
            val fkIntent = Intent(this, MainActivity::class.java)
            startActivity(fkIntent)
        }
        buttonLK.setOnClickListener{
            val galIntent = Intent(this, PersonalAccountActivity::class.java)
            startActivity(galIntent)
        }
        addPhoto.setOnClickListener{
            val addIntent = Intent(this, AddPhotoProgressActivity::class.java)
            startActivity(addIntent)
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPhotos.layoutManager = layoutManager
        val photoAdapter = PhotoAdapter(
            listOf(
                Photo(R.drawable.fat,"19 февраля 2020","130 кг"),
                Photo(R.drawable.strong,"21 февраля 2023","90 кг")
            )
        )

        rvPhotos.adapter=photoAdapter
    }
}
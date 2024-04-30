package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvFoodList: RecyclerView
    private lateinit var tvDate: TextView
    private lateinit var currentCalories:TextView
    private lateinit var totalCalories: TextView
    private lateinit var addEatingButton: ImageView
    //BOTTOMBAR
    private lateinit var buttonFk: Button
    private lateinit var buttonGallery:Button
    private lateinit var buttonLK: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LightWeight)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvFoodList = findViewById(R.id.rvFoodList)
        tvDate = findViewById(R.id.tvDate)
        currentCalories =findViewById(R.id.curCal)
        totalCalories = findViewById(R.id.totalCal)
        addEatingButton = findViewById(R.id.addEating)
        buttonFk=findViewById(R.id.buttonFK)
        buttonGallery = findViewById(R.id.buttonGallery)
        buttonLK = findViewById(R.id.buttonLK)





        buttonFk.setOnClickListener{
            val fkIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(fkIntent)
        }
        buttonGallery.setOnClickListener{
            val galIntent = Intent(this, GalleryActivity::class.java)
            startActivity(galIntent)
        }
        buttonLK.setOnClickListener{
            val galIntent = Intent(this, PersonalAccountActivity::class.java)
            startActivity(galIntent)
        }
        addEatingButton.setOnClickListener{
            val addIntent = Intent(this, AddEatingActivity::class.java)
            startActivity(addIntent)
        }

        val eatingAdapter = EatingAdapter(
            listOf(
                Eating("8:00","500","30","40","50"),
                Eating("10:00","400","10","50","0"),
                Eating("13:00","300","43","24","40"),
                Eating("16:00","250","12","87","0"),
                Eating("20:00","100","0","32","15")
            ),
            object : EatingAdapter.EatingActionListener{
                override fun OnClickItem(eating: Eating) {
                    val checkIntent = Intent(this@MainActivity,CheckEatingActivity::class.java)
                    startActivity(checkIntent)
                }
            },
            currentCalories

        )
        rvFoodList.adapter = eatingAdapter
        eatingAdapter.updateCalories()

    }
}
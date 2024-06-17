package com.example.lightweight.ManagementActivities.GraphActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.ExercizeAdapter
import com.example.lightweight.ManagementActivities.PersonalAccountActivity
import com.example.lightweight.Models.Exercize
import com.example.lightweight.R

class StatFKActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var rvExercizeList: RecyclerView



    private lateinit var etSearchExercize: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat_fk)

        backButton = findViewById(R.id.backbutton)
        rvExercizeList = findViewById(R.id.rvExercizeList)



        etSearchExercize = findViewById(R.id.imputEditText)




        backButton.setOnClickListener {
            val backIntent= Intent(this, PersonalAccountActivity::class.java)
            startActivity(backIntent)
        }


        val exercizeAdapter = ExercizeAdapter(
            listOf(
                Exercize("1","Бег","км"),
                Exercize("1","Плавание","км"),
                Exercize("1","Гири","км"),
                Exercize("1","Ходьба","км"),
                Exercize("1","Штанга","км"),
                Exercize("1","Сон","км")



            ),
            object : ExercizeAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exercize) {
                    val graphIntent = Intent(this@StatFKActivity, PhysicalGraphActivity::class.java)
                    startActivity(graphIntent)
                }
            }, object : ExercizeAdapter.OnItemClickListener{
                override fun onSaveClick(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Сохранить",Toast.LENGTH_SHORT).show()
                }

                override fun onDeleteClick(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Удалить",Toast.LENGTH_SHORT).show()
                }

                override fun onGrammChange(exercize: Exercize, newCount: String) {
                    Toast.makeText(applicationContext,"Изменить",Toast.LENGTH_SHORT).show()
                }

            },hideElements = true
        )
        rvExercizeList.adapter=exercizeAdapter

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                        searchText ->
                    exercizeAdapter.filterItems(searchText.toString())
                }
            }

        }

        etSearchExercize.addTextChangedListener(textWatcher)

    }
}
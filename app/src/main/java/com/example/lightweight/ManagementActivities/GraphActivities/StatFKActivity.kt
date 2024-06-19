package com.example.lightweight.ManagementActivities.GraphActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.ExercizeAdapter
import com.example.lightweight.ManagementActivities.PersonalAccountActivity
import com.example.lightweight.Models.Exercize
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.ExercizeViewHolder
import com.example.lightweight.retrofit.ExerciseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StatFKActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var rvExercizeList: RecyclerView
    private var exercizes = ArrayList<Exercize>()
    private lateinit var authtoken:String



    private lateinit var etSearchExercize: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat_fk)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getExercisesService = retrofit.create(ExerciseApi::class.java)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"
        backButton = findViewById(R.id.backbutton)
        rvExercizeList = findViewById(R.id.rvExercizeList)
        etSearchExercize = findViewById(R.id.imputEditText)
        backButton.setOnClickListener {
            val backIntent= Intent(this, PersonalAccountActivity::class.java)
            startActivity(backIntent)
        }


        val exercizeAdapter = ExercizeAdapter(
            exercizes,
            object : ExercizeAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exercize) {
                    val graphIntent = Intent(this@StatFKActivity, PhysicalGraphActivity::class.java)
                    graphIntent.putExtra("idExervc",exercize.id)
                    graphIntent.putExtra("nameExervc",exercize.name)
                    startActivity(graphIntent)
                }
            }, object : ExercizeAdapter.OnItemClickListener{
                override fun onSaveClick(exercize: Exercize,holder: ExercizeViewHolder) {

                }

                override fun onDeleteClick(exercize: Exercize) {

                }

                override fun onGrammChange(exercize: Exercize, newCount: String) {

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

        val call  = getExercisesService.getAllExercise( )
        call.enqueue(object : Callback<List<Exercize>> {
            override fun onResponse(

                call: Call<List<Exercize>>,
                response: Response<List<Exercize>>
            ) {
                val point = 0
                if(response.code() ==200){
                    exercizes.clear()
                    if (response.body()?.isNotEmpty() == true){
                        exercizes.addAll(response.body()!!)
                        exercizeAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(p0: Call<List<Exercize>>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }
}
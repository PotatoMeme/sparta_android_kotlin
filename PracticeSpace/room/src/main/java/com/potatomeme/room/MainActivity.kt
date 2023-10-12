package com.potatomeme.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.potatomeme.room.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var myDao: MyDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initSettings()
        initViews()
    }

    private fun initSettings() {
        myDao = MyDatabase.getDatabsae(this).getMyDao()
    }

    private fun initViews() {
        val allStudents = myDao.getAllStudent()
        allStudents.observe(this) { studentList: List<Student> ->
            val str = buildString {
                for ((id, name) in studentList) {
                    append(id)
                    append("-")
                    appendLine(name)
                }
            }
            binding.textStudentList.text = str
        }

        binding.addStudent.setOnClickListener {
            val id = binding.editStudentId.text.toString().toInt()
            val name = binding.editStudentName.text.toString()
            if (id > 0 && name.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    myDao.insertStudent(
                        Student(
                            id = id,
                            name = name
                        )
                    )
                }
            }
            binding.editStudentId.text = null
            binding.editStudentName.text = null
        }
        binding.queryStudent.setOnClickListener {
            val name = binding.editStudentName.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val result = myDao.getStudentByName(name)
                if (result.isNotEmpty()) {
                    val str = buildString {
                        result.forEach { student ->
                            append(student.id)
                            append("-")
                            appendLine(student.name)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        binding.textQueryStudent.text = str
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.textQueryStudent.text = ""
                    }
                }
            }
        }

    }
}
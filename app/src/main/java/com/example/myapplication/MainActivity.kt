package com.example.loginapp

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔹 Inisialisasi komponen
        val nama = findViewById<EditText>(R.id.namaText)
        val nim = findViewById<EditText>(R.id.nimNum)
        val spinner = findViewById<Spinner>(R.id.prodiSpin)

        val male = findViewById<RadioButton>(R.id.btnMale)
        val female = findViewById<RadioButton>(R.id.btnFemale)

        val membaca = findViewById<CheckBox>(R.id.checkMembaca)
        val programming = findViewById<CheckBox>(R.id.checkPrograming)
        val other = findViewById<CheckBox>(R.id.checkOther)

        val btn = findViewById<Button>(R.id.btnConfirm)

        // 🔹 Data Spinner (Prodi)
        val prodiList = arrayOf(
            "Pilih Program Studi",
            "Teknik Informatika",
            "Teknik Sipil",
            "Teknik Elektro",
            "Teknik Mesin",
            "Teknik Industri"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            prodiList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // 🔹 Event tombol
        btn.setOnClickListener {

            val namaInput = nama.text.toString().trim()
            val nimInput = nim.text.toString().trim()
            val prodiInput = spinner.selectedItem.toString()

            // =====================
            // ✅ VALIDASI INPUT
            // =====================

            // Nama
            if (namaInput.isEmpty()) {
                nama.error = "Nama tidak boleh kosong!"
                nama.requestFocus()
                return@setOnClickListener
            }

            // NIM kosong
            if (nimInput.isEmpty()) {
                nim.error = "NIM tidak boleh kosong!"
                nim.requestFocus()
                return@setOnClickListener
            }

            // NIM angka saja
            if (!nimInput.matches(Regex("[0-9]+"))) {
                nim.error = "NIM harus berupa angka!"
                nim.requestFocus()
                return@setOnClickListener
            }

            // NIM minimal panjang
            if (nimInput.length < 8) {
                nim.error = "NIM minimal 8 digit!"
                nim.requestFocus()
                return@setOnClickListener
            }

            // Spinner
            if (spinner.selectedItemPosition == 0) {
                Toast.makeText(this, "Pilih program studi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gender
            val genderInput = when {
                male.isChecked -> "Laki-Laki"
                female.isChecked -> "Perempuan"
                else -> ""
            }

            if (genderInput.isEmpty()) {
                Toast.makeText(this, "Pilih jenis kelamin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Hobi
            val hobiList = mutableListOf<String>()
            if (membaca.isChecked) hobiList.add("Membaca")
            if (programming.isChecked) hobiList.add("Programming")
            if (other.isChecked) hobiList.add("Lainnya")

            if (hobiList.isEmpty()) {
                Toast.makeText(this, "Pilih minimal 1 hobi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hobiInput = hobiList.joinToString(", ")

            // =====================
            // 🚀 PINDAH KE BIO
            // =====================

            val intent = Intent(this, BioActivity::class.java)

            intent.putExtra("nama", namaInput)
            intent.putExtra("nim", nimInput)
            intent.putExtra("prodi", prodiInput)
            intent.putExtra("gender", genderInput)
            intent.putExtra("hobi", hobiInput)

            startActivity(intent)
        }
    }
}

class BioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bio_info)

        val nama = intent.getStringExtra("nama")
        val nim = intent.getStringExtra("nim")
        val prodi = intent.getStringExtra("prodi")
        val gender = intent.getStringExtra("gender")
        val hobi = intent.getStringExtra("hobi")

        findViewById<TextView>(R.id.namaUser).text = nama
        findViewById<TextView>(R.id.nimUser).text = nim
        findViewById<TextView>(R.id.prodiUser).text = prodi
        findViewById<TextView>(R.id.genderUser).text = gender
        findViewById<TextView>(R.id.hobiUser).text = hobi
    }
}
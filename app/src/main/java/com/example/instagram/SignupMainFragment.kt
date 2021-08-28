package com.example.instagram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_signup_main.*


class SignupMainFragment : Fragment(R.layout.fragment_signup_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        tvSignup.setOnClickListener {
            navController.navigate(R.id.action_signupMainFragment_to_signupFragment)
        }

        tvLogin.setOnClickListener {
            navController.navigate(R.id.action_signupMainFragment_to_loginFragment2)
        }
    }
//
//    class SignUpMainFragment : AppCompatActivity(), AdapterView.OnItemSelectedListener {
//
//        private var spinner: Spinner? = null
//        private var arrayAdapter: ArrayAdapter<String>? = null
//
//        private var itemList = arrayOf(
//            "English",
//            "Afrikaans",
//            "Bahasa Indonesia",
//            "Dansk (Danish)",
//            "Deutsch (German)",
//            "English (UK)",
//            "Español (Latin America)",
//            "Español (Spain)",
//            "Filipino",
//            "Français (Canada)",
//            "Français (France)",
//            "Hrvatski (Croatian)",
//            "Italiano (Italian)",
//            "Magyar (Hungarian)",
//            "Nederlands (Dutch)",
//            "Norsk (bokmal)",
//            "Polski (Polish)",
//            "Português (Brasil)",
//            "Português (Portugal)",
//            "Românâ (Romanian)",
//            "Slovenčina (Slovak)",
//            "Tiê' Viêt (Vietnamese)",
//            "Tükçe (Turkish)",
//            "Čeština (Czech)",
//            "PycckŃŃ (Russia)",
//            "हिंदी (India)",
//            "ภาษาไทย (Thai)",
//            "中国人 (Chinese Simplified)",
//            "传统, 台湾 (Traditional, Taiwan)",
//            "日本語 (Japanese)",
//            "한국인 (Korean)",
//        )
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContentView(R.layout.fragment_signup_main)
//
//            spinner = findViewById(R.id.spinners)
//            arrayAdapter =
//                ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, itemList)
//            spinner?.adapter = arrayAdapter
//            spinner?.onItemSelectedListener = this
//
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>?) {
//            Toast.makeText(applicationContext, "Nothing Select", Toast.LENGTH_SHORT).show()
//
//        }
//
//        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//            var items: String = parent?.getItemAtPosition(position) as String
//            Toast.makeText(applicationContext, "$items", Toast.LENGTH_LONG).show()
//        }
//    }
}
package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import android.os.Build
import android.os.VibrationEffect


class signingFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navControl : NavController
    private lateinit var binding: FragmentSigninBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        init(view)
        binding.btnSignup.setOnClickListener {
            navControl.navigate(R.id.action_signinFragment_to_signupFragment)
        }

        binding.btnSignin.setOnClickListener {
            registerEvents()
        }

    }

    private fun registerEvents() {

        binding.btnSignin.setOnClickListener {
            val email = binding.EtEmail.text.toString().trim()
            val password = binding.EtPass.text.toString().trim()
            if (email.isEmpty()||password.isEmpty()){
                Toast.makeText(context,"Please fill all the details",Toast.LENGTH_LONG).show()
            }
            if (email.isNotEmpty()&&password.isNotEmpty()){
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(context,"Login Successfully", Toast.LENGTH_SHORT).show()
                            navControl.navigate(R.id.action_signinFragment_to_homeFragment)
                        }
                        else{
                            Toast.makeText(context,it.exception?.message, Toast.LENGTH_SHORT).show()
                        }

                    }
            }

        }

    }
    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

    }

}



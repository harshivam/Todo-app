package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth


class signupFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navControl : NavController
    private lateinit var binding: FragmentSignupBinding

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

       binding = FragmentSignupBinding.inflate(inflater,container,false)
       return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun registerEvents() {

        binding.accsignin.setOnClickListener {
            navControl.navigate(R.id.action_signupFragment_to_signinFragment)
        }


        binding.Signupbtn.setOnClickListener {
            val email = binding.EtEmail.text.toString().trim()
            val password = binding.EtPass.text.toString().trim()
            val verifyPassword = binding.EtRepass.text.toString().trim()
            if (email.isEmpty()||password.isEmpty()||verifyPassword.isEmpty()){
                Toast.makeText(context,"Please fill all the details",Toast.LENGTH_LONG).show()

            }
            if (email.isNotEmpty()&&password.isNotEmpty()&&verifyPassword.isNotEmpty()){
                if (password == verifyPassword){
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(context,"Account successfully registered",Toast.LENGTH_SHORT).show()
                            navControl.navigate(R.id.action_signupFragment_to_homeFragment)
                        }
                        else{
                            Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()

                        }

                    }

                }

        }

    }}

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

    }

}
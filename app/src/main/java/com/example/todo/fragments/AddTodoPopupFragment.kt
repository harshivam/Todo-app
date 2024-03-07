package com.example.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todo.databinding.FragmentAddTodoPopupBinding


class AddTodoPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener

    fun setListener(listener: homeFragment) {
        this.listener = listener
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registerEvents()
    }

    private fun registerEvents() {
        binding.btnAddTask.setOnClickListener {

            val todoTask = binding.inputTask.text.toString()
            if (todoTask.isNotEmpty()) {
                listener.onSaveTask(todoTask, binding.inputTask)
                binding.btnAddTask.playAnimation()


            } else {
                Toast.makeText(context, "please Type some task first", Toast.LENGTH_LONG).show()
            }

        }
        binding.closebtn.setOnClickListener {
            dismiss()
        }

    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoEt: EditText) {

        }
    }


}
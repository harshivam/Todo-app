package com.example.todo.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.utils.ToDoAdapter
import com.example.todo.utils.ToDoData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeFragment : Fragment(), AddTodoPopupFragment.DialogNextBtnClickListener,
    ToDoAdapter.TodoAdapterClickInterface {

    private lateinit var  auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popupFragment: AddTodoPopupFragment
    private lateinit var adapter:ToDoAdapter
    private lateinit var mList: MutableList<ToDoData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getDatabaseFromFirebase()
      registerEvents()



        

    }



    private fun registerEvents(){

    binding.btnAdd.setOnClickListener {
        binding.btnAdd.playAnimation()
        popupFragment = AddTodoPopupFragment()
        popupFragment.setListener(this)
        popupFragment.show(
            childFragmentManager,
            "AddTodoFragment"
        )

    }

}


    private fun init(view: View) {
   navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Tasks").child(auth.currentUser?.uid.toString())

      binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        adapter = ToDoAdapter(mList)
        adapter.setListener(this)
        binding.recyclerView.adapter = adapter


    }
    private fun getDatabaseFromFirebase() {
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for(taskSnapshot in snapshot.children){
                    val todoTask = taskSnapshot.key?.let{
                        ToDoData(it,taskSnapshot.value.toString())

                }
                    if (todoTask!=null){
                        mList.add(todoTask)
                    }
                    adapter.notifyDataSetChanged()

            }}

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show()

            }

        })

    }

    override fun onSaveTask(todo: String, todoEt: EditText) {
        databaseReference.push().setValue(todo).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(context,"Task added Successfully",Toast.LENGTH_SHORT).show()
                todoEt.text = null

            }else{
                Toast.makeText(context,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }

        }
        Handler().postDelayed({
            popupFragment.dismiss()
        }, 2000)
    }

    override fun onDeleteTaskBtnClicked(toDoData: ToDoData){
        databaseReference.child(toDoData.taskId).removeValue().addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context,"Task Deleted Successfully",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,it.exception.toString(),Toast.LENGTH_SHORT).show()

            }
        }

    }
    override fun onEditTaskBtnClicked(toDoData: ToDoData){

    }

    }


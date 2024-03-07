package com.example.todo.utils

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.EachTodoItemBinding
import com.example.todo.fragments.homeFragment


class ToDoAdapter(private val list:MutableList<ToDoData>):
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {


  private var listener:TodoAdapterClickInterface?=null
    fun setListener(listener: homeFragment){
        this.listener = listener

    }


    inner class ToDoViewHolder(val binding:EachTodoItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
      val binding = EachTodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return this.ToDoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
      with(holder){
          with(list[position]){
              binding.todoTask.text = this.task
              binding.btnDlt.setOnClickListener {
                  binding.btnDlt.playAnimation()
                  Handler().postDelayed({
                      listener?.onDeleteTaskBtnClicked(this)
                  }, 1800)



              }
              binding.btnEdit.setOnClickListener {
                  listener?.onEditTaskBtnClicked(this)
                  binding.btnEdit.playAnimation()

              }

          }
      }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setListener() {

    }

    interface TodoAdapterClickInterface{
        fun onDeleteTaskBtnClicked(toDoData: ToDoData){



        }
        fun onEditTaskBtnClicked(toDoData: ToDoData){

        }
    }


}
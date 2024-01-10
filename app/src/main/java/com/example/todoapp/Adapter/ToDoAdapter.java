package com.example.todoapp.Adapter;

//import static com.example.todoapp.AddNewTask.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddNewTask;
import com.example.todoapp.MainActivity;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.R;
import com.example.todoapp.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDB;
    public String up;



    public ToDoAdapter(DataBaseHelper myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent , false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ToDoModel item = mList.get(position);
        holder.task.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.des.setText(item.getDes());
        up = item.getDes();
        holder.s.setText("Status:" + progress(0) + "                     " + item.getDat());
        final MyViewHolder finalHolder = holder;
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("CheckBoxState", "onCheckedChanged called with isChecked: " + isChecked);
                if (isChecked){
                    myDB.updateStatus(item.getId() , 1);
                    myDB.updateDat(item.getId(), item.getDes(), 1);
                    finalHolder.s.setText("Status:" + "inprogress" + "                     " + item.getDat());

                }else
                    myDB.updateStatus(item.getId() , 0);
                myDB.updateDat(item.getId(), item.getDes(), 0);
                finalHolder.s.setText("Status:" + "Completed" + "                     " + item.getDat());

            }

        });
//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
//            }
//        });

    }

    private String progress(int i) {
        if(i==1)
            return "Completed";
        if(i==0)
            return "inprogress";
        return "New";
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
    }



    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ToDoModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deletTask(int position){
        ToDoModel item = mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

//    public void editItem(int position){
//        ToDoModel item = mList.get(position);
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("id" , item.getId());
//        bundle.putString("task" , item.getTask());
//        bundle.putString("des" , item.getDes());
//        bundle.putString("dat" , item.getDat());
//
//
//
//        AddNewTask task = new AddNewTask();
//        task.setArguments(bundle);
//        task.show(activity.getSupportFragmentManager() , task.getTag());
//
//
//    }

    public void editItem(ToDoModel item) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        bundle.putString("des", item.getDes()); // Added description field
        bundle.putString("dat", item.getDat()); // Added date field

        AddNewTask editTaskFragment = new AddNewTask();
        editTaskFragment.setArguments(bundle);

        // Show the fragment using the FragmentManager
        editTaskFragment.show(activity.getSupportFragmentManager(), editTaskFragment.getTag());
    }

    public ToDoModel getItem(int position) {
        return mList.get(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public static String setDAT;
//        private EditText card;
        CheckBox mCheckBox;
        private TextView task,s,des;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
            task =  itemView.findViewById(R.id.task);
            s =itemView.findViewById(R.id.s);
            des =itemView.findViewById(R.id.des);
//            card = itemView.findViewById(R.id.card);

        }
    }
}

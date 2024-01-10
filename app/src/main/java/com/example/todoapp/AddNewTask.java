package com.example.todoapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment implements Date_and_time.DatListener {

    public static final String TAG = "AddNewTask";

    //widgets
    private EditText mEditText,desc;
    private Button mSaveButton, duedate, cancel;


    private DataBaseHelper myDb;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_newtask , container , false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.edittext);
        mSaveButton = view.findViewById(R.id.button_save);
        cancel = view.findViewById(R.id.cancel);
        duedate = view.findViewById(R.id.duedateinput);
        desc = view.findViewById(R.id.desc);


        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openDialog();
            }
        });

        myDb = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String d = bundle.getString("des");
            String t = bundle.getString("task");
            String a = bundle.getString("dat");
            mEditText.setText(t);
            desc.setText(d);
            duedate.setText("Status:" + progress(0) + "                     " + a);


//            if (t.length() > 0 ){
//                mSaveButton.setEnabled(false);
//            }
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.toString().equals("")){
                   mSaveButton.setEnabled(false);
                   mSaveButton.setBackgroundColor(Color.GRAY);
               }else{
                   mSaveButton.setEnabled(true);
                   mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String text = mEditText.getText().toString();
               String d = desc.getText().toString();
               String b = duedate.getText().toString();
                if (finalIsUpdate){
                   myDb.updateTask(bundle.getInt("id") , text);
                   myDb.updateDes(bundle.getInt("id"), d);
                   myDb.updateDat(bundle.getInt("id"), b, 0);
               }else{
                   ToDoModel item = new ToDoModel();
                   item.setTask(text);
                   item.setStatus(0);
                   item.setDes(d);
                   item.setDat(b);
                   myDb.insertTask(item);
               }
               dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String progress(int i) {
        if(i==1)
            return "Completed";
        if(i==0)
            return "inprogress";
        return "New";
    }

    private void openDialog() {
        Date_and_time date_and_time = new Date_and_time();
        date_and_time.setDatListener(this);
        date_and_time.show(getChildFragmentManager(), "date time");
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }

    @Override
    public void applyTexts(String d, String t) {

        duedate.setText(d + " ," + t);
    }
}

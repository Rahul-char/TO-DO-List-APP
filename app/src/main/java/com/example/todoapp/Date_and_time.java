package com.example.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Date_and_time extends AppCompatDialogFragment {
    private EditText date,time;
    private DatListener listener;
    private String d, t;

    @Override
    public Dialog onCreateDialog(Bundle savedInstances) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.date_time, null);
        builder.setView(view)
//                .setTitle("Set Date and Time")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String d = date.getText().toString();
                        String t = time.getText().toString();
                        listener.applyTexts(d, t);

                    }
                });

        date = view.findViewById(R.id.dateinput);
        time = view.findViewById(R.id.timeinput);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCal();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClock();
            }
        });
        return builder.create();
    }

    private void openClock() {
        TimePickerDialog dialog = new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {

                time.setText(String.valueOf(h)+ " : "+String.valueOf(m));

            }
        }, 10,00,true);
        dialog.show();
    }

    private void openCal() {
        DatePickerDialog dialog = new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                date.setText(String.valueOf(day) +"-"+ String.valueOf(month+1) +"-"+String.valueOf(year));
            }
        }, 2022, 0, 15);
        dialog.show();
    }

//    @Override
//    public void onAttach(Context context){
//        super.onAttach(context);
//        try {
//            listener = (DatListener) context;
//        }catch (ClassCastException e){
//            throw new ClassCastException(context.toString() + "must implement datlistener");
//        }
//
//    }

    public interface DatListener{
        void applyTexts(String d,String t);
    }

    public void setDatListener(DatListener l) {
        this.listener = l;
    }

//    private void notifyDateSet(int year, int month, int day) {
//        if (listener != null) {
//            listener.applyTexts(year, month, day);
//        }
//    }
}
package me.doapps.tacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import me.doapps.adapter.Teacher_Adapter;
import me.doapps.beans.Teacher_DTO;
import me.doapps.dialogs.Dialog_Rate;
import me.doapps.modules.Module_Tacher;

/**
 * Created by jnolascob on 31/08/2014.
 */
public class Search extends ActionBarActivity implements AdapterView.OnItemClickListener, TextWatcher {
    private ArrayList<Teacher_DTO> master_teacher_dtos = new ArrayList<Teacher_DTO>();
    private ArrayList<Teacher_DTO> temp_teacher_dtos = new ArrayList<Teacher_DTO>();
    ProgressDialog pDialog;

    private ListView list_teacher;
    private ProgressBar loadingList;
    private EditText txt_search_name;
    private ImageButton btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loadingList = (ProgressBar)findViewById(R.id.loadingList);
        list_teacher = (ListView)findViewById(R.id.list_teacher);
        txt_search_name = (EditText)findViewById(R.id.txt_search_name);

        list_teacher.setOnItemClickListener(this);
        txt_search_name.addTextChangedListener(this);

        Module_Tacher module_tacher = new Module_Tacher(this);
        module_tacher.sendRequestListTeacher();
        module_tacher.setInterface_listTeacher(new Module_Tacher.Interface_ListTeacher() {
            @Override
            public void getListTeacher(boolean status, final ArrayList<Teacher_DTO> teacher_dtos, String message) {
                if(status){
                    loadingList.setVisibility(View.GONE);
                    list_teacher.setVisibility(View.VISIBLE);
                    list_teacher.setAdapter(new Teacher_Adapter(teacher_dtos, Search.this));
                    master_teacher_dtos.clear();
                    master_teacher_dtos = teacher_dtos;
                }
                else{
                    Toast.makeText(Search.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_search = (ImageButton)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog_Rate dialog_rate = new Dialog_Rate(Search.this);
                dialog_rate.show();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Teacher_DTO teacher_dto = (Teacher_DTO)adapterView.getAdapter().getItem(i);
        Log.e("teacher dto", teacher_dto.toString() + ", " + teacher_dto.getName());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Log.e("text", charSequence.toString());
        temp_teacher_dtos.clear();
        for (int j = 0; j < master_teacher_dtos.size(); j++) {
            if(master_teacher_dtos.get(j).getName().toLowerCase().indexOf(charSequence.toString().toLowerCase()) != -1){
                temp_teacher_dtos.add(master_teacher_dtos.get(j));
            }
        }
        list_teacher.setAdapter(new Teacher_Adapter(temp_teacher_dtos, Search.this));
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}

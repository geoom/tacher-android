package me.doapps.tacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import me.doapps.beans.Teacher_DTO;
import me.doapps.dialogs.Dialog_Rate;
import me.doapps.modules.Module_Tacher;
import me.doapps.views.View_Tacher;

/**
 * Created by jnolascob on 31/08/2014.
 */
public class Search extends ActionBarActivity {
    private ArrayList<Teacher_DTO> teacher_dtos = new ArrayList<Teacher_DTO>();
    ProgressDialog pDialog;
    private LinearLayout frame_teachers;


    private ImageButton btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        frame_teachers = (LinearLayout)findViewById(R.id.frame_teachers);


        Module_Tacher module_tacher = new Module_Tacher(this);
        module_tacher.sendRequestListTeacher();
        module_tacher.setInterface_listTeacher(new Module_Tacher.Interface_ListTeacher() {
            @Override
            public void getListTeacher(boolean status, final ArrayList<Teacher_DTO> teacher_dtos, String message) {
                Log.e("size", String.valueOf(teacher_dtos.size()));
                for (int i = 0; i < teacher_dtos.size(); i++) {
                    View_Tacher view_tacher = new View_Tacher(Search.this, teacher_dtos.get(i));
                    view_tacher.setInterfaceTacher(new View_Tacher.Interface_Teacher() {
                        @Override
                        public void getTeacher(Teacher_DTO teacher_dto1) {
                            Log.e("teacher dto", teacher_dto1.toString());
                            Intent intent = new Intent(Search.this, Profile.class);
                            intent.putExtra("teacher_id", teacher_dto1.getId());
                            intent.putExtra("teacher_name", teacher_dto1.getName());
                            intent.putExtra("teacher_description", teacher_dto1.getDescription());
                            intent.putExtra("teacher_image", teacher_dto1.getImage());
                            startActivity(intent);
                        }
                    });
                    frame_teachers.addView(view_tacher);
                }
                Log.e("aviso", message);
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

}

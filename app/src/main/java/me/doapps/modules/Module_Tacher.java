package me.doapps.modules;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.doapps.beans.Teacher_DTO;
import me.doapps.endpoints.EndPoints;

/**
 * Created by jnolascob on 04/10/2014.
 */
public class Module_Tacher {
    private Context context;

    private Interface_ListTeacher interface_listTeacher;

    public Module_Tacher(Context context) {
        this.context = context;
    }


    public void sendRequestListTeacher() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.GET,
                EndPoints.WS_LIST_TEACHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("result", jsonArray.toString());
                            ArrayList<Teacher_DTO> teacher_dtos = new ArrayList<Teacher_DTO>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String id = jsonArray.getJSONObject(i).getString("id");
                                String name = jsonArray.getJSONObject(i).getString("name");
                                String description = jsonArray.getJSONObject(i).getString("description");
                                String image = jsonArray.getJSONObject(i).getString("avatar_url");
                                teacher_dtos.add(new Teacher_DTO(id, name, description, image));
                            }
                            interface_listTeacher.getListTeacher(true, teacher_dtos, "teacher listed");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "Ocurrio un error de Conexion", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonObjectRequest);
    }

    /*Set interfaces*/
    public void setInterface_listTeacher(Interface_ListTeacher interface_listTeacher) {
        this.interface_listTeacher = interface_listTeacher;
    }

    /*Inner Interface*/
    public interface Interface_ListTeacher {
        void getListTeacher(boolean status, ArrayList<Teacher_DTO> teacher_dtos, String message);
    }

}

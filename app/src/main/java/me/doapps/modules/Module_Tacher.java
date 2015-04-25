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
import me.doapps.ws.WS_Tacher;

/**
 * Created by jnolascob on 04/10/2014.
 */
public class Module_Tacher {
    private Context context;

    private Interface_ListTeacher interface_listTeacher;
    private Interface_Login interface_login;

    public Module_Tacher(Context context) {
        this.context = context;
    }

    /*Login with facebook*/
    public void sendRequestLogin(final String access_token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.GET,
                String.format(WS_Tacher.WS_LOGIN, access_token),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("result", jsonObject.toString());

                            boolean success = jsonObject.getBoolean("status");
                            if (success) {
                                int id = jsonObject.getJSONObject("user").getInt("id");
                                interface_login.getLogin(success, id, "Login success");
                            }
                            else{
                                interface_login.getLogin(success, -1, "Login failed");
                            }
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
                                int rating = jsonArray.getJSONObject(i).getInt("global_rating");
                                teacher_dtos.add(new Teacher_DTO(id, name, description, image, rating));
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
    public void setInterface_login(Interface_Login interface_login){
        this.interface_login = interface_login;
    }

    /*Inner Interface*/
    public interface Interface_ListTeacher {
        void getListTeacher(boolean status, ArrayList<Teacher_DTO> teacher_dtos, String message);
    }
    public interface Interface_Login{
        void getLogin(boolean status, int user_id, String message);
    }

}

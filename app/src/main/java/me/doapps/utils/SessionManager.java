package me.doapps.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jnolascob on 04/10/2014.
 */
public class SessionManager {
    private static final String PREFERENCE_NAME = "tacher_preference";

    public static final String USER_IS_LOGIN = "USER_LOGIN";

    public static final String USER_FACEBOOK = "USER_FACEBOOK";
    public static final String USER_ESTADO = "USER_ESTADO";
    public static final String USER_NICK = "USER_NICK";
    public static final String USER_NOMBRES = "USER_NOMBRES";
    public static final String USER_APELLIDOS = "USER_APELLIDOS";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_IMAGEN = "USER_IMAGEN";

    public int PRIVATE_MODE = 0;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME,
                PRIVATE_MODE);
        editor = preferences.edit();
    }

    public boolean getEstadoSession() {
        boolean estado = preferences.getBoolean(USER_IS_LOGIN, false);
        return estado;
    }

    public void crearSessionOn(String _id, String nombre, String apellidos) {
        editor.putBoolean(USER_IS_LOGIN, true);
        editor.putString(USER_FACEBOOK, _id);
        editor.putString(USER_NOMBRES, nombre);
        editor.putString(USER_APELLIDOS, apellidos);

        editor.commit();
    }

    public void cerrarSession() {
        editor.putBoolean(USER_IS_LOGIN, false);
        editor.putString(USER_NOMBRES, "Usuario");
        editor.putBoolean(USER_ESTADO, false);
        editor.commit();
    }

    public String getIdUsuario() {
        String IdUsuario = preferences.getString(USER_FACEBOOK, "0");
        return IdUsuario;
    }
    public String getNomUsuario(){
        String nombreUsuario = preferences.getString(USER_NOMBRES, "usuario");
        return nombreUsuario;
    }

    public boolean getEstadoUsuario() {
        boolean estado = preferences.getBoolean(USER_ESTADO, false);
        return estado;
    }
}

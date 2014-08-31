package me.doapps.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by jnolascob on 31/08/2014.
 */
public class Fonts {
    public static Typeface setRobotoBold(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/RobotoSlab-Bold.ttf");
    }
}

package me.doapps.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import me.doapps.beans.Teacher_DTO;
import me.doapps.tacher.R;
import me.doapps.utils.RoundedTransformation;

/**
 * Created by jnolascob on 27/09/2014.
 */
public class View_Tacher extends LinearLayout implements View.OnClickListener {
    private Teacher_DTO teacher_dto;
    private View view;


    public View_Tacher(Context context, Teacher_DTO teacher_dto) {
        super(context);
        this.teacher_dto = teacher_dto;
        initView();
    }

    public View_Tacher(Context context, AttributeSet attrs, Teacher_DTO teacher_dto) {
        super(context, attrs);
        this.teacher_dto = teacher_dto;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public View_Tacher(Context context, AttributeSet attrs, int defStyle, Teacher_DTO teacher_dto) {
        super(context, attrs, defStyle);
        this.teacher_dto = teacher_dto;
        initView();
    }

    public void initView(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_teacher, this, true);

        ImageView img_photo = (ImageView)view.findViewById(R.id.img_photo);

        String imageurl = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/p160x160/1618522_10152793594515353_605748007_n.jpg?oh=213b8b7d3c61d23c5a4eef00b2044a1c&oe=54B94155&__gda__=1421347487_eca829cd300ad0f2331480d5d388812d";
        Picasso.with(getContext()).load(imageurl).placeholder(R.drawable.gamboa_cruzado).centerCrop().fit().transform(new RoundedTransformation(75, 0)).into(img_photo);
        setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Log.e("clic docente", "ok");
    }
}

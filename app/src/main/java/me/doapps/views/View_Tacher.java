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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import me.doapps.beans.Teacher_DTO;
import me.doapps.tacher.R;
import me.doapps.utils.RoundedTransformation;

/**
 * Created by jnolascob on 27/09/2014.
 */
public class View_Tacher extends LinearLayout implements View.OnClickListener {
    private Teacher_DTO teacher_dto;
    private View view;

    private Interface_Teacher interface_teacher;

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


        TextView txt_name = (TextView)view.findViewById(R.id.txt_name);
        TextView txt_description = (TextView)view.findViewById(R.id.txt_description);
        ImageView img_photo = (ImageView)view.findViewById(R.id.img_photo);

        txt_name.setText(teacher_dto.getName());
        txt_description.setText(teacher_dto.getDescription());

        String imageurl = teacher_dto.getImage();
        Picasso.with(getContext()).load(imageurl).placeholder(R.drawable.teacher_profile_photo).centerCrop().fit().transform(new RoundedTransformation(75, 0)).into(img_photo);
        setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        interface_teacher.getTeacher(teacher_dto);
    }

    /*Set Interface*/
    public void setInterfaceTacher(Interface_Teacher interface_teacher){
        this.interface_teacher = interface_teacher;;
    }
    /*Inner Interface*/
    public interface Interface_Teacher{
        void getTeacher(Teacher_DTO teacher_dto1);
    }
}

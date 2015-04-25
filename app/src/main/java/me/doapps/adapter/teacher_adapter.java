package me.doapps.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.doapps.beans.Teacher_DTO;
import me.doapps.tacher.R;
import me.doapps.utils.RoundedTransformation;

/**
 * Created by HP on 31/08/2014.
 */
public class Teacher_Adapter extends BaseAdapter {
    private ArrayList<Teacher_DTO> teacher_dtos;
    private LayoutInflater inflater;
    private Context context;

    public Teacher_Adapter(ArrayList<Teacher_DTO> teacher_dtos, Context context) {
        this.teacher_dtos = teacher_dtos;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return teacher_dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return teacher_dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        Teacher_DTO teacher_dto = teacher_dtos.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_teacher, parent, false);
            holder = new Holder();

            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_description = (TextView)convertView.findViewById(R.id.txt_description);
            holder.img_teacher = (ImageView)convertView.findViewById(R.id.img_teacher);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.txt_name.setText(teacher_dto.getName());
        holder.txt_description.setText(teacher_dto.getDescription());
        String imageurl = teacher_dto.getImage();
        Picasso.with(context).load(imageurl).placeholder(R.drawable.teacher_profile_photo).centerCrop().fit().transform(new RoundedTransformation(75, 0)).into(holder.img_teacher);
        return convertView;
    }

    class Holder {
        TextView txt_name;
        TextView txt_description;
        ImageView img_teacher;
    }
}

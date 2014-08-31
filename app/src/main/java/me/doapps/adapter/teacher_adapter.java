package me.doapps.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.doapps.beans.Teacher_DTO;
import me.doapps.tacher.R;

/**
 * Created by HP on 31/08/2014.
 */
public class teacher_adapter extends BaseAdapter {
    private ArrayList<Teacher_DTO> teacher_dtos;
    private LayoutInflater inflater;
    private Context context;

    public teacher_adapter(ArrayList<Teacher_DTO> teacher_dtos, Context context) {
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

            holder.txt_name = (TextView) convertView.findViewById(R.id.descripcion_nombre);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.txt_name.setText(teacher_dto.getName());
        return convertView;
    }

    class Holder {
        TextView txt_name;
    }
}

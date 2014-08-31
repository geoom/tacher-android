package me.doapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.doapps.beans.Comment_DTO;
import me.doapps.beans.Teacher_DTO;
import me.doapps.tacher.R;

/**
 * Created by HP on 31/08/2014.
 */
public class comment_adapter extends BaseAdapter{
    private ArrayList<Comment_DTO> comment_dtos;
    private Context context;
    private LayoutInflater inflater;

    public comment_adapter(ArrayList<Comment_DTO> comment_dtos, Context context){
        this.comment_dtos = comment_dtos;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return comment_dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return comment_dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        Comment_DTO comment_dto = comment_dtos.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_comment, parent, false);
            holder = new Holder();

            holder.txt_comment = (TextView) convertView.findViewById(R.id.descripcion_nombre);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.txt_comment.setText(comment_dto.getComment());
        return convertView;
    }

    class Holder {
        TextView txt_comment;
        TextView txt_like;
    }
}

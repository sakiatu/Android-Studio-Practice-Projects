package com.pietheta.alarmclock.journal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pietheta.alarmclock.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Note> notes;
private Context context;
    Adapter(Context context,List<Note> notes){
        this.context = context;
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_view,viewGroup,false);
        return new ViewHolder(view);
    }
    //Show title,date,time from database to list view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String  title    = notes.get(position).getTitle();
        String  date     = notes.get(position).getDate();
        String  time     = notes.get(position).getTime();
        long    id       = notes.get(position).getId();
        Log.d("date on ", "Date on: "+date);

        viewHolder.nTitle.setText(title);
        viewHolder.nDate.setText(date);
        viewHolder.nTime.setText(time);
        viewHolder.nID.setText(String.valueOf(notes.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle,nDate,nTime,nID;

        public ViewHolder(final View itemView) {
            super(itemView);
            nTitle  = itemView.findViewById(R.id.nTitle);
            nDate   = itemView.findViewById(R.id.nDate);
            nTime   = itemView.findViewById(R.id.nTime);
            nID     = itemView.findViewById(R.id.listId);

            itemView.setOnClickListener(view->{
                    Intent intent = new Intent(view.getContext(),Detail.class);
                    intent.putExtra("ID",notes.get(getAdapterPosition()).getId());
                    view.getContext().startActivity(intent);
            });
        }
    }
}

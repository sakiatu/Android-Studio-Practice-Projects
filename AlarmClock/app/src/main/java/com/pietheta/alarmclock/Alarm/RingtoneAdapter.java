package com.pietheta.alarmclock.Alarm;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pietheta.alarmclock.R;

public class RingtoneAdapter extends RecyclerView.Adapter<RingtoneAdapter.RingtoneViewHolder> {

    private OnItemClickListener onItemClickListener;
    private RingtoneList ringtoneList;

    public RingtoneAdapter(Context context) {
        ringtoneList = new RingtoneList(context);
    }

    public interface OnItemClickListener {
        void onClick(String name, String uri);
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class RingtoneViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        RingtoneViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ringtone_name);
        }
    }

    @Override
    public RingtoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ringtone_row, parent, false);
        return new RingtoneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RingtoneViewHolder holder, int position) {
        holder.name.setText(ringtoneList.getNames().get(position));
        holder.itemView
                .setOnClickListener(v -> onItemClickListener
                        .onClick(ringtoneList.getNames().get(position),
                                ringtoneList.getUri().get(position)));
    }

    @Override
    public int getItemCount() {
        return ringtoneList.size();
    }
}


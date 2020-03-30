package com.pietheta.alarmclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    ArrayList<AlarmItem> alarmList;

    public AlarmAdapter(ArrayList<AlarmItem> alarmList) {
        this.alarmList = alarmList;
    }
public OnItemclickListener listener;
    public interface OnItemclickListener{
        void onItemClick( int position);
        void isSwitchOn(int position);

    }
    public void setOnItemClickListener(OnItemclickListener listener){
        this.listener=listener;
    }


    public static class AlarmViewHolder extends RecyclerView.ViewHolder{

    TextView time;
    TextView remainingTime;
    ImageView missionImg;
    Switch alarmSwitch;

    public AlarmViewHolder(@NonNull View itemView,OnItemclickListener listener) {
        super(itemView);
        time=itemView.findViewById(R.id.alarm_time);
        remainingTime=itemView.findViewById(R.id.remaining_time);
        missionImg = itemView.findViewById(R.id.mission_img);
        alarmSwitch = itemView.findViewById(R.id.switch_alarm);

        itemView.setOnClickListener(v -> {
            if(listener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position);
                }
            }
        });

        alarmSwitch.setOnClickListener(v -> {
            if(listener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    listener.isSwitchOn(position);
                }
            }
        });
    }


}





    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View alarmItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);

        return new AlarmViewHolder(alarmItemView,listener);
    }






    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmItem currentItem = alarmList.get(position);
        holder.time.setText(currentItem.getTime());
        holder.remainingTime.setText(getRemainingTime(position));
        holder.missionImg.setImageResource(currentItem.getImageResource());
        holder.alarmSwitch.setChecked(currentItem.isSwitchOn());

    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    //methods
    private String getRemainingTime(int position){
        return ""+position;
    }
}




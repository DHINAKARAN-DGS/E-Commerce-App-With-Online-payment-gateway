package com.daatstudios.cosmic_glow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PickAdapter extends RecyclerView.Adapter<PickAdapter.ViewHolder> {
    private List<PickModel> pickModelList;


    public PickAdapter(List<PickModel> pickModelList) {
        this.pickModelList = pickModelList;
    }

    @NonNull
    @NotNull
    @Override
    public PickAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_container, parent, false);
        return new PickAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PickAdapter.ViewHolder holder, int position) {
        String name = pickModelList.get(position).getName();
        String numer = pickModelList.get(position).getNumber();
        String drop = pickModelList.get(position).getDropl();
        String pick = pickModelList.get(position).getPickl();
        String time = pickModelList.get(position).getTime();
        String task = pickModelList.get(position).getTask();
        String status = pickModelList.get(position).getStstus();
        holder.setData(name,numer,drop,pick,time,task,status);
    }

    @Override
    public int getItemCount() {
        return pickModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,number,dropl,pickl,time,task,stat;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customerName);
            number = itemView.findViewById(R.id.customerNumber);
            dropl = itemView.findViewById(R.id.dropLocation);
            pickl = itemView.findViewById(R.id.pickLocation);
            time = itemView.findViewById(R.id.pickTime);
            task = itemView.findViewById(R.id.ctask);
            stat = itemView.findViewById(R.id.ostatus);
        }
        private void setData(String cname,String cno,String cdrop,String cpick,String ctime,String tasks,String stats){
            name.setText("Bokking Name: "+cname);
            number.setText("Contact Number: "+cno);
            dropl.setText("Drop Location: "+cdrop);
            pickl.setText("Pickup Location: "+cpick);
            time.setText("Time & Date: "+ctime);
            stat.setText("Order Status: "+stats);
            if (!tasks.equals("")) {
                task.setText("Task: " + tasks);
            }else{
                task.setVisibility(View.GONE);
            }

        }
    }
}

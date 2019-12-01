package cs160.final_proj_drawer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> {
    private ArrayList<Stop> dataList;
    Context context;


    public StopAdapter(ArrayList<Stop> data)
    {
        this.dataList = data;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView stopName;
        TextView address;
        TextView description;
        ArrayList<String> photos;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.stopName = (TextView) itemView.findViewById(R.id.stopName);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stop, parent, false);
        StopAdapter.ViewHolder viewHolder = new StopAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StopAdapter.ViewHolder holder, final int position)
    {
        holder.stopName.setText(dataList.get(position).getName());
        holder.address.setText(dataList.get(position).getLocation());
        holder.description.setText(dataList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i("NOTE", "user clicked stop "+position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

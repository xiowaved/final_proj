package cs160.final_proj_drawer.adapters;

import android.content.Context;
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


    public StopAdapter(Context context, ArrayList<Stop> data)
    {
        this.dataList = data;
        this.context = context;
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
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Stop " + position + " is clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

package cs160.final_proj_drawer.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

/* this adapts the recyclerview for showing
stops in an itinerary vertically. used when viewing a
full itinerary
 */
public class VertStopAdapter extends RecyclerView.Adapter<VertStopAdapter.ViewHolder> {
    private ArrayList<Stop> dataList;
    OnRecyclerCardListener onStopListener;



    public VertStopAdapter(ArrayList<Stop> data, OnRecyclerCardListener onStopListener)
    {
        this.dataList = data;
        this.onStopListener = onStopListener;

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView stopName;
        TextView address;
        TextView description;
        ArrayList<String> photos;
        OnRecyclerCardListener onStopListener;


        public ViewHolder(View itemView, OnRecyclerCardListener onStopListener)
        {
            super(itemView);
            this.stopName = (TextView) itemView.findViewById(R.id.stopName);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.description = (TextView) itemView.findViewById(R.id.description);

            this.onStopListener = onStopListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onStopListener.onCardClick(getAdapterPosition());
            Log.i("Stop Adapter", "stop clicked");
        }
    }

    @Override
    public VertStopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vert_stop, parent, false);
        VertStopAdapter.ViewHolder viewHolder = new VertStopAdapter.ViewHolder(view, onStopListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VertStopAdapter.ViewHolder holder, final int position)
    {
        holder.stopName.setText(dataList.get(position).getName());
        holder.address.setText(dataList.get(position).getLocation());
        holder.description.setText(dataList.get(position).getDescription());

//        holder.itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Log.i("NOTE", "user clicked stop "+position);
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

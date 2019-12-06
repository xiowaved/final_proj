package cs160.final_proj_drawer.adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> {
    private ArrayList<Stop> dataList;
    OnRecyclerCardListener onStopListener;



    public StopAdapter(ArrayList<Stop> data, OnRecyclerCardListener onStopListener)
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


        public ViewHolder(final View itemView, final OnRecyclerCardListener onStopListener)
        {
            super(itemView);
            this.stopName = (TextView) itemView.findViewById(R.id.stopName);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.description = (TextView) itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);

            // set on click listeners for buttons in cardviews
            ImageButton deleteButton = (ImageButton) itemView.findViewById(R.id.deleteIcon);
            deleteButton.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStopListener.onCardClick(getAdapterPosition(), OnRecyclerCardListener.cardAction.DELETE);
                    Log.i("StopAdapter", "clicked delete");
                }
            }));
            ImageButton editButton = (ImageButton) itemView.findViewById(R.id.editIcon);
            editButton.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStopListener.onCardClick(getAdapterPosition(), OnRecyclerCardListener.cardAction.EDIT);
                    Log.i("StopAdapter", "clicked edit" + ""+ getAdapterPosition());
                }
            }));
            this.onStopListener = onStopListener;

        }

        @Override
        public void onClick(View view) {
//            onStopListener.onCardClick(getAdapterPosition());
            Log.i("Stop Adapter", "stop clicked");
        }
    }

    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stop, parent, false);
        StopAdapter.ViewHolder viewHolder = new StopAdapter.ViewHolder(view, onStopListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StopAdapter.ViewHolder holder, final int position)
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

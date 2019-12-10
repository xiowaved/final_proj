package cs160.final_proj_drawer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener.cardAction;

public class HorizItinAdapter extends RecyclerView.Adapter<HorizItinAdapter.ViewHolder> {

    private ArrayList<ItineraryObject> itins;
    private OnRecyclerCardListener onItinListener;
    private cardAction action;


    public HorizItinAdapter( ArrayList<ItineraryObject> itins, OnRecyclerCardListener onItinListener, cardAction action)
    {
        this.action = action;
        this.itins = itins;
        this.onItinListener = onItinListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        //UI
        TextView itineraryName;
        ImageView image;
        OnRecyclerCardListener onItinListener;

        //logic
        ItineraryObject itin;
        cardAction action;

        public ViewHolder(View itemView, final OnRecyclerCardListener onItinListener, cardAction action)
        {
            super(itemView);
            this.action = action;
            this.itineraryName = itemView.findViewById(R.id.text);
            this.image = itemView.findViewById(R.id.bg_img);
            this.onItinListener = onItinListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItinListener.onCardClick(getAdapterPosition(), action);
            //user clicked on this individual card
        }
    }

    @Override
    public HorizItinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horiz_itin, parent, false);
        HorizItinAdapter.ViewHolder viewHolder = new HorizItinAdapter.ViewHolder(view, onItinListener, action);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HorizItinAdapter.ViewHolder holder, final int position)
    {
        holder.itin = itins.get(position);
        holder.itineraryName.setText(itins.get(position).getItineraryName());

        String image = itins.get(position).getCoverPhoto();
        Picasso.get().load(image).into(holder.image);
    }

    @Override
    public int getItemCount()
    {
        return itins.size();
    }
}

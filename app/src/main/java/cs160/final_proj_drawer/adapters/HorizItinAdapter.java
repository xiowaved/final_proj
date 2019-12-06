package cs160.final_proj_drawer.adapters;

import android.util.Log;
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

public class HorizItinAdapter extends RecyclerView.Adapter<HorizItinAdapter.ViewHolder> {

    private ArrayList<ItineraryObject> itins;
    private OnRecyclerCardListener onItinListener;


    public HorizItinAdapter( ArrayList<ItineraryObject> itins, OnRecyclerCardListener onItinListener)
    {
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

        public ViewHolder(View itemView, final OnRecyclerCardListener onItinListener)
        {
            super(itemView);
            this.itineraryName = itemView.findViewById(R.id.text);
            this.image = itemView.findViewById(R.id.bg_img);
            this.onItinListener = onItinListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItinListener.onCardClick(getAdapterPosition(), null);
            Log.i("NOTE", "was a click");
        }
    }

    @Override
    public HorizItinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horiz_itin, parent, false);
        HorizItinAdapter.ViewHolder viewHolder = new HorizItinAdapter.ViewHolder(view, onItinListener);
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

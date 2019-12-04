package cs160.final_proj_drawer.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;

public class ItinAdapter extends RecyclerView.Adapter<ItinAdapter.ViewHolder>
{
    private ArrayList<ItineraryObject> itins;
    private OnRecyclerCardListener onItinListener;


    public ItinAdapter( ArrayList<ItineraryObject> itins, OnRecyclerCardListener onItinListener)
    {
        this.itins = itins;
        this.onItinListener = onItinListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView itineraryName;
        TextView numLikesText;
        ImageView bookmark;
        ImageView cover;
        OnRecyclerCardListener onItinListener;

        public ViewHolder(View itemView, final OnRecyclerCardListener onItinListener)
        {
            super(itemView);
            this.itineraryName = itemView.findViewById(R.id.text);
            this.numLikesText = itemView.findViewById(R.id.numLikes);
            this.bookmark = itemView.findViewById(R.id.bkmark);
            this.cover = itemView.findViewById(R.id.cover_img);
            this.onItinListener = onItinListener;

            itemView.setOnClickListener(this);


            bookmark = itemView.findViewById(R.id.bkmark);
            bookmark.setOnClickListener((new View.OnClickListener() {
                @Override
                //todo make sure the bookmmarking here writes to firebase
                public void onClick(View v) {
                    ItineraryObject thisItin = itins.get(getAdapterPosition());
                    thisItin.setBookmarked(!thisItin.getBookmarked());
                    if (thisItin.getBookmarked()) {
                        bookmark.setImageResource(R.drawable.ic_bookmark_filled);
                    } else {
                        bookmark.setImageResource(R.drawable.ic_bkmark);
                    }
                    Log.i("ItinAdapter", "clicked bookmark");
                }
            }));
        }

        @Override
        public void onClick(View view) {
            onItinListener.onCardClick(getAdapterPosition(), null);
            Log.i("NOTE", "was a click");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_itinerary, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onItinListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItinAdapter.ViewHolder holder, final int position)
    {
        ItineraryObject itinerary = itins.get(position);
        holder.itineraryName.setText(itins.get(position).getItineraryName());
        holder.numLikesText.setText(String.valueOf(itins.get(position).getNumLikes()));
        //holder.itemView.setOnClickListener(holder);
        String image = itins.get(position).getCoverPhoto();
        Picasso.get().load(image).into(holder.cover);
        boolean isBookmarked = itinerary.getBookmarked();
        if (isBookmarked) {
            holder.bookmark.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            holder.bookmark.setImageResource(R.drawable.ic_bkmark);
        }
    }

    @Override
    public int getItemCount()
    {
        return itins.size();
    }

}

package cs160.final_proj_drawer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import cs160.final_proj_drawer.logic.FirebaseFuncs;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.ui.itin.DisplayItinHelperFuncs;

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
        //UI
        TextView itineraryName;
        TextView numLikesText;
        ImageView bookmark;
        ImageView like;
        ImageView cover;
        OnRecyclerCardListener onItinListener;

        //logic
        ItineraryObject itin;

        public ViewHolder(View itemView, final OnRecyclerCardListener onItinListener)
        {
            super(itemView);
            this.itineraryName = itemView.findViewById(R.id.text);
            this.numLikesText = itemView.findViewById(R.id.numLikes);
            this.bookmark = itemView.findViewById(R.id.bkmark);
            this.like = itemView.findViewById(R.id.like);
            this.cover = itemView.findViewById(R.id.cover_img);
            this.onItinListener = onItinListener;

            itemView.setOnClickListener(this);

            this.bookmark.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itin.clickBookmark();
                    FirebaseFuncs.writeSingleItin(itin);
                    DisplayItinHelperFuncs.updateBookmarkView(itin.getBookmarked(), bookmark);
                }
            }));
            this.like.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itin.clickLiked();
                    FirebaseFuncs.writeSingleItin(itin);
                    DisplayItinHelperFuncs.updateLikeView(itin.getLiked(), itin.getNumLikes(), like, numLikesText);
                }
            }));
        }

        @Override
        public void onClick(View view) {
            onItinListener.onCardClick(getAdapterPosition(), null);
            //user clicked on this individual card
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
        holder.itin = itins.get(position);
        holder.itineraryName.setText(itins.get(position).getItineraryName());
        holder.numLikesText.setText(String.valueOf(itins.get(position).getNumLikes()));

        String image = itins.get(position).getCoverPhoto();
        Picasso.get().load(image).into(holder.cover);
        DisplayItinHelperFuncs.updateBookmarkView(holder.itin.getBookmarked(), holder.bookmark);
        DisplayItinHelperFuncs.updateLikeView(holder.itin.getLiked(), holder.itin.getNumLikes(), holder.like, holder.numLikesText);
    }

    @Override
    public int getItemCount()
    {
        return itins.size();
    }

}

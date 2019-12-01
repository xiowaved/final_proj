package cs160.final_proj_drawer.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;

public class ItinAdapter extends RecyclerView.Adapter<ItinAdapter.ViewHolder>
{
    private ArrayList<ItineraryObject> dataList;
    Context context;


    public ItinAdapter(Context context, ArrayList<ItineraryObject> data)
    {
        this.dataList = data;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView itineraryName;
        int numLikes;
        String coverPhoto;
        TextView numLikesText;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.itineraryName = (TextView) itemView.findViewById(R.id.text);
            this.numLikesText = (TextView) itemView.findViewById(R.id.numLikes);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_itinerary, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItinAdapter.ViewHolder holder, final int position)
    {
        holder.itineraryName.setText(dataList.get(position).getItineraryName());
        holder.numLikesText.setText(String.valueOf(dataList.get(position).getNumLikes()));
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                //toDo hand itinerary object
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public interface onItinListener{
        void onItinClick(int position);
    }
}

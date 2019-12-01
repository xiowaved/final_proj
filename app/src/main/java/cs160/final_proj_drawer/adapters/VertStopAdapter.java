package cs160.final_proj_drawer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

/* this adapts the recyclerview for showing
stops in an itinerary vertically. used when viewing a
full itinerary
 */
public class VertStopAdapter extends StopAdapter {
    public VertStopAdapter(ArrayList<Stop> data) {
        super(data);
    }

    @Override
    public VertStopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vert_stop, parent, false);
        StopAdapter.ViewHolder viewHolder = new StopAdapter.ViewHolder(view);
        return viewHolder;
    }
}

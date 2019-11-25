package cs160.final_proj_drawer;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ItinAdapter extends RecyclerView.Adapter<ItinAdapter.ViewHolder>
{
    //        private ArrayList<DataNote> dataList;
    private ArrayList<ItineraryObject> dataList;
    Context context;



//        public Adapter(ArrayList<DataNote> data)
//        {
//            this.dataList = data;
//        }

    public ItinAdapter(Context context, ArrayList<ItineraryObject> data)
    {
        this.dataList = data;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //this is all the stuff contained within your cardview, however you define it
//            TextView textViewText;
//            TextView textViewComment;
//            TextView textViewDate;
        TextView itineraryName;
        int numLikes;
        String coverPhoto;
        String bookmark;
        ImageView like;

        public ViewHolder(View itemView)
        {
            super(itemView);
//                this.textViewText = (TextView) itemView.findViewById(R.id.text);
//                this.textViewComment = (TextView) itemView.findViewById(R.id.comment);
//                this.textViewDate = (TextView) itemView.findViewById(R.id.date);
            this.itineraryName = (TextView) itemView.findViewById(R.id.text);
//            this.like = (ImageView) itemView.findViewById(R.id.like);

        }
    }

    @Override
    public ItinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itinerary_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItinAdapter.ViewHolder holder, final int position)
    {
//            holder.textViewText.setText(dataList.get(position).getText());
//            holder.textViewComment.setText(dataList.get(position).getComment());
//            holder.textViewDate.setText(dataList.get(position).getDate());

        holder.itineraryName.setText(dataList.get(position).getItineraryName());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

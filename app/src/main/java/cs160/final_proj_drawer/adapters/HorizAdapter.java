package cs160.final_proj_drawer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;

public class HorizAdapter extends RecyclerView.Adapter<HorizAdapter.ViewHolder>
{

        private ArrayList<Object> dataList;
        String[] categories = {"food", "music", "hike", "art"};
        int[] drawableIds;


    public HorizAdapter(ArrayList<Object> data)
        {

            this.dataList = data;
            drawableIds = new int[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                String d = dataList.get(i).toString();
                int id = Integer.valueOf(d.substring(d.indexOf(" ") + 1));
                drawableIds[i] = id;
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            //this is all the stuff contained within whatever obj we pass
            TextView catName;
            ImageView coverPhoto;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.catName = (TextView) itemView.findViewById(R.id.text);
                this.coverPhoto = (ImageView) itemView.findViewById(R.id.bg_img);
            }
        }

        @Override
        public HorizAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horiz, parent, false);
            HorizAdapter.ViewHolder viewHolder = new HorizAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HorizAdapter.ViewHolder holder, final int position)
        {
            holder.catName.setText(categories[position]);
            // set image resource to drawable id for the picture
            holder.coverPhoto.setImageResource(drawableIds[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.i("NOTE", "user clicked horizAdapter " + position );
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }



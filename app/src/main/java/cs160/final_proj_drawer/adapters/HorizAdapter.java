package cs160.final_proj_drawer.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;

/*  this recyclerview adapter holds our small
    horizontal recyclerviews
    it just has a DRAWABLE and NAME for each item
 */
public class HorizAdapter extends RecyclerView.Adapter<HorizAdapter.ViewHolder>
{

        private ArrayList<Object> dataList;
        String[] categories = {"food", "music", "hike", "art"};
        int[] drawableIds;
        private OnRecyclerCardListener onCatListener;


    public HorizAdapter(ArrayList<Object> data, OnRecyclerCardListener onCatListener)
        {

            this.dataList = data;
            drawableIds = new int[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                String d = dataList.get(i).toString();
                int id = Integer.valueOf(d.substring(d.indexOf(" ") + 1));
                drawableIds[i] = id;
            }
            this.onCatListener = onCatListener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            //this is all the stuff contained within whatever obj we pass
            TextView catName;
            ImageView coverPhoto;
            OnRecyclerCardListener onCatListener;


            public ViewHolder(View itemView, final OnRecyclerCardListener onCatListener)
            {
                super(itemView);
                this.catName = (TextView) itemView.findViewById(R.id.text);
                this.coverPhoto = (ImageView) itemView.findViewById(R.id.bg_img);
                this.onCatListener = onCatListener;
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                onCatListener.onCardClick(getAdapterPosition(), null);
                Log.i("horiz adapter", "click");
            }
        }

        @Override
        public HorizAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horiz, parent, false);
            HorizAdapter.ViewHolder viewHolder = new HorizAdapter.ViewHolder(view, onCatListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HorizAdapter.ViewHolder holder, final int position)
        {
            holder.catName.setText(categories[position]);
            // set image resource to drawable id for the picture
            holder.coverPhoto.setImageResource(drawableIds[position]);
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }



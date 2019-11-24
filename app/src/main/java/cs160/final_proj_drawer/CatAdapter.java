package cs160.final_proj_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder>
{

        //        private ArrayList<DataNote> dataList;
        private ArrayList<Object> dataList;
        Context context;
        String[] categories = {"food", "hike", "music", "art"};
        int[] drawableIds;

//        public Adapter(ArrayList<DataNote> data)
//        {
//            this.dataList = data;
//        }

    public CatAdapter(Context context, ArrayList<Object> data)
        {

            this.dataList = data;
            drawableIds = new int[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                String d = dataList.get(i).toString();
                int id = Integer.valueOf(d.substring(d.indexOf(" ") + 1));
                drawableIds[i] = id;
                Toast.makeText(context, "" + id, Toast.LENGTH_SHORT).show();
            }
            this.context = context;
//            Toast.makeText(context, "" + dataList, Toast.LENGTH_SHORT).show();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            //this is all the stuff contained within whatever obj we pass
            TextView catName;
//            String coverPhoto;
            ImageView coverPhoto;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.catName = (TextView) itemView.findViewById(R.id.text);
                this.coverPhoto = (ImageView) itemView.findViewById(R.id.bg_img);
            }
        }

        @Override
        public CatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_card, parent, false);

            CatAdapter.ViewHolder viewHolder = new CatAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CatAdapter.ViewHolder holder, final int position)
        {
            holder.catName.setText(categories[position]);
            // set image resource to drawable id for the picture defined by the category name
            holder.coverPhoto.setImageResource(drawableIds[position]);
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



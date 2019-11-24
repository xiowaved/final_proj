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



//        public Adapter(ArrayList<DataNote> data)
//        {
//            this.dataList = data;
//        }

    public CatAdapter(Context context, ArrayList<Object> data)
        {
            this.dataList = data;
            this.context = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            //this is all the stuff contained within whatever obj we pass
            TextView catName;
            String coverPhoto;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.catName = (TextView) itemView.findViewById(R.id.text);

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
            holder.catName.setText("change me");

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



package cs160.final_proj_drawer.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs160.final_proj_drawer.ItineraryObject;
import cs160.final_proj_drawer.R;

public class ProfileFragment extends Fragment {

    //private ProfileViewModel profileViewModel;
//    private TextView mTextViewEmpty;
//    private ProgressBar mProgressBarLoading;
//    private ImageView mImageViewEmpty;
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        mTextViewEmpty = (TextView)view.findViewById(R.id.textViewEmpty);
//        mImageViewEmpty = (ImageView)view.findViewById(R.id.imageViewEmpty);
//        mProgressBarLoading = (ProgressBar)view.findViewById(R.id.progressBarLoading);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //go to json and pull whatever we get from firebase (basically the itineraries)
        //arraylist of itinerary objects
//        ArrayList data = new ArrayList<DataNote>();
//        for (int i = 0; i < DataNoteInformation.id.length; i++)
//        {
//            data.add(
//                    new DataNote
//                            (
//                                    DataNoteInformation.id[i],
//                                    DataNoteInformation.textArray[i],
//                                    DataNoteInformation.dateArray[i]
//                            ));
//        }

        ArrayList itineraries = new ArrayList<ItineraryObject>();
        //make json call, find the length and that is your for loop upper bound
        for (int i = 0; i < 4; i++)
        {
            ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName", 0,
                    "coverPhoto", 1, null, new ArrayList<String>(), new ArrayList<String>());

            itineraries.add(itinerary);
        }

        mListadapter = new ListAdapter(itineraries);
        mRecyclerView.setAdapter(mListadapter);

        return view;
//        profileViewModel =
//                ViewModelProviders.of(this).get(ProfileViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_profile, container, false);
//        final TextView textView = root.findViewById(R.id.text_profile);
//        profileViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
//        private ArrayList<DataNote> dataList;
        private ArrayList<ItineraryObject> dataList;

//        public ListAdapter(ArrayList<DataNote> data)
//        {
//            this.dataList = data;
//        }

        public ListAdapter(ArrayList<ItineraryObject> data)
        {
            this.dataList = data;
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
                this.like = (ImageView) itemView.findViewById(R.id.like);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itinerary_card, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position)
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
                    Toast.makeText(getActivity(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }
}
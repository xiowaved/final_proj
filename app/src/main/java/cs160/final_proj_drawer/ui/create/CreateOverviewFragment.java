package cs160.final_proj_drawer.ui.create;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

import static android.app.Activity.RESULT_OK;


public class CreateOverviewFragment extends Fragment {

    private NavController navController;
    private Button addStopsButton;
    private ImageButton coverPhotoButton;
    private ImageView nimage;
    private String itineraryName;
    private String itineraryLocation;
    private final int RESULT_LOAD_IMG = 1;
    private String coverPhoto = "";
    static StorageReference storage = FirebaseStorage.getInstance().getReference();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_overview, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // get the EditTexts so you can query user's itinerary info inputs
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        final TextView errorMsg = (TextView) getView().findViewById(R.id.errorMsg);
//        coverPhoto = "";
        // set onClick listener for the buttons
        nimage = (ImageView) getView().findViewById(R.id.imageview);
        coverPhotoButton = (ImageButton) getView().findViewById(R.id.coverPhoto);
        coverPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "clicked on plus button",
                        Toast.LENGTH_LONG); toast.show();
                //todo -- Chaitanya this is where you add the code that you want to execute when user
                // clicks on the button to add a cover photo
                switch (v.getId()) {
                    case R.id.coverPhoto:
                        selectimage();
                }
            }



        });



        addStopsButton = (Button) getView().findViewById(R.id.button);
        addStopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryName = itinName.getText().toString();
                itineraryLocation = itinLocation.getText().toString();

                // if user left name, location field, or cover photo blank, do not let them navigate to next page yet
                if (itineraryName.isEmpty() || itineraryLocation.isEmpty() || coverPhoto.isEmpty()) {
                // todo IGNORING NO COVER PHOTO SET FOR NOW TO TEST LATER PARTS OF CREATION PROCESS
                //  use the commented out conditional that checks all 3 fields in final version of app
//                if (itineraryName.isEmpty() || itineraryLocation.isEmpty()) {

                // user did not fill in required fields
                    errorMsg.setText("Fill in all fields to continue.");
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    // pass a partially filled-out itinerary object to the createStops fragment
                    ItineraryObject itinerary = new ItineraryObject("creatorName", itineraryName,
                            0, coverPhoto, itineraryLocation, 0, new ArrayList<Stop>(),
                            new ArrayList<String>(), new ArrayList<String>());
                    bundle.putSerializable("itinerary", itinerary);

//                    bundle.putString("name", itineraryName);
//                    bundle.putString("location", itineraryLocation);
                    navController.navigate(R.id.fragment_create_stops,bundle);
                }
            }
        });

    }



    public void selectimage(){
        //select the specific image from the gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);


    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Random rand = new Random();
        int num = rand.nextInt(9000000);
        final StorageReference storageRef = storage.child("itinerary photos/"+"image" + num);


        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                nimage.setImageBitmap(bitmap);
                nimage.setVisibility(View.VISIBLE);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageData = baos.toByteArray();

                storageRef.putBytes(imageData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String URL = uri.toString();
                                    coverPhoto = URL;
                                }
                            });
                        }
                    }
                });

            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }

        }else {

        }
    }

}

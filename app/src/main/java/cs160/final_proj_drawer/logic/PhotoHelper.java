package cs160.final_proj_drawer.logic;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class PhotoHelper {
    private static PhotoHelper mInstance;
    private RequestQueue mrequestQueue;
    private static Context mcontext;


    private  PhotoHelper(Context context){
        mcontext = context;
        mrequestQueue = getrequestqueue();
    }

    private RequestQueue getrequestqueue(){
        if(mrequestQueue == null){
            mrequestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return mrequestQueue;
    }

    public static synchronized PhotoHelper getmInstance(Context context){
        if(mInstance == null){
            mInstance = new PhotoHelper(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getrequestqueue().add(request);
    }
}

package cs160.final_proj_drawer.adapters;


/* when a fragment extends this interface, it becomes
   a listener that can be passed to a corresponding
   Recyclerview Adapter. This way, our fragments
   can receive and take action based off of clicks.
 */


import androidx.annotation.Nullable;

public interface OnRecyclerCardListener {
    enum cardAction
    {
        EDIT, DELETE, BOOKMARK;
    }

    //if null is given, that means no action was taken
    void onCardClick(int position,@Nullable cardAction action);
}

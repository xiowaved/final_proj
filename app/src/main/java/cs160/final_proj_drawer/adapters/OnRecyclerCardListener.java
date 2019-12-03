package cs160.final_proj_drawer.adapters;


/* when a fragment extends this interface, it becomes
   a listener that can be passed to a corresponding
   Recyclerview Adapter. This way, our fragments
   can receive and take action based off of clicks.
 */

enum cardAction
{
    EDIT, DELETE, BOOKMARK;
}

public interface OnRecyclerCardListener {
    void onCardClick(int position, boolean editMode);
}

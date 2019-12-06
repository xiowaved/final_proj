package cs160.final_proj_drawer.ui.itin;

import android.widget.ImageView;
import android.widget.TextView;

import cs160.final_proj_drawer.R;

public class DisplayItinHelperFuncs {

    static int emptyBookmark = R.drawable.ic_bkmark;
    static int filledBookmark = R.drawable.ic_bookmark_filled;
    static int emptyLike = R.drawable.like;
    //todo change this to a filled like drawable
    static int filledLike = R.drawable.like_filled;

    public static void updateBookmarkView(Boolean isBookmarked, ImageView bkmkView) {
        if (isBookmarked) {
            bkmkView.setImageResource(filledBookmark);
        } else {
            bkmkView.setImageResource(emptyBookmark);
        }
    }

    public static void updateLikeView(Boolean isLiked, ImageView likeView, TextView numLikesView) {
        int numLikes = Integer.valueOf(numLikesView.getText().toString());
        if (isLiked) {
            likeView.setImageResource(filledLike);
            numLikes++;
        } else {
            likeView.setImageResource(emptyLike);
            numLikes--;
        }
        numLikesView.setText("" + numLikes);

    }
}

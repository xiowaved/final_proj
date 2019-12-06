package cs160.final_proj_drawer.ui.itin;

import android.widget.ImageView;
import android.widget.TextView;

import cs160.final_proj_drawer.R;

public class DisplayItinHelperFuncs {

    static int emptyBookmark = R.drawable.ic_bkmark;
    static int filledBookmark = R.drawable.ic_bookmark_filled;
    static int emptyLike = R.drawable.like;
    static int filledLike = R.drawable.like_filled;

    public static void updateBookmarkView(Boolean isBookmarked, ImageView bkmkView) {
        if (isBookmarked) {
            bkmkView.setImageResource(filledBookmark);
        } else {
            bkmkView.setImageResource(emptyBookmark);
        }
    }

    public static void updateLikeView(Boolean isLiked, int numLikes, ImageView likeView, TextView numLikesView) {
        numLikes = Integer.valueOf(numLikesView.getText().toString());
        if (isLiked) {
            likeView.setImageResource(filledLike);
        } else {
            likeView.setImageResource(emptyLike);
        }
        numLikesView.setText("" + numLikes);

    }
}

package cs160.final_proj_drawer.ui.itin;

import android.widget.ImageView;

import cs160.final_proj_drawer.R;

public class DisplayItinHelperFuncs {

    static int emptyBookmark = R.drawable.ic_bkmark;
    static int filledBookmark = R.drawable.ic_bookmark_filled;
    static int emptyLike = R.drawable.like;
    //todo change this to a filled like drawable
    static int filledLike = R.drawable.ic_bookmark_filled;

    static void updateBookmarkView(Boolean isBookmarked, ImageView bkmkView) {
        if (isBookmarked) {
            bkmkView.setImageResource(filledBookmark);
        } else {
            bkmkView.setImageResource(emptyBookmark);
        }
    }

    static void updateLikeView(Boolean isLiked, ImageView likeView) {
        if (isLiked) {
            likeView.setImageResource(filledLike);
        } else {
            likeView.setImageResource(emptyLike);

        }
    }
}

package org.dync.giftlibrary.widget;

import android.animation.AnimatorSet;
import android.view.View;



public interface ICustormAnim {
    AnimatorSet startAnim(GiftFrameLayout giftFrameLayout, View rootView);
    AnimatorSet comboAnim(GiftFrameLayout giftFrameLayout, View rootView);
    AnimatorSet endAnim(GiftFrameLayout giftFrameLayout, View rootView);
}

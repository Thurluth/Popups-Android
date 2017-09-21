package thurluth.popup;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

abstract class Popup {
    RelativeLayout parentLayout;
    RelativeLayout generalLayout;
    LinearLayout messageLayout;

    int pxToDp(int px, DisplayMetrics displayMetrics) {
        float res;

        res = px * displayMetrics.density;
        return (int)res;
    }

    public void setAcceptListener(View.OnClickListener listener) {
        ImageButton accept = (ImageButton) messageLayout.findViewWithTag("Confirm");
        accept.setOnClickListener(listener);
    }

    public void setAcceptColor(int color) {
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        acceptButton.setColorFilter(color);
    }

    public void setAcceptBackgroundColor(int colorNotPressed, int colorPressed) {
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        int[][] states = new int[][] {
                new int[] { -android.R.attr.state_pressed }, // not pressed
                new int[] { android.R.attr.state_pressed }  // pressed
        };
        int[] colors = new int[] {
                colorNotPressed,
                colorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        acceptButton.setBackgroundTintList(background);
    }

    public void setPopupBackgroundColor(int color) {
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setColor(color);
    }

    public void setPopupCornerRadius(int radius) {
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setCornerRadius(radius);
    }

    public void setPopupOutline(int width, int color) {
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setStroke(width, color);
    }

    public void setMessageText(CharSequence messageText) {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        message.setText(messageText);
    }

    public void setMessageTextColor(int color) {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        message.setTextColor(color);
    }

    public int getMessageTextColor() {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        return message.getCurrentTextColor();
    }

    public void setMessageTextSize(int size) {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        message.setTextSize(size);
    }

    Popup(@NonNull RelativeLayout _parentLayout) {
        parentLayout = _parentLayout;
    }

    void disableParentLayout(RelativeLayout parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(false);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                if (layout.getTag() != "Popup Tag")
                    disableParentLayout(layout);
            }
            catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    disableParentLayout(layout);
                }
                catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        disableParentLayout(layout);
                    }
                    catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            disableParentLayout(layout);
                        }
                        catch (Exception ignored){}
                    }
                }
            }
        }
    }

    private void disableParentLayout(LinearLayout parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(false);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                disableParentLayout(layout);
            }
            catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    disableParentLayout(layout);
                }
                catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        disableParentLayout(layout);
                    }
                    catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            disableParentLayout(layout);
                        }
                        catch (Exception ignored){}
                    }
                }
            }
        }
    }

    private void disableParentLayout(ScrollView parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(false);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                disableParentLayout(layout);
            }
            catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    disableParentLayout(layout);
                }
                catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        disableParentLayout(layout);
                    }
                    catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            disableParentLayout(layout);
                        }
                        catch (Exception ignored){}
                    }
                }
            }
        }
    }

    private void disableParentLayout(MyViewPager parentLayout) {
        parentLayout.setPageable(false);
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(false);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                if (layout.getTag() != "Popup Tag")
                    disableParentLayout(layout);
            }
            catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    disableParentLayout(layout);
                }
                catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        disableParentLayout(layout);
                    }
                    catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            disableParentLayout(layout);
                        }
                        catch (Exception ignored){}
                    }
                }
            }
        }
    }

    void enableParentLayout(RelativeLayout parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(true);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                enableParentLayout(layout);
            } catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    enableParentLayout(layout);
                } catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        enableParentLayout(layout);
                    } catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            enableParentLayout(layout);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    private void enableParentLayout(LinearLayout parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(true);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                enableParentLayout(layout);
            } catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    enableParentLayout(layout);
                } catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        enableParentLayout(layout);
                    } catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            enableParentLayout(layout);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    private void enableParentLayout(ScrollView parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(true);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                enableParentLayout(layout);
            }
            catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    enableParentLayout(layout);
                }
                catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        enableParentLayout(layout);
                    } catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            enableParentLayout(layout);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    private void enableParentLayout(MyViewPager parentLayout) {
        parentLayout.setPageable(true);
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(true);
            try {
                RelativeLayout layout = (RelativeLayout) parentLayout.getChildAt(i);
                enableParentLayout(layout);
            } catch (Exception e) {
                try {
                    LinearLayout layout = (LinearLayout) parentLayout.getChildAt(i);
                    enableParentLayout(layout);
                } catch (Exception ex) {
                    try {
                        ScrollView layout = (ScrollView) parentLayout.getChildAt(i);
                        enableParentLayout(layout);
                    } catch (Exception exe) {
                        try {
                            MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                            enableParentLayout(layout);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    void fadeOutAnimation() {
        Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(300);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                generalLayout.setVisibility(View.GONE);
                generalLayout.setAlpha(0.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        generalLayout.startAnimation(fadeOut);
    }

    void fadeInAnimation() {
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(300);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                generalLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                generalLayout.setAlpha(1.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        generalLayout.startAnimation(fadeIn);
    }

    public void display() {
        parentLayout.addView(generalLayout);
        disableParentLayout(parentLayout);
        fadeInAnimation();
    }

    public void closePopup() {
        enableParentLayout(parentLayout);
        fadeOutAnimation();
        parentLayout.removeView(messageLayout);
    }

    View.OnClickListener defaultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closePopup();
        }
    };
}

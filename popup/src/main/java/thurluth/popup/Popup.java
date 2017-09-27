package thurluth.popup;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

abstract class Popup
{
    ViewGroup parentLayout;
    RelativeLayout generalLayout;
    LinearLayout messageLayout;

    int acceptColor = Color.WHITE;
    int acceptColorNotPressed = Color.parseColor("#60C5FF");
    int acceptColorPressed = Color.parseColor("#FF51A6D7");
    int refuseColor = Color.WHITE;
    int refuseColorNotPressed = Color.parseColor("#FF4141");
    int refuseColorPressed = Color.parseColor("#FFDE3939");

    int popupOutlineWidth = 2;
    int popupOutlineColor = Color.parseColor("#34000000");

    int pxToDp(int px, DisplayMetrics displayMetrics)
    {
        float res;

        res = px * displayMetrics.density;
        return (int) res;
    }

    public Popup setPopupBackgroundColor(int color)
    {
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setColor(color);
        return this;
    }

    public void setPopupCornerRadius(int radius)
    {
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setCornerRadius(radius);
    }

    public void setPopupOutlineWidth(int width)
    {
        popupOutlineWidth = width;
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setStroke(popupOutlineWidth, popupOutlineColor);
    }

    public void setPopupOutlineColor(int color)
    {
        popupOutlineColor = color;
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setStroke(popupOutlineWidth, popupOutlineColor);
    }

    public void setPopupOutline(int width, int color)
    {
        popupOutlineWidth = width;
        popupOutlineColor = color;
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setStroke(popupOutlineWidth, popupOutlineColor);
    }

    public void setMessageText(CharSequence messageText)
    {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        message.setText(messageText);
    }

    public void setMessageTextColor(int color)
    {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        message.setTextColor(color);
    }

    public int getMessageTextColor()
    {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        return message.getCurrentTextColor();
    }

    public void setMessageTextSize(int size)
    {
        TextView message = (TextView) messageLayout.findViewWithTag("Message");
        message.setTextSize(size);
    }

    Popup(@NonNull View _parentLayout)
    {
        parentLayout = (ViewGroup) _parentLayout;
    }

    void disableParentLayout(ViewGroup parentLayout)
    {
        for (int i = 0; i < parentLayout.getChildCount(); i++)
        {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(false);
            try
            {
                try
                {
                    MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                    layout.setPageable(false);
                }
                catch (Exception ignored)
                {
                }
                try
                {
                    MyScrollView layout = (MyScrollView) parentLayout.getChildAt(i);
                    layout.setScrollable(false);
                }
                catch (Exception ignored)
                {
                }
                ViewGroup layout = (ViewGroup) parentLayout.getChildAt(i);
                if (layout.getTag() != "Popup Tag")
                    disableParentLayout(layout);
            }
            catch (Exception ignored)
            {
            }
        }
    }

    void enableParentLayout(ViewGroup parentLayout)
    {
        for (int i = 0; i < parentLayout.getChildCount(); i++)
        {
            View child = parentLayout.getChildAt(i);
            child.setEnabled(true);
            try
            {
                try
                {
                    MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                    layout.setPageable(true);
                }
                catch (Exception ignored)
                {
                }
                try
                {
                    MyScrollView layout = (MyScrollView) parentLayout.getChildAt(i);
                    layout.setScrollable(true);
                }
                catch (Exception ignored)
                {
                }
                ViewGroup layout = (ViewGroup) parentLayout.getChildAt(i);
                enableParentLayout(layout);
            }
            catch (Exception ignored)
            {
            }
        }
    }

    void fadeOutAnimation()
    {
        Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(300);
        generalLayout.startAnimation(fadeOut);
    }

    void fadeInAnimation()
    {
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(300);
        generalLayout.startAnimation(fadeIn);
    }

    public void display()
    {
        parentLayout.addView(generalLayout);
        fadeInAnimation();
        disableParentLayout(parentLayout);
    }

    public void closePopup()
    {
        enableParentLayout(parentLayout);
        fadeOutAnimation();
        parentLayout.removeView(generalLayout);
    }

    View.OnClickListener defaultListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            closePopup();
        }
    };
}

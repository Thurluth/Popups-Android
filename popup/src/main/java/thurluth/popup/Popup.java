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

    public void setPopupBackgroundColor(int color)
    {
        GradientDrawable prevBackground = (GradientDrawable) messageLayout.getBackground();
        prevBackground.setColor(color);
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
                ViewGroup layout = (ViewGroup) parentLayout.getChildAt(i);
                if (layout.getTag() != "Popup Tag")
                    disableParentLayout(layout);
            } catch (Exception e)
            {
                try
                {
                    MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                    layout.setPageable(false);
                    disableParentLayout(layout);
                } catch (Exception ignored)
                {
                }
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
                ViewGroup layout = (ViewGroup) parentLayout.getChildAt(i);
                enableParentLayout(layout);
            } catch (Exception e)
            {
                try
                {
                    MyViewPager layout = (MyViewPager) parentLayout.getChildAt(i);
                    layout.setPageable(true);
                    enableParentLayout(layout);
                } catch (Exception ignored)
                {
                }
            }
        }
    }

    void fadeOutAnimation()
    {
        Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(300);
        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                generalLayout.setVisibility(View.GONE);
                generalLayout.setAlpha(0.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        });
        generalLayout.startAnimation(fadeOut);
    }

    void fadeInAnimation()
    {
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(300);
        fadeIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                generalLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                generalLayout.setAlpha(1.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        });
        generalLayout.startAnimation(fadeIn);
    }

    public void display()
    {
        parentLayout.addView(generalLayout);
        disableParentLayout(parentLayout);
        fadeInAnimation();
    }

    public void closePopup()
    {
        enableParentLayout(parentLayout);
        fadeOutAnimation();
        parentLayout.removeView(messageLayout);
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

package thurluth.popup;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopupMessage extends Popup
{

    private void createLayout(Context context, Display display, DisplayMetrics displayMetrics)
    {
        int colorPopup = Color.parseColor("#f5f5f5");
        int popupOutlineColor = Color.parseColor("#34000000");
        generalLayout = new RelativeLayout(context);
        messageLayout = new LinearLayout(context);
        Point screenSize = new Point();

        //          SET GENERAL POPUP LAYOUT
        RelativeLayout.LayoutParams generalLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        generalLayout.setLayoutParams(generalLayoutParams);
        generalLayout.setGravity(Gravity.CENTER);
        generalLayout.setTag("Popup Tag");

        //          SET LAYOUT OF POPUP
        display.getSize(screenSize);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (screenSize.x * (70f / 100f)),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        GradientDrawable popupBackground = new GradientDrawable();
        popupBackground.setColor(colorPopup);
        popupBackground.setCornerRadius(20);
        popupBackground.setStroke(this.popupOutlineWidth, this.popupOutlineColor);
        messageLayout.setBackground(popupBackground);
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        messageLayout.setGravity(Gravity.CENTER);
        messageLayout.setLayoutParams(layoutParams);

        //          SET MESSAGE TEXT VIEW
        TextView message = new TextView(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pxToDp(5, displayMetrics), pxToDp(10, displayMetrics), pxToDp(5, displayMetrics), 0);
        message.setText("Text");
        message.setLayoutParams(layoutParams);
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        message.setTextSize(20);
        message.setTag("Message");

        //          SET ACCEPT BUTTON
        int color = Color.parseColor("#60C5FF");
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                color,
                Color.RED,
                Color.GREEN,
                Color.BLUE
        };
        ColorStateList background = new ColorStateList(states, colors);
        ImageButton acceptButton = new ImageButton(context);
        layoutParams = new LinearLayout.LayoutParams(messageLayout.getLayoutParams().width / 4,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        acceptButton.setColorFilter(Color.WHITE);
        acceptButton.setLayoutParams(layoutParams);
        acceptButton.setImageResource(R.drawable.icon_confirm);
        acceptButton.setBackgroundTintList(background);
        acceptButton.setOnClickListener(defaultListener);
        acceptButton.setTag("Confirm");

        messageLayout.addView(message);
        messageLayout.addView(acceptButton);

        generalLayout.addView(messageLayout);
    }

    //      ACCEPT BUTTON SETTINGS

    public void setAcceptListener(View.OnClickListener listener)
    {
        ImageButton accept = (ImageButton) messageLayout.findViewWithTag("Confirm");
        accept.setOnClickListener(listener);
    }

    public int getAcceptColor()
    {
        return (this.acceptColor);
    }

    public void setAcceptColor(int color)
    {
        this.acceptColor = color;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        acceptButton.setColorFilter(this.acceptColor);
    }

    public int getAcceptBackgroundColorNotPressed()
    {
        return this.acceptColorNotPressed;
    }

    public int getAcceptBackgroundColorPressed()
    {
        return this.acceptColorPressed;
    }

    public void setAcceptBackgroundColorNotPressed(int colorNotPressed)
    {
        this.acceptColorNotPressed = colorNotPressed;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // not pressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        acceptButton.setBackgroundTintList(background);
    }

    public void setAcceptBackgroundColorPressed(int colorPressed)
    {
        this.acceptColorPressed = colorPressed;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // not pressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        acceptButton.setBackgroundTintList(background);
    }

    public void setAcceptBackgroundColor(int colorNotPressed, int colorPressed)
    {
        this.acceptColorNotPressed = colorNotPressed;
        this.acceptColorPressed = colorPressed;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // not pressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        acceptButton.setBackgroundTintList(background);
    }

    public PopupMessage(@NonNull Activity activity)
    {
        super(activity.getWindow().getDecorView().getRootView());
        Context context = activity.getApplicationContext();
        createLayout(context, activity.getWindowManager().getDefaultDisplay(), context.getResources().getDisplayMetrics());
    }
}
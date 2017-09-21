package thurluth.popup;

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

public class PopupBoolean extends Popup {

    private void createLayout(Context context, Display display, DisplayMetrics displayMetrics) {
        int colorPopup = Color.parseColor("#f5f5f5");
        int colorPopupOutline = Color.parseColor("#34000000");
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(screenSize.x * (70f / 100f)),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        GradientDrawable popupBackground = new GradientDrawable();
        popupBackground.setColor(colorPopup);
        popupBackground.setCornerRadius(20);
        popupBackground.setStroke(2, colorPopupOutline);
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

        //          SET BUTTONS LAYOUT
        LinearLayout buttonLayout = new LinearLayout(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayout.setLayoutParams(layoutParams);
        buttonLayout.setGravity(Gravity.CENTER);

        //          SET ACCEPT BUTTON
        int color = Color.parseColor("#60C5FF");
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[] {
                color,
                Color.RED,
                Color.GREEN,
                Color.BLUE
        };
        ColorStateList background = new ColorStateList(states, colors);
        ImageButton acceptButton = new ImageButton(context);
        layoutParams = new LinearLayout.LayoutParams(messageLayout.getLayoutParams().width / 4,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, messageLayout.getLayoutParams().width / 8, 0);
        acceptButton.setColorFilter(Color.WHITE);
        acceptButton.setLayoutParams(layoutParams);
        acceptButton.setImageResource(R.drawable.icon_confirm);
        acceptButton.setBackgroundTintList(background);
        acceptButton.setOnClickListener(defaultListener);
        acceptButton.setTag("Confirm");
        buttonLayout.addView(acceptButton);

        //          SET CANCEL BUTTON
        color = Color.parseColor("#ff4141");
        states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };
        colors = new int[] {
                color,
                Color.RED,
                Color.GREEN,
                Color.BLUE
        };
        background = new ColorStateList(states, colors);
        ImageButton cancelButton = new ImageButton(context);
        layoutParams = new LinearLayout.LayoutParams(messageLayout.getLayoutParams().width / 4,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cancelButton.setColorFilter(Color.WHITE);
        cancelButton.setBackgroundTintList(background);
        cancelButton.setLayoutParams(layoutParams);
        cancelButton.setImageResource(R.drawable.icon_cancel);
        cancelButton.setOnClickListener(defaultListener);
        cancelButton.setTag("Cancel");
        buttonLayout.addView(cancelButton);

        messageLayout.addView(message);
        messageLayout.addView(buttonLayout);

        generalLayout.addView(messageLayout);
    }

    public void setCancelColor(int color) {
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        cancelButton.setColorFilter(color);
    }

    public void setCancelBackgroundColor(int colorNotPressed, int colorPressed) {
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        int[][] states = new int[][] {
                new int[] { -android.R.attr.state_pressed }, // not pressed
                new int[] { android.R.attr.state_pressed }  // pressed
        };
        int[] colors = new int[] {
                colorNotPressed,
                colorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        cancelButton.setBackgroundTintList(background);
    }

    public void setRefuseListener(View.OnClickListener listener) {
        ImageButton refuse = (ImageButton) messageLayout.findViewWithTag("Cancel");
        refuse.setOnClickListener(listener);
    }

    public PopupBoolean(@NonNull RelativeLayout _parentLayout, @NonNull Display display) {
        super(_parentLayout);
        Context context = _parentLayout.getContext();
        createLayout(context, display, context.getResources().getDisplayMetrics());
    }
}
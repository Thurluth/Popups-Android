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

public class PopupChoice extends Popup {
    private Context context;
    private DisplayMetrics displayMetrics;
    private LinearLayout buttonLayout;
    private LinearLayout choicesLayout;

    private void createLayout(Display display) {
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
        int popupWidth = (int)(screenSize.x * (70f / 100f));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popupWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        GradientDrawable popupBackground = new GradientDrawable();
        popupBackground.setColor(colorPopup);
        popupBackground.setCornerRadius(20);
        popupBackground.setStroke(2, colorPopupOutline);
        messageLayout.setBackground(popupBackground);
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        messageLayout.setGravity(Gravity.CENTER);
        messageLayout.setLayoutParams(layoutParams);
        messageLayout.setFocusable(true);
        messageLayout.setFocusableInTouchMode(true);

        //          SET LAYOUT OF CHOICES
        layoutParams = new LinearLayout.LayoutParams(popupWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pxToDp(10, displayMetrics), pxToDp(10, displayMetrics),
                pxToDp(10, displayMetrics), pxToDp(10, displayMetrics));
        choicesLayout = new LinearLayout(context);
        choicesLayout.setOrientation(LinearLayout.VERTICAL);
        choicesLayout.setGravity(Gravity.CENTER);
        choicesLayout.setLayoutParams(layoutParams);

        //          SET MESSAGE TEXT VIEW
        final TextView message = new TextView(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pxToDp(5, displayMetrics), pxToDp(10, displayMetrics), pxToDp(5, displayMetrics), 0);
        message.setText("Text");
        message.setLayoutParams(layoutParams);
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        message.setTextSize(24);
        message.setTag("Message");

        //          SET BUTTONS LAYOUT
        buttonLayout = new LinearLayout(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayout.setLayoutParams(layoutParams);
        buttonLayout.setGravity(Gravity.CENTER);

        //          SET CANCEL BUTTON
        int color = Color.parseColor("#ff4141");
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

    public void addChoice(String text, View.OnClickListener listener) {
        int colorChoiceSeparator = Color.parseColor("#34000000");
        final TextView choice = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pxToDp(5, displayMetrics), pxToDp(5, displayMetrics),
                pxToDp(5, displayMetrics), pxToDp(5, displayMetrics));
        choice.setLayoutParams(layoutParams);
        choice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        choice.setTextSize(20);
        choice.setText(text);
        choice.setTag(text);
        choice.setOnClickListener(listener);

        if (choicesLayout.getChildCount() != 0) {
            View v = new View(context);
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
            layoutParams.setMargins(pxToDp(50, displayMetrics), 0, pxToDp(50, displayMetrics), 0);
            v.setLayoutParams(layoutParams);
            v.setBackgroundColor(colorChoiceSeparator);

            choicesLayout.addView(v);
        }

        choicesLayout.addView(choice);
    }

    public void endChoice() {
        messageLayout.addView(choicesLayout);
        messageLayout.addView(buttonLayout);
    }

    public PopupChoice(@NonNull RelativeLayout _parentLayout, @NonNull Display display) {
        super(_parentLayout);
        context = _parentLayout.getContext();
        displayMetrics = context.getResources().getDisplayMetrics();
        createLayout(display);
    }

    @Override
    public void closePopup() {
        enableParentLayout(parentLayout);
        fadeOutAnimation();
        parentLayout.removeView(messageLayout);
    }

    @Override
    public void display() {
        parentLayout.addView(generalLayout);
        disableParentLayout(parentLayout);
        fadeInAnimation();
    }
}
package thurluth.popup;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopupInputInt extends Popup {

    private MyEditText input;

    private void createLayout(final Context context, Display display, DisplayMetrics displayMetrics) {
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
        int popupWidth = (int)(screenSize.x * (70f / 100f));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popupWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        GradientDrawable popupBackground = new GradientDrawable();
        popupBackground.setColor(colorPopup);
        popupBackground.setCornerRadius(20);
        popupBackground.setStroke(this.popupOutlineWidth, this.popupOutlineColor);
        messageLayout.setBackground(popupBackground);
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        messageLayout.setGravity(Gravity.CENTER);
        messageLayout.setLayoutParams(layoutParams);
        messageLayout.setFocusable(true);
        messageLayout.setFocusableInTouchMode(true);

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

        //          SET INPUT FIELD
        layoutParams = new LinearLayout.LayoutParams((int)(popupWidth / 2.5f),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pxToDp(5, displayMetrics), 0, pxToDp(5, displayMetrics), pxToDp(5, displayMetrics));
        input = new MyEditText(context);
        input.setLayoutParams(layoutParams);
        input.setTextSize(20);
        input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        InputFilter[] inputFilter = new InputFilter[1];
        inputFilter[0] = new InputFilter.LengthFilter(9);
        input.setTag("Input");
        input.setFilters(inputFilter);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from editText
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(input.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    input.clearFocus();
                }
                return false;
            }
        });

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
        messageLayout.addView(input);
        messageLayout.addView(buttonLayout);

        generalLayout.addView(messageLayout);
    }

    public void setDefaultInput(CharSequence defaultValue) {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("Input");
        input.setText(defaultValue);
    }

    //      ACCEPT BUTTON SETTINGS

    public void setAcceptListener(View.OnClickListener listener) {
        ImageButton accept = (ImageButton) messageLayout.findViewWithTag("Confirm");
        accept.setOnClickListener(listener);
    }

    public int getAcceptColor() {
        return (this.acceptColor);
    }

    public void setAcceptColor(int color) {
        this.acceptColor = color;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        acceptButton.setColorFilter(this.acceptColor);
    }

    public int getAcceptBackgroundColorNotPressed() {
        return this.acceptColorNotPressed;
    }

    public int getAcceptBackgroundColorPressed() {
        return this.acceptColorPressed;
    }

    public void setAcceptBackgroundColorNotPressed(int colorNotPressed) {
        this.acceptColorNotPressed = colorNotPressed;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        int[][] states = new int[][] {
                new int[] { -android.R.attr.state_pressed }, // not pressed
                new int[] { android.R.attr.state_pressed }  // pressed
        };
        int[] colors = new int[] {
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        acceptButton.setBackgroundTintList(background);
    }

    public void setAcceptBackgroundColorPressed(int colorPressed) {
        this.acceptColorPressed = colorPressed;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        int[][] states = new int[][] {
                new int[] { -android.R.attr.state_pressed }, // not pressed
                new int[] { android.R.attr.state_pressed }  // pressed
        };
        int[] colors = new int[] {
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        acceptButton.setBackgroundTintList(background);
    }

    public void setAcceptBackgroundColor(int colorNotPressed, int colorPressed) {
        this.acceptColorNotPressed = colorNotPressed;
        this.acceptColorPressed = colorPressed;
        ImageButton acceptButton = (ImageButton) messageLayout.findViewWithTag("Confirm");
        int[][] states = new int[][] {
                new int[] { -android.R.attr.state_pressed }, // not pressed
                new int[] { android.R.attr.state_pressed }  // pressed
        };
        int[] colors = new int[] {
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        acceptButton.setBackgroundTintList(background);
    }

    //      CANCEL BUTTON SETTINGS

    public void setCancelListener(View.OnClickListener listener) {
        ImageButton refuse = (ImageButton) messageLayout.findViewWithTag("Cancel");
        refuse.setOnClickListener(listener);
    }

    public int getCancelColor() {
        return (this.refuseColor);
    }

    public void setCancelColor(int color) {
        this.refuseColor = color;
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        cancelButton.setColorFilter(this.refuseColor);
    }

    public int getCancelBackgroundColorNotPressed() {
        return this.refuseColorNotPressed;
    }

    public int getCancelBackgroundColorPressed() {
        return this.refuseColorPressed;
    }

    public void setCancelBackgroundColorNotPressed(int colorNotPressed) {
        this.refuseColorPressed = colorNotPressed;
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        int[][] states = new int[][] {
                new int[] { -android.R.attr.state_pressed }, // not pressed
                new int[] { android.R.attr.state_pressed }  // pressed
        };
        int[] colors = new int[] {
                this.refuseColorNotPressed,
                this.refuseColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        cancelButton.setBackgroundTintList(background);
    }

    public void setCancelBackgroundColorPressed(int colorPressed) {
        this.refuseColorPressed = colorPressed;
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        int[][] states = new int[][] {
                new int[] { -android.R.attr.state_pressed }, // not pressed
                new int[] { android.R.attr.state_pressed }  // pressed
        };
        int[] colors = new int[] {
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        cancelButton.setBackgroundTintList(background);
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

    public PopupInputInt(@NonNull RelativeLayout _parentLayout, @NonNull Display display) {
        super(_parentLayout);
        Context context = _parentLayout.getContext();
        createLayout(context, display, context.getResources().getDisplayMetrics());
    }

    @Override
    public void closePopup() {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("Input");
        input.setEnabled(false);
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

    public int getValue() {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("Input");
        if (input.getText().toString().matches(""))
            return 0;
        return Integer.parseInt(input.getText().toString());
    }

    public CharSequence getValueStr() {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("Input");
        if (input.getText().toString().matches(""))
            return "0";
        return input.getText();
    }
}
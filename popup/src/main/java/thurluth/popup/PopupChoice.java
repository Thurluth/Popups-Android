package thurluth.popup;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PopupChoice extends Popup
{

    public interface PopupListener
    {
        String[] setChoices();

        int setDisplayableChoices();

        void onSelected(String choice);

        void onCancel();
    }

    private int displayableChoices = 3;
    private Context context;
    private PopupChoice popup = this;
    private LinearLayout buttonLayout;
    private ScrollView choicesLayout;
    private LinearLayout choicesContent;
    private String value;
    private PopupListener listener;
    private Point screenSize = new Point();

    private void createLayout(Display display)
    {
        int colorPopup = Color.parseColor("#f5f5f5");
        generalLayout = new RelativeLayout(context);
        messageLayout = new LinearLayout(context);

        //          SET GENERAL POPUP LAYOUT
        RelativeLayout.LayoutParams generalLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        generalLayout.setLayoutParams(generalLayoutParams);
        generalLayout.setGravity(Gravity.CENTER);
        generalLayout.setTag("Popup Tag");

        //          SET LAYOUT OF POPUP
        display.getSize(screenSize);
        int popupWidth = (int) (screenSize.x * (70f / 100f));
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

        //          SET LAYOUT OF CHOICES
        layoutParams = new LinearLayout.LayoutParams(popupWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        choicesContent = new LinearLayout(context);
        choicesContent.setOrientation(LinearLayout.VERTICAL);
        choicesContent.setGravity(Gravity.CENTER);
        choicesContent.setLayoutParams(layoutParams);
        choicesLayout = new ScrollView(context);
        int choiceHeight = 20 + dpToPx(5) * 2 + dpToPx(10) * 2;
        layoutParams = new LinearLayout.LayoutParams(popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(10), dpToPx(10),
                dpToPx(10), dpToPx(10));
        choicesLayout.setLayoutParams(layoutParams);
        choicesLayout.addView(choicesContent);


        //          SET MESSAGE TEXT VIEW
        final TextView message = new TextView(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(5), dpToPx(10), dpToPx(5), 0);
        message.setText(R.string.default_message);
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
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // unpressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                refuseColorNotPressed,
                refuseColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        ImageButton cancelButton = new ImageButton(context);
        layoutParams = new LinearLayout.LayoutParams(messageLayout.getLayoutParams().width / 4,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cancelButton.setColorFilter(Color.WHITE);
        cancelButton.setBackgroundTintList(background);
        cancelButton.setLayoutParams(layoutParams);
        cancelButton.setImageResource(R.drawable.icon_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closePopup();
                listener.onCancel();
            }
        });
        cancelButton.setTag("Cancel");
        buttonLayout.addView(cancelButton);

        messageLayout.addView(message);

        generalLayout.addView(messageLayout);
    }

    //      CANCEL BUTTON SETTINGS

    public int getCancelColor()
    {
        return (this.refuseColor);
    }

    public void setCancelColor(int color)
    {
        this.refuseColor = color;
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        cancelButton.setColorFilter(this.refuseColor);
    }

    public int getCancelBackgroundColorNotPressed()
    {
        return this.refuseColorNotPressed;
    }

    public int getCancelBackgroundColorPressed()
    {
        return this.refuseColorPressed;
    }

    public void setCancelBackgroundColorNotPressed(int colorNotPressed)
    {
        this.refuseColorPressed = colorNotPressed;
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // not pressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                this.refuseColorNotPressed,
                this.refuseColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        cancelButton.setBackgroundTintList(background);
    }

    public void setCancelBackgroundColorPressed(int colorPressed)
    {
        this.refuseColorPressed = colorPressed;
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // not pressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                this.acceptColorNotPressed,
                this.acceptColorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        cancelButton.setBackgroundTintList(background);
    }

    public void setCancelBackgroundColor(int colorNotPressed, int colorPressed)
    {
        ImageButton cancelButton = (ImageButton) messageLayout.findViewWithTag("Cancel");
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // not pressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                colorNotPressed,
                colorPressed
        };
        ColorStateList background = new ColorStateList(states, colors);
        cancelButton.setBackgroundTintList(background);
    }

    //      CHOICES SETTINGS

    private void addChoice(String text)
    {
        int colorChoiceSeparator = Color.parseColor("#34000000");
        final TextView choice = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        choice.setLayoutParams(layoutParams);
        choice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        choice.setTextSize(20);
        choice.setText(text);
        choice.setTag(text);
        choice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                popup.closePopup();
                value = view.getTag().toString();
                popup.listener.onSelected(value);
            }
        });

        if (choicesContent.getChildCount() != 0)
        {
            View v = new View(context);
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
            layoutParams.setMargins(dpToPx(50), 0, dpToPx(50), 0);
            v.setLayoutParams(layoutParams);
            v.setBackgroundColor(colorChoiceSeparator);

            choicesContent.addView(v);
            if (choicesContent.getChildCount() > displayableChoices + 2)
            {
                int popupWidth = (int) (screenSize.x * (70f / 100f));
                int choiceHeight = 20 + dpToPx(5) * 2 + dpToPx(10) * 2;
                layoutParams = new LinearLayout.LayoutParams(popupWidth, displayableChoices * choiceHeight);
                layoutParams.setMargins(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
                choicesLayout.setLayoutParams(layoutParams);
            }
        }

        choicesContent.addView(choice);
    }

    private void endChoice()
    {
        messageLayout.addView(choicesLayout);
        messageLayout.addView(buttonLayout);
    }

    public PopupChoice(@NonNull Activity activity, PopupListener listener)
    {
        super(activity.getWindow().getDecorView().getRootView());
        this.listener = listener;
        this.displayableChoices = this.listener.setDisplayableChoices();
        if (this.displayableChoices <= 0)
            this.displayableChoices = 3;
        context = activity.getApplicationContext();
        createLayout(activity.getWindowManager().getDefaultDisplay());
        String[] choicesList = this.listener.setChoices();
        for (String choice : choicesList)
            this.addChoice(choice);
        endChoice();
    }
}
package thurluth.popup;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopupLogin extends Popup
{

    public interface PopupListener
    {
        void onConfirm(String login, String password);

        void onCancel();

        void onForgot();

        void onRegister();
    }

    private PopupListener listener;
    private Context context;

    private float errorTextSize = 18;
    private int errorTextColor = Color.RED;
    private boolean errorTextItalic = true;

    private void createLayout(final Context context, Display display)
    {
        this.context = context;

        int colorPopup = Color.parseColor("#f5f5f5");
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

        //          SET MESSAGE TEXT VIEW
        final TextView message = new TextView(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(5), dpToPx(10), dpToPx(5), 0);
        message.setText(R.string.default_message);
        message.setLayoutParams(layoutParams);
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        message.setTextSize(24);
        message.setTag("Message");

        //          SET LOGIN INPUT FIELD
        layoutParams = new LinearLayout.LayoutParams((int) (popupWidth / 1.5f),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(5), 0, dpToPx(10));
        final MyEditText loginInput = new MyEditText(context);
        loginInput.setLayoutParams(layoutParams);
        loginInput.setTextColor(Color.BLACK);
        loginInput.setTextSize(20);
        loginInput.setInputType(InputType.TYPE_CLASS_TEXT);
        loginInput.setTag("LoginInput");
        loginInput.setPrefix("Login :");
        loginInput.setFocusable(true);
        loginInput.setFocusableInTouchMode(true);
        loginInput.setHandlesColors(Color.parseColor("#60C5FF"));
        loginInput.setCursorColor(Color.parseColor("#60C5FF"));
        loginInput.setHighlightColor(Color.parseColor("#A260C5FF"));
        loginInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        loginInput.setSelectAllOnFocus(true);
        generalLayout.addView(loginInput.getFocusNothing());

        //          SET PASSWORD INPUT FIELD
        layoutParams = new LinearLayout.LayoutParams((int) (popupWidth / 1.5f),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(5), 0, dpToPx(10));
        final MyEditText passwordInput = new MyEditText(context);
        passwordInput.setLayoutParams(layoutParams);
        passwordInput.setTextColor(Color.BLACK);
        passwordInput.setTextSize(20);
        passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordInput.setTag("PasswordInput");
        passwordInput.setPrefix("Password :");
        passwordInput.setFocusable(true);
        passwordInput.setFocusableInTouchMode(true);
        passwordInput.setHandlesColors(Color.parseColor("#60C5FF"));
        passwordInput.setCursorColor(Color.parseColor("#60C5FF"));
        passwordInput.setHighlightColor(Color.parseColor("#A260C5FF"));
        passwordInput.setImeOptions(EditorInfo.IME_ACTION_GO);
        passwordInput.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                passwordInput.getFocusNothing().requestFocus();
                listener.onConfirm(getLoginValue(), getPasswordValue());
                return false;
            }
        });
        passwordInput.setSelectAllOnFocus(true);
        generalLayout.addView(passwordInput.getFocusNothing());


        //          SET BUTTONS LAYOUT
        LinearLayout buttonLayout = new LinearLayout(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayout.setLayoutParams(layoutParams);
        buttonLayout.setGravity(Gravity.CENTER);

        //          SET ACCEPT BUTTON
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // unpressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[]{
                acceptColorNotPressed,
                acceptColorPressed
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
        acceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.onConfirm(getLoginValue(), getPasswordValue());
            }
        });
        acceptButton.setTag("Confirm");
        buttonLayout.addView(acceptButton);

        //          SET CANCEL BUTTON
        states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // unpressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };
        colors = new int[]{
                refuseColorNotPressed,
                refuseColorPressed
        };
        background = new ColorStateList(states, colors);
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
                final LinearLayout error = (LinearLayout) messageLayout.findViewWithTag("Error");
                error.removeAllViews();
                listener.onCancel();
            }
        });
        cancelButton.setTag("Cancel");
        buttonLayout.addView(cancelButton);

        //          SET ERROR MESSAGE LAYOUT
        LinearLayout error = new LinearLayout(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(20), dpToPx(10), dpToPx(20), dpToPx(10));
        error.setLayoutParams(layoutParams);
        error.setGravity(Gravity.CENTER);
        error.setTag("Error");


        //          SET IDENTIFICATION PROBLEMS LAYOUT
        LinearLayout problemLayout = new LinearLayout(context);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
        problemLayout.setLayoutParams(layoutParams);
        problemLayout.setGravity(Gravity.CENTER);

        //          SET FORGOT PASSWORD
        TextView forgot = new TextView(context);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        forgot.setLayoutParams(layoutParams);
        forgot.setText(R.string.forgot_password);
        forgot.setTextSize(16);
        forgot.setTextColor(Color.parseColor("#FF4FA0CF"));
        forgot.setGravity(Gravity.CENTER);
        forgot.setPaintFlags(forgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closePopup();
                listener.onForgot();
            }
        });
        forgot.setTag("Forgot");
        problemLayout.addView(forgot);

        //          SET NOT REGISTERED
        TextView register = new TextView(context);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        register.setLayoutParams(layoutParams);
        register.setText(R.string.not_registered);
        register.setTextSize(16);
        register.setTextColor(Color.parseColor("#FF4FA0CF"));
        register.setGravity(Gravity.CENTER);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closePopup();
                listener.onRegister();
            }
        });
        register.setTag("Register");
        problemLayout.addView(register);


        messageLayout.addView(message);
        messageLayout.addView(loginInput);
        messageLayout.addView(passwordInput);
        messageLayout.addView(error);
        messageLayout.addView(problemLayout);
        messageLayout.addView(buttonLayout);

        generalLayout.addView(messageLayout);
    }

    public void setProblemsTextSize(float size)
    {
        final TextView forgot = (TextView) messageLayout.findViewWithTag("Forgot");
        final TextView register = (TextView) messageLayout.findViewWithTag("Register");
        forgot.setTextSize(size);
        register.setTextSize(size);
    }

    public void setProblemsTextSize(float forgotSize, float registerSize)
    {
        final TextView forgot = (TextView) messageLayout.findViewWithTag("Forgot");
        final TextView register = (TextView) messageLayout.findViewWithTag("Register");
        forgot.setTextSize(forgotSize);
        register.setTextSize(registerSize);
    }

    public void setForgotText(CharSequence text)
    {
        final TextView forgot = (TextView) messageLayout.findViewWithTag("Forgot");
        forgot.setText(text);
    }

    public void setRegisterText(CharSequence text)
    {
        final TextView register = (TextView) messageLayout.findViewWithTag("Register");
        register.setText(text);
    }

    public void setProblemsTextColor(int color)
    {
        final TextView forgot = (TextView) messageLayout.findViewWithTag("Forgot");
        final TextView register = (TextView) messageLayout.findViewWithTag("Register");
        forgot.setTextColor(color);
        register.setTextColor(color);
    }

    public void setProblemsTextColor(int forgotColor, int registerColor)
    {
        final TextView forgot = (TextView) messageLayout.findViewWithTag("Forgot");
        final TextView register = (TextView) messageLayout.findViewWithTag("Register");
        forgot.setTextColor(forgotColor);
        register.setTextColor(registerColor);
    }

    public void setErrorTextSize(float size)
    {
        this.errorTextSize = size;
    }

    public void setErrorTextColor(int color)
    {
        this.errorTextColor = color;
    }

    public void setErrorTextItalic(boolean inItalic)
    {
        this.errorTextItalic = inItalic;
    }

    public void setErrorMessage(CharSequence message)
    {
        final LinearLayout error = (LinearLayout) messageLayout.findViewWithTag("Error");
        if (message != null && !message.toString().matches(""))
        {
            error.removeAllViews();
            TextView errorMessage = new TextView(context);
            errorMessage.setText(message);
            errorMessage.setTextSize(errorTextSize);
            errorMessage.setTextColor(errorTextColor);
            errorMessage.setGravity(Gravity.CENTER);
            if (errorTextItalic)
                errorMessage.setTypeface(null, Typeface.ITALIC);

            error.addView(errorMessage);
            Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
            error.startAnimation(shake);
        }
    }

    //      ACCEPT BUTTON SETTINGS

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

    public PopupLogin(@NonNull Activity activity, final PopupListener listener)
    {
        super(activity.getWindow().getDecorView().getRootView());
        this.listener = listener;
        Context context = activity.getApplicationContext();
        createLayout(context, activity.getWindowManager().getDefaultDisplay());
    }

    @Override
    public void closePopup()
    {
        super.closePopup();
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("LoginInput");
        input.setEnabled(false);
        input = (MyEditText) messageLayout.findViewWithTag("PasswordInput");
        input.setEnabled(false);
    }

    @Override
    public void display()
    {
        super.display();
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("LoginInput");
        input.setText("");
        input.setEnabled(true);
        input = (MyEditText) messageLayout.findViewWithTag("PasswordInput");
        input.setText("");
        input.setEnabled(true);
    }

    private String getLoginValue()
    {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("LoginInput");
        return input.getText().toString();
    }

    private String getPasswordValue()
    {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("PasswordInput");
        return input.getText().toString();
    }

    public void setLoginPrefix(String prefix)
    {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("LoginInput");
        input.setPrefix(prefix);
    }

    public void setPasswordPrefix(String prefix)
    {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("PasswordInput");
        input.setPrefix(prefix);
    }

    public void setPrefixes(String loginPrefix, String passwordPrefix)
    {
        MyEditText input = (MyEditText) messageLayout.findViewWithTag("LoginInput");
        input.setPrefix(loginPrefix);
        input = (MyEditText) messageLayout.findViewWithTag("PasswordInput");
        input.setPrefix(passwordPrefix);
    }
}
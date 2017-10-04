# Popups-Android [![](https://jitpack.io/v/Thurluth/Popups-Android.svg)](https://jitpack.io/#Thurluth/Popups-Android)

Popups allows you to display some popups with simple lines of code
<br/>
_Minimum API : 23_

You can dowload the APK in order to test the popups

You also have custom _ScrollView_ / _EditText_ / _ViewPager_ in the library

<br/>

# Avaible Popups

## PopupMessage

This Popup just display a simple message with a button to close the popup.

<img src="https://raw.github.com/Thurluth/Popups-Android/master/sceenshot/PopupMessage.png"/>

## <br/>PopupBoolean

This Popup display a text with two buttons.

<img src="https://raw.github.com/Thurluth/Popups-Android/master/sceenshot/PopupBoolean.png"/>


## <br/>PopupChoice

This Popup display a text and multiple clickable choices.

<img src="https://raw.github.com/Thurluth/Popups-Android/master/sceenshot/PopupChoice.png"/>


## <br/>PopupInputInt

This Popup display a text and an EditText where you can enter number.

<img src="https://raw.github.com/Thurluth/Popups-Android/master/sceenshot/PopupInputInt.png"/>


## <br/>PopupInputString

This Popup display a text and an EditText where you can enter text.

<img src="https://raw.github.com/Thurluth/Popups-Android/master/sceenshot/PopupInputString.png"/>


## <br/>PopupLogin

This Popup display a text and an EditText where you can enter a login and one where you can enter a password.

<img src="https://raw.github.com/Thurluth/Popups-Android/master/sceenshot/PopupLogin.png"/>

<br/>

# USAGE

## How to use it in your project ?

Add this line in your project build.gradle at the end of repositories:

```
repositories {
    maven { url 'https://jitpack.io' } // THIS LINE
}
```

And add the dependency in your app build.gradle :

```
dependencies {
    compile 'com.github.Thurluth:Popups-Android:1.6.1' // THIS LINE
}
```

## General usage

<br/>&rarr; Create Popup :
```
PopupType popup = new PopupType(Activity activity, PopupListener listener);
```
See [different listeners](#different-listeners) bellow
 
<br/>&rarr; Set Popup message text :
```
popup.setMessageText(String text);
```
> Set on _Text_ by default

<br/>&rarr; Set message color :
```
popup.setMessageTextColor(int color);
```

<br/>&rarr; Set message size :
```
popup.setMessageTextSize(int size);
```
> Set on _20_ by default

<br/>&rarr; Set Popup background color :
```
popup.setPopupBackgroundColor(int color);
```

<br/>&rarr; Set Popup outline width :
```
popup.setPopupOutlineWidth(int width);
```
> Set on _2_ by default

<br/>&rarr; Set Popup outline color :
```
popup.setPopupOutlineColor(int color);
```

<br/>&rarr; Set Popup outline :
```
popup.setPopupOutline(int width, int color);
```
> Default width set on _2_

<br/>&rarr; Set Popup corner radius :
```
popup.setPopupCornerRadius(int radius);
```
> Set on _20_ by default

<br/>&rarr; Display Popup :
```
popup.display();
```

## <br/>Popups having an accept button usage

<br/>&rarr; Set accept button icon color :
```
popup.setAcceptColor(int color);
```

<br/>&rarr; Set accept button background color when not pressed :
```
popup.setAcceptBackgroundColorNotPressed(int colorNotPressed);
```

<br/>&rarr; Set accept button background color when pressed :
```
popup.setAcceptBackgroundColorPressed(int colorPressed);
```

<br/>&rarr; Set accept button background color :
```
popup.setAcceptBackgroundColor(int colorNotPressed, int colorPressed);
```

## <br/>Popups having a cancel button usage

<br/>&rarr; Set cancel button icon color :
```
popup.setCancelColor(int color);
```

<br/>&rarr; Set cancel button background color when not pressed :
```
popup.setCancelBackgroundColorNotPressed(int colorNotPressed);
```

<br/>&rarr; Set cancel button background color when pressed :
```
popup.setCancelBackgroundColorPressed(int colorPressed);
```

<br/>&rarr; Set cancel button background color :
```
popup.setCancelBackgroundColor(int colorNotPressed, int colorPressed);
```

## <br/>PopupInput usage

<br/>&rarr; Set default value in EditText :
```
popup.setDefaultInput(CharSequence defaultValue);
```
> _null_ by default

## <br/>PopupLogin usage

<br/>&rarr; Set problems _(forgotten password / not register yet)_ text size :
```
popup.setProblemsTextSize(float size);
``` 
or
``` 
popup.setProblemsTextSize(float forgotSize, float registerSize);
```

<br/>&rarr; Set forgotten password text :
```
popup.setForgotText(CharSequence text);
```
> Set on _Forgot your password ?_ by default

<br/>&rarr; Set register text :
```
popup.setRegisterText(CharSequence text);
```
> Set on _Not registered yet ?_ by default

<br/>&rarr; Set problems text color :
```
popup.setProblemsTextColor(int color);
``` 
or
``` 
popup.setProblemsTextColor(int forgotColor, int registerColor);
```

<br/>&rarr; Set error message text size :
```
popup.setErrorTextSize(float size);
```
> Set on _18_ by default

<br/>&rarr; Set error message text color :
```
popup.setErrorTextColor(int color);
```

<br/>&rarr; Set error message in italic or not :
```
popup.setErrorTextItalic(boolean inItalic);
```
> Set on _true_ by default

##<br/>Different listeners

<br/>&rarr; PopupMessage :

```
lister = new PopupMessage.PopupListener()
{
    @Override
    public void onClosed()
    {
         /*
         *   HERE YOUR CODE THAT WILL
         *   BE EXECUTED WHEN POPUP
         *   IS CLOSED
         */
    }
});
 ```

<br/>&rarr; PopupBoolean :

```
lister = new PopupBoolean.PopupListener()
{
    @Override
    public void onAccept()
    {
         /*
         *   HERE YOUR CODE THAT WILL
         *   BE EXECUTED WHEN ACCEPT
         *   BUTTON IS PRESSED
         */
    }
    
    @Override
    public void onRefuse()
    {
         /*
         *   HERE YOUR CODE THAT WILL
         *   BE EXECUTED WHEN REFUSE
         *   BUTTON IS PRESSED
         */    
    }
});
 ```

<br/>&rarr; PopupChoice :

```
lister = new PopupChoice.PopupListener()
{
    @Override
    public String[] setChoices()
    {
        return new String[] {
                "Here",
                "You",
                "Put",
                "Your",
                "Choices",
                "List"
        };
    }
    
    @Override
    public int setDisplayableChoices()
    {
        return {int};
    }
    
    @Override
    public void onSelected(String choice)
    {
         /*
         *   HERE YOUR CODE THAT WILL
         *   BE EXECUTED WHEN A CHOICE
         *   IS SELECTED
         */
    }
    
    @Override
    public void onCancel()
    {
         /*
         *    HERE YOUR CODE THAT WILL
         *    BE EXECUTED WHEN CANCEL
         *    BUTTON IS PRESSED
         */
    }
};
 ```
 
 <br/>

<br/>&rarr; PopupInput :

```
lister = new PopupInput.PopupListener()
{
    @Override
    public void onConfirm(Object value)
    {
         /*
         *   HERE YOUR CODE THAT WILL
         *   BE EXECUTED WHEN CONFIRM
         *   BUTTON IS PRESSED
         */
    }
    
    @Override
    public void onRefuse()
    {
         /*
         *   HERE YOUR CODE THAT WILL
         *   BE EXECUTED WHEN CANCEL
         *   BUTTON IS PRESSED
         */    
    }
});
 ```

<br/>&rarr; PopupLogin :

```
lister = new PopupLogin.PopupListener()
{
    @Override
    public String onConfirm(String login, String password)
    {
         /*
         *    HERE YOUR CODE THAT WILL
         *    BE EXECUTED WHEN ACCEPT
         *    BUTTON IS PRESSED
         *
         *    RETURN "" OR null IF YOU WANT
         *    TO CLOSE THE POPUP.
         *    OTHERWISE RETURN AN ERROR MESSAGE
         */
    }
 
    @Override
    public void onCancel()
    {
         /*
         *    HERE YOUR CODE THAT WILL
         *    BE EXECUTED WHEN CANCEL
         *    BUTTON IS PRESSED
         */
    }
 
    @Override
    public void onForgot()
    {
         /*
         *    HERE YOUR CODE THAT WILL
         *    BE EXECUTED IF USER SAYS
         *    HE FORGOT HIS PASSWORD
         */
    }
 
    @Override
    public void onRegister()
    {
         /*
         *    HERE YOUR CODE THAT WILL
         *    BE EXECUTED IF USER SAYS
         *    HE WANTS TO REGISTER
         */
    }
};
 ```

# DEVELOP BY

[Thurluth](https://github.com/Thurluth) - thurluth@gmail.com

<br/>

# LICENSE

    MIT License

    Copyright (c) 2017 Thurluth

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

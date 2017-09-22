# Popups-Android [![](https://jitpack.io/v/Thurluth/Popups-Android.svg)](https://jitpack.io/#Thurluth/Popups-Android)

Popups allows you to display some popups with simple lines of code
<br/>
_Minimum API : 23_

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

<br/>

# USAGE

## How to use it in your project ?

Add this line in your root build.gradle at the end of repositories:

```
maven { url 'https://jitpack.io' }
```

And add the dependency in your app build.gradle :

```
compile 'com.github.Thurluth:Popups-Android:1.0.2'
```

## General usage

<br/>&rarr; Create Popup :
```
PopupType popup = new PopupType(RelativeLayout activityLayout, Display display);
```
 
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

<br/>&rarr; Close Popup :
```
popup.closePopup();
```
> **When you change a listener, you have to call this method if you want to close the popup**

## <br/>Popups having an accept button usage

<br/>&rarr; Set accept button action when clicked :
```
popup.setAcceptListener(onClickListener listener);
```
> Close popup by default

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
> Close popup by default

## <br/>PopupChoice usage

<br/>&rarr; Add choice :
```
popup.addChoice(String text, onClickListener listener);
```

<br/>&rarr; When all choices added :
```
popup.endChoice();
```

## <br/>PopupInput usage

<br/>&rarr; Set default value in EditText (nothing if don't call) :
```
popup.setDefaultInput(CharSequence defaultValue);
```
> _Null_ by default

<br/>&rarr; Get the value entered :
```
popup.getValue();
```

<br/>&rarr; Get the value as text :
```
popup.getValueStr();
```

<br/>

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

package com.bodaciousithub.custompasswordviewedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class CustomPasswordViewEditText extends android.support.v7.widget.AppCompatEditText {

    Drawable viewPasswordImage;


    public CustomPasswordViewEditText(Context context) {
        super(context);
    }

    public CustomPasswordViewEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPasswordViewEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        viewPasswordImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_remove_red_eye_black_24dp, null);

        // If the X (clear) button is tapped, clear the text.
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Use the getCompoundDrawables()[2] expression to check
                // if the drawable is on the "end" of text [2].
                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart; // Used for LTR languages
                    float clearButtonEnd;  // Used for RTL languages
                    boolean isClearButtonClicked = false;
                    // Detect the touch in RTL or LTR layout direction.
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // If RTL, get the end of the button on the left side.
                        clearButtonEnd = viewPasswordImage
                                .getIntrinsicWidth() + getPaddingStart();
                        // If the touch occurred before the end of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }
                    } else {
                        // Layout is LTR.
                        // Get the start of the button on the right side.
                        clearButtonStart = (getWidth() - getPaddingEnd()
                                - viewPasswordImage.getIntrinsicWidth());
                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }


                    // Check for actions if the button is tapped.
                    if (isClearButtonClicked) {
                        // Check for ACTION_DOWN (always occurs before ACTION_UP).
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // Switch to the black version of clear button.
                            viewPasswordImage =
                                    ResourcesCompat.getDrawable(getResources(),
                                            R.drawable.ic_remove_red_eye_black_24dp, null);
                            setTransformationMethod(null);
                        }
                        // Check for ACTION_UP.
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // Switch to the opaque version of clear button.
                            viewPasswordImage =
                                    ResourcesCompat.getDrawable(getResources(),
                                            R.drawable.ic_remove_red_eye_black_24dp, null);
                            // Clear the text and hide the clear button.
                            //hideClearButton();
                            setTransformationMethod(new PasswordTransformationMethod());
                            return true;
                        }

                    } else {
                        return false;
                    }
                }
                return false;
            }
        });


        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(getText().toString())){
                    hideClearButton();
                }else {
                    showClearButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    /**
     * Shows the visiblePassword button.
     */
    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                      // Start of text.
                        null,               // Above text.
                        viewPasswordImage,  // End of text.
                        null);              // Below text.
    }

    /**
     * Hides the visiblePassword button.
     */
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Start of text.
                        null,      // Above text.
                        null,      // End of text.
                        null);     // Below text.
    }

}

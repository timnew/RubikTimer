package com.github.timnew.rubiktimer.common.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.github.timnew.rubiktimer.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.dialog_edit_text)
public class DialogEditText extends FrameLayout {

    @ViewById(android.R.id.edit)
    protected EditText editText;

    public DialogEditText(Context context) {
        super(context);
    }

    public DialogEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditText getEditText() {
        return editText;
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(CharSequence text) {
        editText.setText(text);
    }

    public void setHint(int resId) {
        editText.setHint(resId);
    }
}

package com.yuanbaogo.zui.edittext;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @Description: EditText添加搜索按钮
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/9/21 3:19 PM
 * @Modifier:
 * @Modify:
 */
public class EditTextOnEditor {

    private static EditTextOnEditor editTextOnEditor = null;

    private EditText editSearch;

    private OnEditor onEditor;

    public static EditTextOnEditor getOnEditor() {
        if (editTextOnEditor == null) {
            synchronized (EditTextOnEditor.class) {
                if (editTextOnEditor == null) {
                    editTextOnEditor = new EditTextOnEditor();
                }
            }
        }
        return editTextOnEditor;
    }

    public EditTextOnEditor setEditTextOnEditor(EditText editSearch) {
        this.editSearch = editSearch;
        return this;
    }

    public EditTextOnEditor setOnEditor(OnEditor onEditor) {
        this.onEditor = onEditor;
        return this;
    }

    public void startEditor() {

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchKey = editSearch.getText().toString();
                    if (!TextUtils.isEmpty(searchKey)) {
                        editSearch.clearFocus();
                        if (!TextUtils.isEmpty(searchKey)) {
                            onEditor.onEditor(textView, actionId, keyEvent, searchKey);
                        }
                    } else {
                        onEditor.onNoEditor(textView, actionId, keyEvent, searchKey);
                    }
                    return true;
                }
                return false;

            }
        });

    }

    public interface OnEditor {

        void onEditor(TextView textView, int actionId, KeyEvent keyEvent, String searchKey);

        void onNoEditor(TextView textView, int actionId, KeyEvent keyEvent, String searchKey);

    }

}

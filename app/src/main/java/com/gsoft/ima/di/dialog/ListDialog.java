package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gsoft.ima.R;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.di.components.Label;

public class ListDialog extends SweetDialog {
    ArrayAdapter<String> adapter;
    EditText outputEditText;

    public ListDialog(Context context, String title, String[] items, EditText outputEditText)
    {
        super(context, R.layout.dialog_list, title, null);
        this.outputEditText = outputEditText;
        ListView listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, items);
        listView.setAdapter(adapter);
        EditText query = (EditText) findViewById(R.id.edit_query);
        Label layoutLabel = (Label) query.getParent();
        TextView textViewLabel = (TextView) layoutLabel.getChildAt(0);
        textViewLabel.setText(context.getString(R.string.search));

        listView.setOnItemClickListener(new TextChanged());
        query.addTextChangedListener(new TextChanged());
    }

    class TextChanged implements TextWatcher, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            outputEditText.setText(((TextView) view).getText().toString());
            cancel();
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            adapter.getFilter().filter(charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    public void show() {
        super.show();
    }

}

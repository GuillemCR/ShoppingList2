package edu.upc.eseiaat.pma.guillemcomas.shoppinglist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ListActivity extends AppCompatActivity {

    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;
    private ListView list;
    private Button btn_add;
    private EditText edit_item;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = (ListView) findViewById(R.id.list);
        btn_add = (Button) findViewById(R.id.add_btn);
        edit_item = (EditText) findViewById(R.id.edit_iten);

        itemList = new ArrayList<>();
        itemList.add("Tomàquets");
        itemList.add("Paper WC");
        itemList.add("Iogurts");
        itemList.add("Galetes");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        edit_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {                 //metode per enviar desde teclat
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return true;
            }
        });

        list.setAdapter(adapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });
    }

    private void maybeRemoveItem(final int pos) {
        this.pos = pos;
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        String remove_message = getResources().getString(R.string.removemsg);
        builder.setMessage(remove_message + itemList.get(pos) + "?");

        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private void addItem() {
        String item_txt = edit_item.getText().toString();
        if (!item_txt.isEmpty()) {                                                                    //equivalent a .equals("")
            itemList.add(item_txt);
            adapter.notifyDataSetChanged();
            edit_item.setText("");
        }
    }
}

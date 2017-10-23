package edu.upc.eseiaat.pma.guillemcomas.shoppinglist2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ArrayList<ShoppingItem> itemList;
    private ShoppingList_Adapter adapter;
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
        itemList.add(new ShoppingItem("Tomàquets"));
        itemList.add(new ShoppingItem("Paper WC"));
        itemList.add(new ShoppingItem("Iogurts"));
        itemList.add(new ShoppingItem("Galetes"));

        adapter = new ShoppingList_Adapter(this, R.layout.shopping_item, itemList);

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

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                itemList.get(pos).toggleChecked();
                adapter.notifyDataSetChanged();
                /*ShoppingItem item= itemList.get(pos);
                boolean checked= item.isChecked();
                itemList.get(pos).setChecked();*/
            }
        });

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
        builder.setMessage(remove_message + itemList.get(pos).getText() + "?");

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
            itemList.add(new ShoppingItem(item_txt));
            adapter.notifyDataSetChanged();
            edit_item.setText("");
        }
        list.smoothScrollToPosition(itemList.size()-1);                                             //size()-1 és l'últim element
    }
}
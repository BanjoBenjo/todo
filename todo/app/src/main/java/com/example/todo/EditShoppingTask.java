package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EditShoppingTask extends Activity {
    private ArrayList<ShoppingItem> shoppingItems;
    private ArrayAdapter<String> shoppingItemsAdapter;
    private ListView shoppingListView;
    private Button addShoppingItem;

    private EditText inputName;
    private EditText inputQuantity;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_shopping_task);

        shoppingListView = findViewById(R.id.shoppingListView);
        addShoppingItem = findViewById(R.id.addShoppingItem);

        addShoppingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToList(v);
            }
        });

        shoppingItems = new ArrayList<>();
        shoppingItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        shoppingListView.setAdapter(shoppingItemsAdapter);
        setUpListViewListener();



        inputName = findViewById(R.id.nameOfShoppingItem);
        inputQuantity = findViewById(R.id.numberOfShoppingItems);
    }



    private void setUpListViewListener() {
        shoppingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //changes checked field in ShoppingItem
                //Context context = getApplicationContext();
                //Toast.makeText(context, "Item has been removed.", Toast.LENGTH_LONG).show();
                ShoppingItem itemToCheck = shoppingItems.get(position);
                itemToCheck.toggleCheck();
                shoppingItemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    private void addItemToList(View v) {

        if(!(inputName.equals(""))) {
            int newQuantity = 1;

            if(!(inputQuantity.equals(""))) {
                newQuantity = Integer.parseInt(inputQuantity.getText().toString());
            }
            ShoppingItem newItem = new ShoppingItem(newQuantity, inputName.getText().toString());
            shoppingItems.add(newItem);
            shoppingItemsAdapter.add(newItem.toString());
            inputName.setText("");
            inputQuantity.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_LONG).show();
        }
        shoppingItemsAdapter.notifyDataSetChanged();
    }

}

package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
	}

	public void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
	}

	public void addTodoItem(View v) {
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}

	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

}

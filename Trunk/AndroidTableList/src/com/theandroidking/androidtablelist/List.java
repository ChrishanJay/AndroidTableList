package com.theandroidking.androidtablelist;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class List extends ListActivity {
	
	EditText type;
	//private ArrayList<String> restaurants = new ArrayList<String>();
	ArrayList<String> grouped , temp;
	ArrayList<String> index;
	ArrayList<String> countries;
	LinearLayout sideIndex;	
	EntryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        type = (EditText) findViewById(R.id.input);
        sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        
        countries = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.countries_array)));
       
        index = Utils.createIndex(countries);
        grouped = Utils.groupCountries(countries , index);
        adapter = new EntryAdapter(getApplicationContext(), grouped);
		setListAdapter(adapter);
		updateIndex(index);
		
		type.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
								
				grouped.clear();
				for (int i = 0; i < countries.size(); i++) {
					if ((start + 1) <= countries.get(i).length()) {
						if (countries.get(i).trim().toLowerCase().contains(s.toString().toLowerCase())) {
							grouped.add(countries.get(i));
						}
					}
				}
				index = Utils.createIndex(grouped);
				//grouped = Utils.groupCountries(countries , index);
				adapter.notifyDataSetChanged();
				updateIndex(index);

			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					index = Utils.createIndex(grouped);
					adapter.notifyDataSetChanged();
					updateIndex(index);
				}
			}
		});
        
    }

	private void updateIndex(ArrayList<String> rest_index) {
		sideIndex.removeAllViews();		

		for (int i = 0; i < rest_index.size(); i++) {
			String index_letter = rest_index.get(i).toString();
			TextView tv = new TextView(getApplicationContext());
			tv.setText(index_letter);
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(5);
			tv.setTextColor(getResources().getColor(android.R.color.black));
			tv.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Small);

			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.5f);
			tv.setLayoutParams(params);
			sideIndex.addView(tv);
			tv.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					String s = ((TextView) v).getText().toString();
					ArrayList<String> ttemp = new ArrayList<String>();

					for (String rr : grouped) {
						ttemp.add(rr);
					}
					int in = ttemp.indexOf(s);
					setSelection(in);

				}
			});
		}

	}
	

	
	
	private class EntryAdapter extends ArrayAdapter<String> {

		private final ArrayList<String> items;
		private final LayoutInflater vi;

		public EntryAdapter(Context context, ArrayList<String> items) {
			super(context, 0, items);
			this.items = items;
			vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;

			final String s = items.get(position);
			if (s != null) {
				if (s.length() == 1) {

					v = vi.inflate(R.layout.list_item_section, null);
					v.setBackgroundResource(android.R.color.darker_gray);

					v.setOnClickListener(null);
					v.setOnLongClickListener(null);
					v.setLongClickable(false);

					final TextView sectionView = (TextView) v.findViewById(R.id.dealname);
					sectionView.setText(s);
				}
				else {

					v = vi.inflate(R.layout.list_item_entry, null);
					final TextView title = (TextView) v.findViewById(R.id.list_item_entry_title);

					if (title != null)
						title.setText(s);

					v.setOnClickListener(new OnClickListener() {

						
						public void onClick(View v) {
//							Log.i(getContext().getPackageName(), "Onclick : " + i.getId());
//							YumActivity.rest_id = i.getId();
//							if (YumActivity.backtomap) {
//								startActivity(new Intent(getApplicationContext(), Map.class).putExtra("rest", true));
//
//							}
//							else {
//
//								Intent restaurant_deals = new Intent(RestaurantSearch.this, RestaurantDeals.class);
//								startActivity(restaurant_deals);
//							}

						}
					});

				}
			}
			return v;
		}

	}
}

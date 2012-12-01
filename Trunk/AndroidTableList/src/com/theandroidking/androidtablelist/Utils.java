package com.theandroidking.androidtablelist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Utils {
	
	public static ArrayList<String> groupCountries(ArrayList<String> r, ArrayList<String> index) {
		
		ArrayList<String> array_sort = new ArrayList<String>();
		for (int i = 0; i < index.size(); i++) {
			String s = index.get(i);
			array_sort.add(s);
			for (int j = 0; j < r.size(); j++) {

				if (r.get(j).startsWith(index.get(i).toString())) {
					array_sort.add(r.get(j));

				}
			}
		}
		return array_sort;

	}
	
	public static ArrayList<String> createIndex(ArrayList<String> rest) {
		ArrayList<String> index = new ArrayList<String>();
		for (int i = 0; i < rest.size(); i++) {
			if (rest.get(i).substring(0, 1).toUpperCase().trim().length() == 1) {
				index.add(rest.get(i).substring(0, 1).toUpperCase());
			}
		}

		Set<String> set = new HashSet<String>(index);
		index.clear();
		index = new ArrayList<String>(set);
		Collections.sort(index);
		return index;
	}
	

}

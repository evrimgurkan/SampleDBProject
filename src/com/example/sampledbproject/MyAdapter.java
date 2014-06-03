package com.example.sampledbproject;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Cards>{
	
	private final Context context;
    private final List<Cards> cardsArrayList;

    public MyAdapter(Context context, List<Cards> cardsArrayList) {
    	 
        super(context, R.layout.row, cardsArrayList);

        this.context = context;
        this.cardsArrayList = cardsArrayList;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);

        // 4. Set the text for textView 
        labelView.setText(cardsArrayList.get(position).getCardName());
        valueView.setText(cardsArrayList.get(position).getCardAccount());

        // 5. return rowView
        return rowView;
    }
}

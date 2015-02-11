package mx.mobilecoder.capturaalfugitivo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mx.mobilecoder.capturaalfugitivo.model.Fugitive;

/**
 * Created by Swanros on 07/02/15.
 *
 * Esta clase, FugitiveAdapter es la que se va a encargar de mapear un Array de Fugitivos a
 * la vista a la que haya sido asignado.
 *
 * Más info sobre Adapters: http://developer.android.com/reference/android/widget/Adapter.html
 */
public class FugitiveAdapter extends ArrayAdapter<Fugitive> {
    public FugitiveAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FugitiveAdapter(Context context, int resource, Fugitive[] items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        Fugitive fugitive = getItem(position);
        if (fugitive != null) {
            TextView fugitiveNameTextView = (TextView) rowView.findViewById(android.R.id.text1);

            fugitiveNameTextView.setText(fugitive.mName);
        }

        return rowView;
    }

}

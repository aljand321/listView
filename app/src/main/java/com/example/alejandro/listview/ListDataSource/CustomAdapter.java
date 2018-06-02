package com.example.alejandro.listview.ListDataSource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejandro.listview.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{
    private Context CONTEX;
    private ArrayList<ItemList>LIST;

    public CustomAdapter(Context context, ArrayList<ItemList> List){
        this.CONTEX = context;
        this.LIST = List;
    }

    @Override
    public int getCount() {
        return this.LIST.size();
    }

    @Override
    public Object getItem(int position) {
        return this.LIST.get( position );
    }

    @Override
    public long getItemId(int position) {
         return position;
    }

    //>>>>>>>>>>>>>>>>>>>>

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflate = (LayoutInflater) this.CONTEX.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflate.inflate(R.layout.item_layout, null);

        }
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView year = (TextView)convertView.findViewById( R.id.year );
        TextView type = (TextView)convertView.findViewById( R.id.type );
        title.setText( this.LIST.get(position).getTitle());
        year.setText( this.LIST.get(position).getYear());
        type.setText( this.LIST.get(position).getType());

        ImageView img = (ImageView)convertView.findViewById(R.id.poster_layout);

        try {
            URL url = new URL( this.LIST.get( position ).getPoster() );
            InputStream stream = url.openConnection().getInputStream();
            Bitmap imageBipmap = BitmapFactory.decodeStream( stream );
            img.setImageBitmap( imageBipmap );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}

package com.example.alejandro.listview;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alejandro.listview.ListDataSource.CustomAdapter;
import com.example.alejandro.listview.ListDataSource.ItemList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView LIST;
    private ArrayList<ItemList> LISTINFO;
    private Context root;
    private CustomAdapter ADAPTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy( policy );

        root = this;

        LISTINFO = new ArrayList<ItemList>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        loadConponents();
        //"http://10.10.1.237:7777/api/v1.0/"+keystr
        //"http://www.omdbapi.com/?s=" + keystr + "&page=1&apikey=e1c80c83"
    }
    private void loadInitialRestData(String keystr){
        AsyncHttpClient client = new AsyncHttpClient( );
        client.get("    http://10.10.1.237:7777/api/v1.0/"+keystr, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray list = (JSONArray) response.get("Search");
                    for (int i = 0; i<list.length(); i++){
                        JSONObject itemJson = list.getJSONObject( i );
                        String title = itemJson.getString( "Title" );
                        String year = itemJson.getString( "Year" );
                        String imdbID = itemJson.getString( "imdbID" );
                        String type = itemJson.getString( "Type" );
                        String Poster = itemJson.getString( "Poster" );
                        ItemList item = new ItemList( Poster, title, year, type, imdbID );
                        LISTINFO.add( item );
                    }
                    ADAPTER = new CustomAdapter( root, LISTINFO );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){

            }
        });
    }

    private void loadConponents() {
        LIST = (ListView) this.findViewById( R.id.ListviewLayout );
        //LISTINFO.add( new ItemList( "https://koreaboo-cdn.storage.googleapis.com/2017/06/yoona-2015.jpg", "prueva", "159", "move" ));
        EditText search = (EditText)this.findViewById( R.id.searchmovie );
        //eventos

        search.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                loadInitialRestData(str);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        ADAPTER = new CustomAdapter( this, LISTINFO );
        LIST.setAdapter( ADAPTER );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

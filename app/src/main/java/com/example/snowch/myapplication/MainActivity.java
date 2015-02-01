package com.example.snowch.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cloudant.p2p.listener.HttpListener;
import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;

import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create datastore
        File path = getApplicationContext().getDir("datastores", MODE_PRIVATE);
        DatastoreManager manager = new DatastoreManager(path.getAbsolutePath());

        Datastore ds = manager.openDatastore("mydb");
        ds.close();

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8182);
        component.getDefaultHost().attachDefault(HttpListener.class);

        Map<String, Object> contextAttr = new HashMap<String, Object>();
        contextAttr.put("DB_DIR", path.getAbsolutePath());
        Context context = new Context();
        context.setAttributes(contextAttr);

        component.setContext(context);

        try {
            component.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

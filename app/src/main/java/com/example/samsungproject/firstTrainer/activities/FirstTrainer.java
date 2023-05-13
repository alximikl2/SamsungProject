package com.example.samsungproject.firstTrainer.activities;

import static com.example.samsungproject.firstTrainer.Tags.ID;
import static com.example.samsungproject.firstTrainer.Tags.ON_CREATE;
import static com.example.samsungproject.firstTrainer.Tags.ON_DESTROY;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.R;
import com.example.samsungproject.databinding.FirstTrainerBinding;
import com.example.samsungproject.firstTrainer.ActivitiesAdapter;
import com.example.samsungproject.firstTrainer.ActivityRecord;
import com.example.samsungproject.firstTrainer.ActivityStateReceiver;
import com.example.samsungproject.firstTrainer.RecordFields;
import com.example.samsungproject.firstTrainer.Tags;
import com.example.samsungproject.firstTrainer.dialogs.ActivityCreateDialog;
import com.example.samsungproject.firstTrainer.dialogs.ActivitySwitchDialog;

public class FirstTrainer extends AppCompatActivity {
    private FirstTrainerBinding binding;
    private final String name = Types.MAIN.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRecord record = new ActivityRecord(name, "No parent", true, Types.STANDARD, this::finish);
        RecordFields.addRecord(record);

        setTitle(Types.MAIN.getName());
        binding = FirstTrainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(ResourcesCompat.getDrawable(
                getResources(), R.drawable.gradient, getTheme()));

        ActivitiesAdapter adapter = new ActivitiesAdapter(Types.MAIN.getName());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView rv = findViewById(R.id.table);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        IntentFilter ifi = new IntentFilter();
        ifi.addAction("ACTIVITY_STATE_ACTION");

        registerReceiver(new ActivityStateReceiver(), ifi);

        sendMessageToBroadcast(ON_CREATE.getId());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RecordFields.removeRecord(name);
        sendMessageToBroadcast(Tags.ON_DESTROY.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.new_activity_create) {
            ActivityCreateDialog dialog = new ActivityCreateDialog(name);
            dialog.show(getSupportFragmentManager(), "New Activity Dialog");
            return true;
        }
        if(id == R.id.switch_activity){
            ActivitySwitchDialog dialog = new ActivitySwitchDialog();
            dialog.show(getSupportFragmentManager(), "New Switch Dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessageToBroadcast(int id){
        Intent onCreateIntent = new Intent();
        onCreateIntent.setAction("ACTIVITY_STATE_ACTION");
        if (id == ON_CREATE.getId()) {
            onCreateIntent.putExtra(ID.getName(), ON_CREATE.getId());
            onCreateIntent.putExtra(ON_CREATE.getName(), getLocalClassName());
        } else if(id == ON_DESTROY.getId()){
            onCreateIntent.putExtra(ID.getName(), ON_DESTROY.getId());
            onCreateIntent.putExtra(ON_DESTROY.getName(), getLocalClassName());
        }
        sendBroadcast(onCreateIntent);
    }
}
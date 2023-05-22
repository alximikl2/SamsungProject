package com.example.samsungproject.firstTrainer.activities;

import static com.example.samsungproject.firstTrainer.Tags.ACTIVITY_NAME;
import static com.example.samsungproject.firstTrainer.Tags.ID;
import static com.example.samsungproject.firstTrainer.Tags.IS_TASK;
import static com.example.samsungproject.firstTrainer.Tags.ON_CREATE;
import static com.example.samsungproject.firstTrainer.Tags.ON_DESTROY;
import static com.example.samsungproject.firstTrainer.Tags.PARENT_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.R;
import com.example.samsungproject.databinding.FirstTrainerFrameBinding;
import com.example.samsungproject.firstTrainer.ActivitiesAdapter;
import com.example.samsungproject.firstTrainer.ActivityRecord;
import com.example.samsungproject.firstTrainer.FirstTrainerFields;
import com.example.samsungproject.firstTrainer.dialogs.ActivityCreateDialog;
import com.example.samsungproject.firstTrainer.dialogs.ActivitySwitchDialog;
import com.example.samsungproject.popup.PopupTutorial;

public abstract class AnyActivity extends AppCompatActivity {
    private FirstTrainerFrameBinding binding;
    private String name;
    protected Types type;
    private RecyclerView rv;
    private boolean tutorial, isNullName = false;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tutorial = FirstTrainerFields.isTutorialFourth();

        setType();

        name = getIntent().getStringExtra(ACTIVITY_NAME.getName());
        String parent = getIntent().getStringExtra(PARENT_NAME.getName());
        boolean isTask = getIntent().getBooleanExtra(IS_TASK.getName(), false);

        if(name == null){
            parent = "Не известен";
            name = "null " + FirstTrainerFields.getNullIndex();
            isNullName = true;
        }

        ActivityRecord record = new ActivityRecord(name, parent, isTask, type, this::finish);
        FirstTrainerFields.addRecord(record);

        binding = FirstTrainerFrameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle(name);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(ResourcesCompat.getDrawable(
                getResources(), R.drawable.gradient,
                new ContextThemeWrapper(getApplicationContext(), R.style.Theme_FirstTrainer).getTheme()));

        ActivitiesAdapter adapter = new ActivitiesAdapter(name, getResources());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.table);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        if(tutorial && isNullName) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                View popupView = getLayoutInflater().inflate(R.layout.tutorial_first_popup, null);
                PopupTutorial popupTutorial = new PopupTutorial(popupView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        true);
                popupTutorial.putString(getResources().getString(R.string.message_popup_first_4));
                this.runOnUiThread(() -> {
                    popupTutorial.showAsDropDown(rv.getChildAt(rv.getChildCount() - 1),
                            (int) (getResources().getDisplayMetrics().density * 8), 0);
                });
            });
            thread.start();
            FirstTrainerFields.tutorialFourthUsed();
            tutorial = false;
        }

        sendMessageToBroadcast(ON_CREATE.getId());
    }

    protected void setType(Types type){
        this.type = type;
    }

    protected void setType(){
        setType(Types.NULL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FirstTrainerFields.removeRecord(name);
        sendMessageToBroadcast(ON_DESTROY.getId());
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
            dialog.show(getSupportFragmentManager(), null);
            return true;
        }
        if(id == R.id.switch_activity){
            ActivitySwitchDialog dialog = new ActivitySwitchDialog();
            dialog.show(getSupportFragmentManager(), null);
            return true;
        }
        if(id == R.id.exit){
            FirstTrainerFields.exit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessageToBroadcast(int id){
        Intent intent = new Intent();
        intent.setAction("ACTIVITY_STATE_ACTION");
        if (id == ON_CREATE.getId()) {
            intent.putExtra(ID.getName(), ON_CREATE.getId());
            intent.putExtra(ON_CREATE.getName(), name);
        } else if(id == ON_DESTROY.getId()){
            intent.putExtra(ID.getName(), ON_DESTROY.getId());
            intent.putExtra(ON_DESTROY.getName(), name);
        }
        sendBroadcast(intent);
    }
}

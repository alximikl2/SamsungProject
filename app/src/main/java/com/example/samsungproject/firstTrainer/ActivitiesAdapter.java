package com.example.samsungproject.firstTrainer;

import static com.example.samsungproject.firstTrainer.activities.Types.MAIN;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.R;

import java.util.ArrayList;
import java.util.Objects;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private ArrayList<ActivityRecord> records;
    private final String activityName;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView typeTextView;
        private final TextView nameTextView;
        private final TextView parentTextView;
        private final TextView isTask;
        private final Button finishButton;

        public ViewHolder(View view) {
            super(view);

            typeTextView = view.findViewById(R.id.type);
            nameTextView = view.findViewById(R.id.name);
            parentTextView = view.findViewById(R.id.parent);
            isTask = view.findViewById(R.id.task);
            finishButton = view.findViewById(R.id.button);
        }

        public TextView[] getTextViews() {
            return new TextView[]{typeTextView, nameTextView, parentTextView, isTask};
        }

        public Button getButton() {
            return finishButton;
        }
    }

    public ActivitiesAdapter(String activityName) {
        this.activityName = activityName;
        this.records = RecordFields.getRecords();
        RecordFields.addNotification(this::update);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(){
        this.records = RecordFields.getRecords();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final TextView[] textViews = viewHolder.getTextViews();
        final Button button = viewHolder.getButton();

        TextView typeTextView = textViews[0];
        TextView nameTextView = textViews[1];
        TextView parentTextView = textViews[2];
        TextView isTask = textViews[3];

        ActivityRecord record = records.get(position);
        String name = record.getName();

        typeTextView.setText(record.getType().getName());
        nameTextView.setText(name);
        parentTextView.setText(record.getParent());
        if(record.isTask()) {
            isTask.setText("Task");
        } else {
            isTask.setText("Not A Task");
        }

        if(Objects.equals(name, activityName) || Objects.equals(name, MAIN.getName())){
            button.setVisibility(View.INVISIBLE);
        } else {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener((View view) -> {
                record.finishActivity();
                RecordFields.removeRecord(record.getName());
            });
        }
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
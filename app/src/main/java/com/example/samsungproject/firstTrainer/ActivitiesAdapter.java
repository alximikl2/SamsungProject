package com.example.samsungproject.firstTrainer;

import static com.example.samsungproject.firstTrainer.activities.Types.MAIN;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject.R;
import com.example.samsungproject.popup.PopupInfo;

import java.util.ArrayList;
import java.util.Objects;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private ArrayList<ActivityRecord> records;
    private final String activityName;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView typeTextView;
        private final TextView nameTextView;
        //private final TextView parentTextView;
        //private final TextView isTask;
        private final Button finishButton;
        private final Button showButton;
        private final View popupView;

        public ViewHolder(View view, View popupView) {
            super(view);

            //typeTextView = view.findViewById(R.id.type);
            nameTextView = view.findViewById(R.id.name);
            //parentTextView = view.findViewById(R.id.parent);
            //isTask = view.findViewById(R.id.task);
            finishButton = view.findViewById(R.id.button);
            showButton = view.findViewById(R.id.show_button);
            this.popupView = popupView;
        }

        public TextView getTextView() {
            return nameTextView;
        }

        public Button getButton() {
            return finishButton;
        }

        public Button getImageButton(){return showButton;}

        public View getPopupView(){return popupView;}
    }

    public ActivitiesAdapter(String activityName) {
        this.activityName = activityName;
        this.records = FirstTrainerFields.getRecords();
        FirstTrainerFields.addNotification(this::update);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(){
        this.records = FirstTrainerFields.getRecords();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_row, viewGroup, false);
        View popupView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.more_info, null);

        return new ViewHolder(view, popupView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final TextView nameTextView = viewHolder.getTextView();
        final Button button = viewHolder.getButton();
        final Button imageButton = viewHolder.getImageButton();

        ActivityRecord record = records.get(position);
        String name = record.getName();

        nameTextView.setText(name);

        if(Objects.equals(name, activityName) || Objects.equals(name, MAIN.getName())){
            button.setVisibility(View.INVISIBLE);
        } else {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener((View view) -> {
                record.finishActivity();
                FirstTrainerFields.removeRecord(record.getName());
            });
        }
        imageButton.setOnClickListener((View view) -> {
            PopupInfo popupWindow = new PopupInfo(viewHolder.getPopupView(),
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true);
            if(record.isTask()) {
                popupWindow.fillStrings(record.getParent(), "Task", record.getType().getName());
            } else {
                popupWindow.fillStrings(record.getParent(), "Not A Task", record.getType().getName());
            }
            popupWindow.showAsDropDown(view);
        });

    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
package com.example.samsungproject.firstTrainer;

import java.util.ArrayList;
import java.util.Objects;

public class RecordFields {
    private static final ArrayList<ActivityRecord> records = new ArrayList<>();
    private static final ArrayList<Runnable> notifications = new ArrayList<>();
    private static boolean tutorialFirst, tutorialSecond, tutorialThird;
    
    public static void setTutorial(boolean bool){
        tutorialFirst = bool;
        tutorialSecond = bool;
        tutorialThird = bool;
    }

    public static void addRecord(ActivityRecord record){
        records.add(record);
    }

    public static void addNotification(Runnable runnable){
        notifications.add(runnable);
    }

    private static void dropNotifications(){
        for(Runnable runnable : notifications){
            runnable.run();
        }
    }

    public static void removeRecord(int index){
        records.remove(index);
        notifications.remove(index);
        dropNotifications();
    }

    public static void removeRecord(String name){
        for(int i = 0; i < records.size(); i++){
            if(Objects.equals(records.get(i).getName(), name)){
                RecordFields.removeRecord(i);
                break;
            }
        }
    }

    public static boolean inRecords(String name){
        for(int i = 0; i < records.size(); i++){
            if(Objects.equals(records.get(i).getName(), name)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<ActivityRecord> getRecords() {
        return records;
    }

    public static boolean isTutorialFirst() {
        if(tutorialFirst){
            tutorialFirst = false;
            return true;
        }
        return false;
    }

    public static boolean isTutorialSecond() {
        if(tutorialSecond){
            tutorialSecond = false;
            return true;
        }
        return false;
    }

    public static boolean isTutorialThird() {
        if(tutorialThird){
            tutorialThird = false;
            return true;
        }
        return false;
    }
}

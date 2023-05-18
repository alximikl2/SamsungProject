package com.example.samsungproject.secondTrainer;

public class SecondTrainerFields {
    private static boolean tutorialFirst, tutorialSecond, tutorialThird;

    public static void setTutorial(boolean bool){
        tutorialFirst = bool;
        tutorialSecond = bool;
        tutorialThird = bool;
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

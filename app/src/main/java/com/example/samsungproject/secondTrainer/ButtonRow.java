package com.example.samsungproject.secondTrainer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.samsungproject.R;

import java.util.ArrayList;

public class ButtonRow {
    private final LinearLayout layout;
    private final Context context;
    private final FragmentManager fragmentManager;
    private final int selectedTextColor = 0xFF007AFF;
    private final int defaultTextColor = 0xFF6B6B6B;
    private final ArrayList<MainFragment> fragments = new ArrayList<>();

    public ButtonRow(LinearLayout layout, Context context, FragmentManager fragmentManager){
        this.layout = layout;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void createButton(MainFragment fragment){
        fragments.add(fragment);
        FlagButton button = new FlagButton(
                new ContextThemeWrapper(context, R.style.ButtonStyle),
                null, R.style.ButtonStyle);
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (20 * scale + 0.5f);
        button.setCornerRadius(pixels);
        button.setText(fragment.getTag());
        layout.addView(button);
        initializeButton(button);
        setCurrentButton(layout.getChildCount() - 1);
    }

    public void deleteButton(MainFragment fragment){
        for(int i = 0;i < fragments.size(); i++){
            if(fragment == fragments.get(i)){
                layout.removeViewAt(i);
                fragments.remove(i);
                break;
            }
        }
    }

    public void initializeButton(FlagButton button){
        button.setOnClickListener((View view) -> {
            for(int i = 0; i < layout.getChildCount();i++){
                if((FlagButton) layout.getChildAt(i) == button){
                    setCurrentButton(i);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    for (MainFragment fr: fragments){
                        switch (fr.getType()) {
                            case REMOVE:
                                fragmentTransaction.remove(fr);
                                break;
                            case ATTACH_DETACH:
                                if (!fr.isDetached()) {
                                    fragmentTransaction.detach(fr);
                                }
                                break;
                            case SHOW_HIDE:
                                if (!fr.isHidden()) {
                                    fragmentTransaction.hide(fr);
                                }
                                break;
                            default:
                                throw new NoSuchFieldError("No such fragment type");
                        }
                    }
                    MainFragment fragment = fragments.get(i);
                    switch (fragment.getType()) {
                        case ATTACH_DETACH:
                            fragmentTransaction.attach(fragment);
                            break;
                        case SHOW_HIDE:
                            fragmentTransaction.show(fragment);
                            break;
                        default:
                            throw new NoSuchFieldError("No such fragment type");
                    }
                    fragmentTransaction.commit();
                    break;
                }
            }
        });
    }

    @Nullable
    public FlagButton getCurrentButton(){
        for(int i = 0; i < layout.getChildCount(); i++){
            FlagButton button = (FlagButton) layout.getChildAt(i);
            if(button.isCurrent()){
                return button;
            }
        }
        return null;
    }

    public ArrayList<MainFragment> getFragments() {
        return fragments;
    }

    public void setCurrentButton(int currentButton){
        FlagButton prevButton = getCurrentButton();
        if(prevButton != null){
            prevButton.setTextColor(defaultTextColor);
            prevButton.setStrokeColor(ColorStateList.valueOf(defaultTextColor));
            prevButton.setClickable(true);
            prevButton.setCurrent(false);
        }
        FlagButton button = (FlagButton) layout.getChildAt(currentButton);
        button.setTextColor(selectedTextColor);
        button.setStrokeColor(ColorStateList.valueOf(selectedTextColor));
        button.setClickable(false);
        button.setCurrent(true);
    }
}

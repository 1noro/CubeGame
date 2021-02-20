package net.a3do.cubegame.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private static UserPreferences userpreferences = null;

    public static UserPreferences getInstance() {
        if (userpreferences == null) {
            userpreferences = new UserPreferences();
        }
        return userpreferences;
    }

    public void setNewScore(Context context, int level, int new_score) {
        SharedPreferences.Editor editor = context.getSharedPreferences("cube_game_preferences", Context.MODE_PRIVATE).edit();
        editor.putInt("bestScore" + level, new_score);
        editor.apply();
    }

    public int getBestScore(Context context, int level) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cube_game_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("bestScore" + level, 0);
    }

}

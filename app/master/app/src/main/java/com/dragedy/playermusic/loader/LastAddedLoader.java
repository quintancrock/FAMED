package com.dragedy.playermusic.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.dragedy.playermusic.model.Song;
import com.dragedy.playermusic.util.PreferenceUtil;

import java.util.ArrayList;

public class LastAddedLoader {

    @NonNull
    public static ArrayList<Song> getLastAddedSongs(@NonNull Context context) {
        return SongLoader.getSongs(makeLastAddedCursor(context));
    }

    public static Cursor makeLastAddedCursor(@NonNull final Context context) {
        long fourWeeksAgo = (System.currentTimeMillis() / 1000) - (4 * 3600 * 24 * 7);
        // possible saved timestamp caused by user "clearing" the last added playlist
        long cutoff = PreferenceUtil.getInstance(context).getLastAddedCutOffTimestamp() / 1000;
        if (cutoff < fourWeeksAgo) {
            cutoff = fourWeeksAgo;
        }

        return SongLoader.makeSongCursor(
                context,
                MediaStore.Audio.Media.DATE_ADDED + ">?",
                new String[]{String.valueOf(cutoff)},
                MediaStore.Audio.Media.DATE_ADDED + " DESC");
    }
}

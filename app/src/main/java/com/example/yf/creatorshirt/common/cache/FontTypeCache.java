package com.example.yf.creatorshirt.common.cache;

import android.content.Context;

import com.example.yf.creatorshirt.mvp.model.detaildesign.TextEntity;
import com.example.yf.creatorshirt.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfang on 2017/8/23.
 */

public class FontTypeCache {
    private Context context;

    public FontTypeCache(Context _context) {
        this.context = _context;
    }

    public void saveHistoryWord(List<TextEntity> data) {
        write(getCacheFilePath(), data);
    }

    public List<TextEntity> getHistoryWord() {
        return read(getCacheFilePath());
    }

    private String getCacheFilePath() {
        return FileUtils.getCachePath(context) + File.separator + "cache_history_hotword";
    }

    private void write(String fileName, List<TextEntity> _data) {
        if (_data == null) {
            return;
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(_data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<TextEntity> read(String fileName) {
        List<TextEntity> data = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            data = new ArrayList<>();
            data = (List<TextEntity>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public void clearCache() {
        FileUtils.deleteFile(new File(getCacheFilePath()));
    }
}

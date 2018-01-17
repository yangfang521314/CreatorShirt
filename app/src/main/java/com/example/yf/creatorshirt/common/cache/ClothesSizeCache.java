package com.example.yf.creatorshirt.common.cache;

import android.content.Context;

import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangfang on 2018/1/14.
 */

public class ClothesSizeCache {
    private Context context;

    public ClothesSizeCache(Context _context) {
        this.context = _context;
    }

    public void saveUserInfo(Map<String, List<ClothesSize>> datas) {
        write(getCacheFilePath(), datas);
    }

    public Map<String, List<ClothesSize>> getUserInfo() {
        return read(getCacheFilePath());
    }

    /**
     * 本地的缓存
     *
     * @return
     */
    private String getCacheFilePath() {
        return context.getCacheDir().getPath() + File.separator
                + "clothessize";
    }

    /**
     * 所属APP下的存储目录
     *
     * @return
     */
    private String getFilesDir() {
        return context.getFilesDir().getPath() + File.separator
                + "clothessize";
    }


    private void write(String fileName, Map<String, List<ClothesSize>> _datas) {
        if (_datas == null) {
            return;
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(_datas);
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

    @SuppressWarnings("unchecked")
    private Map<String, List<ClothesSize>> read(String fileName) {
        Map<String, List<ClothesSize>> datas = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            datas = new HashMap<>();
            datas = (Map<String, List<ClothesSize>>) ois.readObject();
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
        return datas;
    }
}

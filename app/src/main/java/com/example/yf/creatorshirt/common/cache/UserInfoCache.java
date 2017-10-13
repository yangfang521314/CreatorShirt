package com.example.yf.creatorshirt.common.cache;

import android.content.Context;

import com.example.yf.creatorshirt.mvp.model.LoginBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class UserInfoCache {
    private Context context;

    public UserInfoCache(Context _context) {
        this.context = _context;
    }

    public void saveUserInfo(LoginBean datas) {
        write(getFilesDir(), datas);
    }

    public LoginBean getUserInfo() {
        return read(getFilesDir());
    }

    /**
     * 本地的缓存
     *
     * @return
     */
    private String getCacheFilePath() {
        return context.getCacheDir().getPath() + File.separator
                + "cache_userinfo";
    }

    /**
     * 所属APP下的存储目录
     *
     * @return
     */
    private String getFilesDir() {
        return context.getFilesDir().getPath() + File.separator
                + "cache_userinfo";
    }


    private void write(String fileName, LoginBean _datas) {
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
    private LoginBean read(String fileName) {
        LoginBean datas = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            datas = new LoginBean();
            datas = (LoginBean) ois.readObject();
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

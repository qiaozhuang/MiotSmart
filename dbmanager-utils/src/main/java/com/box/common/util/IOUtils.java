/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.box.common.util;

import android.database.Cursor;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;

/**
 * Author: wyouflf
 * Date: 13-8-26
 * Time: 下午6:02
 */
public class IOUtils {

    private IOUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable e) {
            }
        }
    }

    public static final String APPLICATION_FOLDER = "MiotAllHouse";
    private static final String CACHE_FOLDER = "cache";
    private static final String CACHE_WEB_FOLDER = "WebCache";
    public static final String CACHE_XWALK_LIB = "DynamicLibrary";

    /**
     * Get the application folder on the SD Card. Create it if not present.
     *
     * @return The application folder.
     */
    public static File getApplicationFolder() {
        File root = Environment.getExternalStorageDirectory();
        if (root.canWrite()) {
            File folder = new File(root, APPLICATION_FOLDER);

            if (!folder.exists()) {
                folder.mkdir();
            }
            return folder;

        } else {
            return null;
        }
    }

    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    public static void clearApplicationFolder() {

        File root = Environment.getExternalStorageDirectory();

        if (root.canWrite()) {

            File folder = new File(root, APPLICATION_FOLDER);

            if (folder.exists()) {
                RecursionDeleteFile(folder);
            }

        }
    }

    /**
     * Get the application download folder on the SD Card. Create it if not
     * present.
     *
     * @return The application download folder.
     */
    public static File getCacheXwalkLibFolder() {
        File root = getApplicationFolder();

        if (root != null) {

            File folder = new File(root, CACHE_XWALK_LIB);

            if (!folder.exists()) {
                folder.mkdir();
            }

            return folder;

        } else {
            return null;
        }
    }

    public static File getCacheFolder() {
        File root = getApplicationFolder();

        if (root != null) {

            File folder = new File(root, CACHE_FOLDER);

            if (!folder.exists()) {
                folder.mkdir();
            }

            return folder;

        } else {
            return null;
        }
    }

    public static File getWebCacheFolder() {
        File root = getApplicationFolder();

        if (root != null) {

            File folder = new File(root, CACHE_WEB_FOLDER);

            if (!folder.exists()) {
                folder.mkdir();
            }

            return folder;

        } else {
            return null;
        }
    }

}

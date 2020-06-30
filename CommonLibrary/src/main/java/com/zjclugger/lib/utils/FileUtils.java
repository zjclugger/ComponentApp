package com.zjclugger.lib.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件操作辅助类
 *
 * @author King.Zi
 * @Title: FileUtils
 * @Date: 2016-6-3
 * @Description:
 */
@SuppressLint("SdCardPath")
public class FileUtils {
    public static final int FILENAME_MAX_LENGTH = 255;
    public static final String DOWNLOAD_DIR = "/mnt/sdcard/download/";
    private static final String TAG = "fileUtils";

    private FileUtils() {
    }

    public static boolean isSdcardExist() {
        boolean isExist = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            isExist = true;
        }
        return isExist;
    }

    public static boolean isSdcardFull() {
        return getFreeSpace() <= 10 * 1024 * 1024;
    }

    public static boolean createDir(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return false;
        }
        if (isSdcardExist()) {
            try {
                File dir = new File(dirPath.trim());
                if (!dir.exists()) {
                    return dir.mkdirs();
                }
                return true;
            } catch (Exception e) {
                Log.e(TAG, "create dir is failed!!, ex is " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static long getAllSize() {
        if (isSdcardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getBlockCount();
            return availableBlocks * blockSize;
        }
        return 0;
    }

    @SuppressWarnings("deprecation")
    public static long getFreeSpace() {
        long freeSpace = 0;
        if (isSdcardExist()) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                freeSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return freeSpace;
    }

    public static File createFile(String dir, String fileName, long remainSpace, long fileSize) {
        File file = null;
        if (isSdcardExist()) {
            file = new File(dir, fileName);
            if (!file.exists()) {
                try {
                    if (remainSpace > fileSize) {
                        file.createNewFile();
                    } else {
                        Log.e(TAG, "Remain space is not enough");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "create file has exception the message is " + e.getMessage());
                }
            }
        }
        return file;
    }

    public static boolean deleteAllFile(File file) {
        boolean flag = false;
        try {
            if (file.isFile()) {
                flag = deleteFile(file);
                Log.d(TAG, "delete file " + file.getName() + " is successed? ==" + flag);
            } else if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    flag = deleteFile(file);
                    Log.d(TAG, "delete file " + file.getName() + " is successed? ==" + flag);
                } else {
                    for (int i = 0; i < childFiles.length; i++) {
                        flag = deleteAllFile(childFiles[i]);
                        Log.d(TAG, "delete file " + childFiles[i].getName() + " is successed? =="
                                + flag);
                    }
                }

                flag = deleteFile(file);
            }

            return flag;
        } catch (Exception e) {
            Log.e(TAG, "Exe deleteAllFile(file) " + "has exception=" + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteFile(File file) {
        boolean flag = false;
        try {
            if (file.exists()) {
                final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                file.renameTo(to);
                flag = to.delete();
                Log.i(TAG, "Delete file " + file.getName() + " is " + flag);
            } else {
                Log.d(TAG, "Delete file " + file.getName() + " but it is not exists");
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exe delete(file) " + "has exception=" + e.getMessage());
        }

        return flag;
    }

    public static boolean installAPK(String apkPath) throws RemoteException {
        chmodFile(apkPath);
        String[] args = {
                "pm", "install", "-r", apkPath
        };
        String midRes = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            midRes = new String(data);
            if (midRes.contains("Success")) {
                return true;
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                    errIs = null;
                }
                if (inIs != null) {
                    inIs.close();
                    inIs = null;
                }
                if (baos != null) {
                    baos.close();
                    baos = null;
                }
                midRes = null;
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * @param apkPath
     * @return String
     * @throws RemoteException
     * @Title: chmodupgradeAppEffect
     * @Description: chmod apk's upgrade file path
     */
    public static String chmodFile(String apkPath) throws RemoteException {
        File mFile = new File(apkPath);
        String result = "";
        String midResult = "";
        if (mFile.exists()) {
            Log.i(TAG, "chmod upgrade process");
            String[] args = {
                    "chmod", "-R", "777", apkPath
            };
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = null;
            InputStream errIs = null;
            InputStream inIs = null;
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                int read = -1;
                process = processBuilder.start();
                errIs = process.getErrorStream();
                while ((read = errIs.read()) != -1) {
                    baos.write(read);
                }
                baos.write('\n');
                inIs = process.getInputStream();
                while ((read = inIs.read()) != -1) {
                    baos.write(read);
                }
                byte[] data = baos.toByteArray();
                midResult = new String(data);
                result = midResult;
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            } finally {
                try {
                    if (errIs != null) {
                        errIs.close();
                        errIs = null;
                    }
                    if (inIs != null) {
                        inIs.close();
                        inIs = null;
                    }
                    if (baos != null) {
                        baos.close();
                        baos = null;
                    }
                    midResult = null;
                } catch (IOException e) {
                    Log.i(TAG, e.toString());
                }
                if (process != null) {
                    process.destroy();
                }
            }
        } else {
            Log.i(TAG, "the chmod file is not exist=" + apkPath);
        }
        return result;
    }

    public static boolean isDir(File file) {
        boolean b = file != null && file.exists();
        return b && file.isDirectory();
    }

    public static List<File> listFilesInDir(File dir, boolean isRecursive) {
        if (!isDir(dir)) return null;
        if (isRecursive) return listFilesInDir(dir, true);
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            Collections.addAll(list, files);
        }
        return list;
    }

    /**
     * 移动文件
     *
     * @param srcFileName 源文件完整路径
     * @param destDirName 目的目录完整路径
     * @return 文件移动成功返回 {@code true}，否则返回 {@code false}
     */
    public static boolean moveFile(String srcFileName, String destDirName) {

        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
    }

    /**
     * 移动目录
     *
     * @param srcDirName  源目录完整路径
     * @param destDirName 目的目录完整路径
     * @return 目录移动成功返回true，否则返回false
     */
    public static boolean moveDirectory(String srcDirName, String destDirName) {

        File srcDir = new File(srcDirName);
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }

        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        /**
         * 如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹
         * 注意移动文件夹时保持文件夹的树状结构
         */
        File[] sourceFiles = srcDir.listFiles();
        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile())
                moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
            else if (sourceFile.isDirectory())
                moveDirectory(sourceFile.getAbsolutePath(),
                        destDir.getAbsolutePath() + File.separator + sourceFile.getName());
            else
                ;
        }
        return srcDir.delete();
    }

    public static String getFileName(String path) {
        if (!TextUtils.isEmpty(path)) {
            return new File(path).getName();
        }
        return "";
    }

    public static long getFileSize(String filePath) {
        return getFileLength(new File(filePath));
    }

    public static long getFileSize(File file) {
        FileInputStream inputStream = null;
        FileChannel fileChannel = null;
        long fileSize = -1;
        try {
            if (null != file && file.exists() && file.isFile()) {
                inputStream = new FileInputStream(file);
                fileChannel = inputStream.getChannel();
                fileSize = fileChannel.size();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file " + file + " not found");
        } catch (IOException e) {
            Log.e(TAG, "file " + file + " io exception");
        } finally {
            if (null != fileChannel) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return fileSize;
    }

    public static long getFileLength(File file) {
        //同getFileSize(FileChannel)方法，不可使用FileInputStream.available方法，大文件时会出错.
        if (null != file && file.exists() && file.isFile()) {
            return file.length();
        }
        return 0;
    }
}

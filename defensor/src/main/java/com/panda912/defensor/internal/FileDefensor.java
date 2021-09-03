package com.panda912.defensor.internal;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
 * Created by panda on 2021/8/30 16:02
 */
public class FileDefensor {

  public static File newFile(String pathname) {
    try {
      return new File(pathname);
    } catch (NullPointerException e) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "new File(pathname) throw NullPointerException due to pathname is null.", e);
      return new File("");
    }
  }

  public static File newFile(String parent, String child) {
    try {
      return new File(parent, child);
    } catch (NullPointerException e) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "new File(parent,child) throw NullPointerException due to child is null.", e);
      return new File(parent, "");
    }
  }

  public static File newFile(File parent, String child) {
    try {
      return new File(parent, child);
    } catch (NullPointerException e) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "new File(parent,child) throw NullPointerException due to child is null.", e);
      return new File(parent, "");
    }
  }

  public static File newFile(URI uri) {
    if (uri == null) {
      String error = "new File(URI uri) throw NullPointerException due to uri is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return new File("");
    }
    try {
      return new File(uri);
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "new File(URI uri) throw IllegalArgumentException", e);
      return new File("");
    }
  }

  public static String getName(File file) {
    if (file != null) {
      return file.getName();
    }
    String error = "File.getName() throw NullPointerException due to file is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return "";
  }

  public static String getParent(File file) {
    if (file != null) {
      return file.getParent();
    }
    String error = "File.getParent() throw NullPointerException due to file is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return null;
  }

  public static File getParentFile(File file) {
    if (file != null) {
      return file.getParentFile();
    }
    String error = "File.getParentFile() throw NullPointerException due to file is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return null;
  }

  public static String getPath(File file) {
    if (file != null) {
      return file.getPath();
    }
    String error = "File.getPath() throw NullPointerException due to file is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return "";
  }

  public static boolean isAbsolute(File file) {
    if (file != null) {
      return file.isAbsolute();
    }
    String error = "File.isAbsolute() throw NullPointerException due to file is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return false;
  }

  public static String getAbsolutePath(File file) {
    if (file != null) {
      return file.getAbsolutePath();
    }
    String error = "File.getAbsolutePath() throw NullPointerException due to file is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return "";
  }

  public static File getAbsoluteFile(File file) {
    if (file == null) {
      String error = "File.getAbsoluteFile() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return file.getAbsoluteFile();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.getAbsoluteFile() throw SecurityException.", e);
      return null;
    }
  }

  public static String getCanonicalPath(File file) throws IOException {
    if (file == null) {
      String error = "File.getCanonicalPath() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return file.getCanonicalPath();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.getCanonicalPath() throw SecurityException.", e);
      return "";
    }
  }

  public static File getCanonicalFile(File file) throws IOException {
    if (file == null) {
      String error = "File.getCanonicalFile() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return file.getCanonicalFile();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.getCanonicalFile() throw SecurityException.", e);
      return null;
    }
  }

  public static URL toURL(File file) throws MalformedURLException {
    if (file == null) {
      String error = "File.toURL() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    return file.toURL();
  }

  public static URI toURI(File file) {
    if (file == null) {
      String error = "File.toURI() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return file.toURI();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.toURI() throw SecurityException.", e);
      return null;
    }
  }

  public static boolean canRead(File file) {
    if (file == null) {
      String error = "File.canRead() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.canRead();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.canRead() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean canWrite(File file) {
    if (file == null) {
      String error = "File.canWrite() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.canWrite();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.canWrite() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean exists(File file) {
    if (file == null) {
      String error = "File.exists() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.exists();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.exists() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean isDirectory(File file) {
    if (file == null) {
      String error = "File.isDirectory() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.isDirectory();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.isDirectory() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean isFile(File file) {
    if (file == null) {
      String error = "File.isFile() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.isFile();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.isFile() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean isHidden(File file) {
    if (file == null) {
      String error = "File.isHidden() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.isHidden();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.isHidden() throw SecurityException.", e);
      return false;
    }
  }

  public static long lastModified(File file) {
    if (file == null) {
      String error = "File.lastModified() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0L;
    }
    try {
      return file.lastModified();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.lastModified() throw SecurityException.", e);
      return 0L;
    }
  }

  public static long length(File file) {
    if (file == null) {
      String error = "File.length() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0L;
    }
    try {
      return file.length();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.length() throw SecurityException.", e);
      return 0L;
    }
  }

  public static boolean createNewFile(File file) throws IOException {
    if (file == null) {
      String error = "File.createNewFile() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.createNewFile();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.createNewFile() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean delete(File file) {
    if (file == null) {
      String error = "File.delete() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.delete();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.delete() throw SecurityException.", e);
      return false;
    }
  }

  public static void deleteOnExit(File file) {
    if (file == null) {
      String error = "File.deleteOnExit() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      file.deleteOnExit();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.deleteOnExit() throw SecurityException.", e);
    }
  }

  public static String[] list(File file) {
    if (file == null) {
      String error = "File.list() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return new String[0];
    }
    try {
      return file.list();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.list() throw SecurityException.", e);
      return new String[0];
    }
  }

  public static String[] list(File file, FilenameFilter filter) {
    if (file == null) {
      String error = "File.list(filter) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return new String[0];
    }
    try {
      return file.list(filter);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.list(filter) throw SecurityException.", e);
      return new String[0];
    }
  }

  public static File[] listFiles(File file) {
    if (file == null) {
      String error = "File.listFiles() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return new File[0];
    }
    try {
      return file.listFiles();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.listFiles() throw SecurityException.", e);
      return new File[0];
    }
  }

  public static File[] listFiles(File file, FilenameFilter filter) {
    if (file == null) {
      String error = "File.listFiles(filter) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return new File[0];
    }
    try {
      return file.listFiles(filter);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.listFiles(filter) throw SecurityException.", e);
      return new File[0];
    }
  }

  public static File[] listFiles(File file, FileFilter filter) {
    if (file == null) {
      String error = "File.listFiles(filter) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return new File[0];
    }
    try {
      return file.listFiles(filter);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.listFiles(filter) throw SecurityException.", e);
      return new File[0];
    }
  }

  public static boolean mkdir(File file) {
    if (file == null) {
      String error = "File.mkdir() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.mkdir();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.mkdir() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean mkdirs(File file) {
    if (file == null) {
      String error = "File.mkdirs() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.mkdirs();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.mkdirs() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean renameTo(File file, File dest) {
    if (file == null) {
      String error = "File.renameTo(dest) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.renameTo(dest);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.renameTo(dest) throw SecurityException.", e);
      return false;
    } catch (NullPointerException e) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "File.renameTo(dest) throw NullPointerException.", e);
      return false;
    }
  }

  public static boolean setLastModified(File file, long time) {
    if (file == null) {
      String error = "File.setLastModified(time) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.setLastModified(time);
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "File.setLastModified(" + time + ") throw SecurityException.", e);
      return false;
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.setLastModified(" + time + ") throw SecurityException.", e);
      return false;
    }
  }

  public static boolean setReadOnly(File file) {
    if (file == null) {
      String error = "File.setReadOnly() throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.setReadOnly();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.setReadOnly() throw SecurityException.", e);
      return false;
    }
  }

  public static boolean setWritable(File file, boolean writable, boolean ownerOnly) {
    if (file == null) {
      String error = "File.setWritable(writable,ownerOnly) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.setWritable(writable, ownerOnly);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.setWritable(" + writable + ", " + ownerOnly + ") throw SecurityException.", e);
      return false;
    }
  }

  public static boolean setWritable(File file, boolean writable) {
    return setWritable(file, writable, true);
  }

  public static boolean setReadable(File file, boolean readable, boolean ownerOnly) {
    if (file == null) {
      String error = "File.setReadable(readable,ownerOnly) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.setReadable(readable, ownerOnly);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.setReadable(" + readable + ", " + ownerOnly + ") throw SecurityException.", e);
      return false;
    }
  }

  public static boolean setReadable(File file, boolean readable) {
    return setReadable(file, readable, true);
  }

  public static boolean setExecutable(File file, boolean executable, boolean ownerOnly) {
    if (file == null) {
      String error = "File.setExecutable(executable,ownerOnly) throw NullPointerException due to file is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.setExecutable(executable, ownerOnly);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.setExecutable(" + executable + ", " + ownerOnly + ") throw SecurityException.", e);
      return false;
    }
  }

  public static boolean setExecutable(File file, boolean executable) {
    return setExecutable(file, executable, true);
  }

  public static boolean canExecute(File file) {
    if (file == null) {
      String error = "File.canExecute() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    try {
      return file.canExecute();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.canExecute() throw SecurityException.", e);
      return false;
    }
  }

  public static long getTotalSpace(File file) {
    if (file == null) {
      String error = "File.getTotalSpace() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0L;
    }
    try {
      return file.getTotalSpace();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.getTotalSpace() throw SecurityException.", e);
      return 0L;
    }
  }

  public static long getFreeSpace(File file) {
    if (file == null) {
      String error = "File.getFreeSpace() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0L;
    }
    try {
      return file.getFreeSpace();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.getFreeSpace() throw SecurityException.", e);
      return 0L;
    }
  }

  public static long getUsableSpace(File file) {
    if (file == null) {
      String error = "File.getUsableSpace() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0L;
    }
    try {
      return file.getUsableSpace();
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "File.getUsableSpace() throw SecurityException.", e);
      return 0L;
    }
  }

  public static String toString(File file) {
    if (file == null) {
      String error = "File.toString() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return "";
    }
    return file.toString();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static Path toPath(File file) {
    if (file == null) {
      String error = "File.toPath() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return file.toPath();
    } catch (InvalidPathException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "File.toPath() throw InvalidPathException", e);
      return null;
    }
  }

}

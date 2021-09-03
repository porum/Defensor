package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.io.File;
import java.net.URI;

/**
 * Created by panda on 2021/9/1 16:32
 */
public class SafeFile extends File {

  public SafeFile(String pathname) {
    super(pathname == null ? "" : pathname);
    if (pathname == null) {
      String err = "new File(String pathname) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public SafeFile(String parent, String child) {
    super(parent, child == null ? "" : child);
    if (child == null) {
      String err = "new File(String parent, String child) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public SafeFile(File parent, String child) {
    super(parent, child == null ? "" : child);
    if (child == null) {
      String err = "new File(File parent, String child) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public SafeFile(URI uri) {
    super(uri);
  }
}

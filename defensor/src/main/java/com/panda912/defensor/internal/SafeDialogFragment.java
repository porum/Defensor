package com.panda912.defensor.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/8 09:08
 */
public class SafeDialogFragment extends DialogFragment {

  @Override
  public void dismiss() {
    try {
      super.dismiss();
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.DialogException, "DialogFragment.dismiss() throw IllegalArgumentException", e);
    }
  }

  @Override
  public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
    try {
      return super.show(transaction, tag);
    } catch (IllegalStateException e) {
      CrashDefensor.onCrash(ErrorCode.DialogException, "DialogFragment.show(FragmentTransaction transaction, String tag) throw IllegalStateException", e);
    }
    return -1;
  }

  @Override
  public void show(@NonNull FragmentManager manager, @Nullable String tag) {
    try {
      super.show(manager, tag);
    } catch (IllegalStateException e) {
      CrashDefensor.onCrash(ErrorCode.DialogException, "DialogFragment.show(FragmentManager manager, String tag) throw IllegalStateException", e);
    }
  }
}

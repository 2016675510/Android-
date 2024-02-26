// Generated code from Butter Knife. Do not modify!
package com.example.administrator.test2.ActivityView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.administrator.test2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentControlActivity_ViewBinding implements Unbinder {
  private FragmentControlActivity target;

  @UiThread
  public FragmentControlActivity_ViewBinding(FragmentControlActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FragmentControlActivity_ViewBinding(FragmentControlActivity target, View source) {
    this.target = target;

    target.button1 = Utils.findRequiredViewAsType(source, R.id.bt1, "field 'button1'", ImageButton.class);
    target.button2 = Utils.findRequiredViewAsType(source, R.id.bt2, "field 'button2'", ImageButton.class);
    target.button3 = Utils.findRequiredViewAsType(source, R.id.notebook1, "field 'button3'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentControlActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.button1 = null;
    target.button2 = null;
    target.button3 = null;
  }
}

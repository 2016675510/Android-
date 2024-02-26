// Generated code from Butter Knife. Do not modify!
package com.example.administrator.test2.ActivityView.FragmentView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.administrator.test2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListNoteActivity_ViewBinding implements Unbinder {
  private ListNoteActivity target;

  private View view2131296284;

  @UiThread
  public ListNoteActivity_ViewBinding(final ListNoteActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.addNote, "field 'addnot' and method 'onViewClicked'");
    target.addnot = Utils.castView(view, R.id.addNote, "field 'addnot'", ImageButton.class);
    view2131296284 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ListNoteActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.addnot = null;

    view2131296284.setOnClickListener(null);
    view2131296284 = null;
  }
}

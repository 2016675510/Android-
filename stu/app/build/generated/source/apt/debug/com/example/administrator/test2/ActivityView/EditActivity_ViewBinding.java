// Generated code from Butter Knife. Do not modify!
package com.example.administrator.test2.ActivityView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.administrator.test2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditActivity_ViewBinding implements Unbinder {
  private EditActivity target;

  private View view2131296330;

  private View view2131296399;

  @UiThread
  public EditActivity_ViewBinding(EditActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EditActivity_ViewBinding(final EditActivity target, View source) {
    this.target = target;

    View view;
    target.timeYear = Utils.findRequiredViewAsType(source, R.id.timeYear, "field 'timeYear'", TextView.class);
    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", TextView.class);
    target.noteContent = Utils.findRequiredViewAsType(source, R.id.notepad_content, "field 'noteContent'", EditText.class);
    target.noteTitle = Utils.findRequiredViewAsType(source, R.id.noteTitle, "field 'noteTitle'", EditText.class);
    target.content_num = Utils.findRequiredViewAsType(source, R.id.num, "field 'content_num'", TextView.class);
    view = Utils.findRequiredView(source, R.id.delete, "method 'onClick'");
    view2131296330 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.note_add, "method 'onClick'");
    view2131296399 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EditActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.timeYear = null;
    target.time = null;
    target.noteContent = null;
    target.noteTitle = null;
    target.content_num = null;

    view2131296330.setOnClickListener(null);
    view2131296330 = null;
    view2131296399.setOnClickListener(null);
    view2131296399 = null;
  }
}

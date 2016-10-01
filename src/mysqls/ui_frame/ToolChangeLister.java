package mysqls.ui_frame;

/**
 * Created by 长宏 on 2016/10/1 0001.
 * 点击toolbar的时候发布的事件，用来让其他面板更新
 */
public interface ToolChangeLister {
    /*被点击的item*/
    void onchange(Toolitem toolitem);
}

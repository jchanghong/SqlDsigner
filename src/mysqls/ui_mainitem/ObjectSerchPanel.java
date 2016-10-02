package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;

import javax.swing.*;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class ObjectSerchPanel extends JPanel implements ConnectINFOListener{
    private static ObjectSerchPanel me=null;
    public static ObjectSerchPanel getInstance() {
        if (me == null) {
            me = new ObjectSerchPanel();
        }
        return me;
    }

    private ObjectSerchPanel() {

    }

    @Override
    public void onchange(String name, Object news, Object oldies) {

        if (name.equals(ConnectINFO.DATABASENAME)) {

        }

    }
}

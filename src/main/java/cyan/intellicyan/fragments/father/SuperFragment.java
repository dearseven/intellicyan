package cyan.intellicyan.fragments.father;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by wx on 2017/3/12.
 */

public class SuperFragment extends Fragment {
    protected View mView;

    protected <T> T findAnyView(int id) {
        return (T) mView.findViewById(id);
    }

}

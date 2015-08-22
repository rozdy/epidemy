package sorry.no.domain.test_project.ui.options;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;


public class OptionsListFragment extends ListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {
        void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    public OptionsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(OptionsContent.createOptionsListAdapter(getActivity()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(OptionsContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
    }
}

package assignment02.csc214.project2.Social_Network;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewUserFragment extends Fragment {


    public ViewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_user, container, false);
    }

}

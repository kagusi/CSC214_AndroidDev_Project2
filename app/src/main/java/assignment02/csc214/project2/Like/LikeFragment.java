package assignment02.csc214.project2.Like;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment {


    public LikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_like, container, false);
    }

}

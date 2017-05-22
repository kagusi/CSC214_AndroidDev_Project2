package assignment02.csc214.project2.Comment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private EditText mCommentEdiText;
    private Button mCommentButton;


    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        return view;
    }

}

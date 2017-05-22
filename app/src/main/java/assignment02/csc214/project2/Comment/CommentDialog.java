package assignment02.csc214.project2.Comment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Comment;
import assignment02.csc214.project2.Models.Feed;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/8/2017.
 */

public class CommentDialog extends DialogFragment {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_FEEDID = "feedid";
    private static final String KEY_NUMBEROFCOMMENTS = "numcoments";

    private RecyclerView mRecyclerView;
    private EditText mCommentEdiText;
    private Button mCommentButton;

    private String feedID;
    private String username;
    private int mNumberOfComments;

    List<Comment> commentList;

    Bundle args;

    public CommentDialog()
    {

    }

    public static CommentDialog newInstance(String username, String feedID, int numberOfComments)
    {
        Bundle args = new Bundle();
        args.putString(KEY_USERNAME, username);
        args.putString(KEY_FEEDID, feedID);
        args.putInt(KEY_NUMBEROFCOMMENTS, numberOfComments);

        CommentDialog dialog = new CommentDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_comment, null);
        args = getArguments();
        feedID = args.getString(KEY_FEEDID);
        username = args.getString(KEY_USERNAME);
        mNumberOfComments = args.getInt(KEY_NUMBEROFCOMMENTS);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.CommentRecycler_view);
        mCommentEdiText = (EditText)view.findViewById(R.id.Comment_EnterPostEdiTextView);
        mCommentButton = (Button)view.findViewById(R.id.Comment_CommentButton);

        String[] feedid = new String[1];
        feedid[0] = feedID;
        commentList = ModelQueryManager.get(getContext().getApplicationContext()).getComment(feedid);

        mRecyclerView.setAdapter(new CommentRecyclerAdapter(commentList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mCommentEdiText.getText().toString()))
                {
                    Toast.makeText(getActivity(), "COMMENT CAN'T BE EMPTY!", Toast.LENGTH_LONG).show();

                }
                else
                {
                    String comment = mCommentEdiText.getText().toString();
                    Comment newComment = new Comment(feedID, username, comment);

                    //Create comment
                    ModelQueryManager.get(getContext().getApplicationContext()).createComment(newComment);

                    String[] feedid = new String[1];
                    feedid[0] = feedID;
                    //Update comment list for recycler adapter
                    commentList = ModelQueryManager.get(getContext().getApplicationContext()).getComment(feedid);
                    mRecyclerView.setAdapter(new CommentRecyclerAdapter(commentList));
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    Toast.makeText(getActivity(), "COMMENT POSTED", Toast.LENGTH_LONG).show();

                    mNumberOfComments +=1;
                    Feed feed = new Feed(username, "Welcome", "empty");
                    feed.setFeedID(feedID);
                    feed.setNumberOfComments(mNumberOfComments);
                    ModelQueryManager.get(getActivity().getApplicationContext()).updateFeed2(feed);

                    getTargetFragment().onActivityResult(3, 1, null);
                    //dismiss();
                }

            }
        });


        return new AlertDialog.Builder(getContext())
                .setView(view)
                .create();
    }
}

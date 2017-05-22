package assignment02.csc214.project2.Comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import assignment02.csc214.project2.Models.Comment;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/8/2017.
 */

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentHolder>   {

    private List<Comment> mComments;
    private Context mContext;

    public CommentRecyclerAdapter(List<Comment> comments)
    {
        mComments = comments;
    }


    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_display_recycl_layout, parent, false);
        mContext = parent.getContext();

        CommentHolder holder = new CommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {

        Comment comment = mComments.get(position);
        holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}

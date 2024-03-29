package com.dablaze.notekeeper;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>{

    private final Context fContext;
    private final LayoutInflater fLayoutInflater;
    private final List<NoteInfo> mNotes;

    public NoteRecyclerAdapter(Context Context, List<NoteInfo> notes) {
        fContext = Context;
        mNotes = notes;
        fLayoutInflater = LayoutInflater.from(fContext);
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View itemView = fLayoutInflater.inflate ( R.layout.view_layout_notes, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteInfo note = mNotes.get(position);
        holder.mTextCourse.setText(note.getCourse().getTitle());
        holder.mTextTitle.setText(note.getTitle());
        holder.mCurrentPosition = position;

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mTextCourse;
        public final TextView mTextTitle;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextCourse =  itemView.findViewById(R.id.text_course);
            mTextTitle =  itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(fContext, NoteActivity.class);
                    intent.putExtra(NoteActivity.NOTE_POSITION, mCurrentPosition);
                    fContext.startActivity(intent);
                }
            });
        }
    }
}

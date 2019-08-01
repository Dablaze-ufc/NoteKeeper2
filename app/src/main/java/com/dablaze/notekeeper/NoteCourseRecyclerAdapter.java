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

public class NoteCourseRecyclerAdapter extends RecyclerView.Adapter<NoteCourseRecyclerAdapter.ViewHolder>{

    private final Context fContext;
    private final LayoutInflater fLayoutInflater;
    private final List<CourseInfo> mCourses;

    public NoteCourseRecyclerAdapter(Context Context, List<CourseInfo> courses) {
        fContext = Context;
        mCourses = courses;
        fLayoutInflater = LayoutInflater.from(fContext);
    }
    
    @Override
    public ViewHolder onCreateViewHolder(  ViewGroup parent, int ViewType) {
        View itemView = fLayoutInflater.inflate ( R.layout.view_layout_courses, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        CourseInfo courses = mCourses.get(position);
        holder.mTextCourse.setText(courses.getTitle());
        holder.mCurrentPosition = position;

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mTextCourse;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextCourse =  itemView.findViewById(R.id.text_course);

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

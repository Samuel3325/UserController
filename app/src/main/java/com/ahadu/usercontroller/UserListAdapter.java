package com.ahadu.usercontroller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UsersViewHolder> {

    Context context;
    List<userModel> mData;
    boolean isDark = false;
    DatabaseHelperClass databaseHelperClass ;

    public UserListAdapter(Context context, List<userModel> mData, boolean isDark) {
        this.context = context;
        this.mData = mData;
        this.isDark = isDark;
    }

    public UserListAdapter(Context context, List<userModel> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new UsersViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, final int position) {

        holder.img_user.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_anim));
        holder.container.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
         holder.tv_title.setText(mData.get(position).getFullName());
         holder.tv_content.setText(mData.get(position).getContent());
         holder.tv_date.setText(mData.get(position).getDate());
         holder.img_user.setImageResource(mData.get(position).getUserPhoto());


         databaseHelperClass = new DatabaseHelperClass(context);
         holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

             @Override
             public boolean onLongClick(View v) {
                 userModel uM = mData.get(position);
                 String sth = mData.get(position).getFullName();
                 mData.remove(uM);
                 notifyItemRemoved(position);
                 databaseHelperClass.delete(sth);

                 return false;
             }
         });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title,tv_content,tv_date;
        ImageView img_user;
        RelativeLayout container;


        public UsersViewHolder(@NonNull final View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Profile.class);
                    intent.putExtra("USERNAME",tv_title.getText().toString());
                    context.startActivity(intent);
                }
            });

            if(isDark){
                setDarkTheme();
            }
        }
        private void setDarkTheme(){
            container.setBackgroundResource(R.drawable.card_bg_dark);
        }
    }

}

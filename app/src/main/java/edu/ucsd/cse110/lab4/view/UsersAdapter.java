package edu.ucsd.cse110.lab4.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;

public class UsersAdapter  extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users = Collections.emptyList();
   // private Consumer<User> onLabelClickedHandler;
    private Consumer<User> onDeleteClickedHandler;

//    public void setOnLabelClickedHandler(Consumer<User> onLabelClicked) {
//        this.onLabelClickedHandler = onLabelClicked;
//    }

    public void setOnUserDeleteClickListener(Consumer<User> onDeleteClicked) {
        this.onDeleteClickedHandler = onDeleteClicked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        var user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public long getItemId (int position) {
        return users.get(position).uniqueID.hashCode();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView labelView;
        private final TextView uidView;
        public final View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.labelView = itemView.findViewById(R.id.user_item_label);
            this.uidView = itemView.findViewById(R.id.user_item_uid);
            this.deleteButton = itemView.findViewById(R.id.user_item_delete);
        }

        public void bind(User user) {
            labelView.setText(user.label);
            uidView.setText(user.uniqueID);
            deleteButton.setOnClickListener(v -> onDeleteClickedHandler.accept(user));
        }
    }

    public void setUsers (List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}

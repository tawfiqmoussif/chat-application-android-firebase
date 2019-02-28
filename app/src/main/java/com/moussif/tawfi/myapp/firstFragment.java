package com.moussif.tawfi.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import com.google.firebase.iid.FirebaseInstanceId;
import com.moussif.tawfi.myapp.Adapter.UserAdapter;
import com.moussif.tawfi.myapp.Notification.Token;
import com.moussif.tawfi.myapp.model.Chat;
import com.moussif.tawfi.myapp.model.Chatlist;
import com.moussif.tawfi.myapp.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class firstFragment extends Fragment {
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<Chatlist> usersList;

    public firstFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateToken(FirebaseInstanceId.getInstance().getToken());
        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }
    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (Chatlist chatlist : usersList){
                        if (user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
 /* private void readUsers() {
         reference = FirebaseDatabase.getInstance().getReference("Chats");
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 usersList.clear();
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                     Chat chat=snapshot.getValue(Chat.class);
                     if(chat.getSender().equals(fuser.getUid())){
                         usersList.add(chat.getReceiver());
                     }
                     if(chat.getReceiver().equals(fuser.getUid())){
                         usersList.add(chat.getSender());
                     }
                 }

                 /*myUsers.clear();
                 mUsers.clear();
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     User user = snapshot.getValue(User.class);
                     assert user !=null;
                     assert fuser != null;
                     for (String id : usersList){
                         if (user.getId().equals(id)){
                                 myUsers.add(user);

                         }
                     }


                 }
                 int i=0;
                 for(User user1:myUsers){
                     if(i==0){
                        mUsers.add(user1);
                     }
                     else {
                         for (User user2 : mUsers) {
                             if(user2.getId().equals(user1.getId())){

                             }
                             else{
                                 mUsers.add(user1);
                             }
                         }
                     }
                     i++;
                 }*/
/*

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}

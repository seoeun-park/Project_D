package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.projectd.Dto.ChatDto;
import com.example.projectd.Dto.MemberDto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<MemberDto> memberDtoList;
    private List<ChatDto> chatList;

    private String nickname = LoginActivity.loginDTO.getMember_nickname();
    private String photo_url = LoginActivity.loginDTO.getMember_profile();

    private EditText EditText_Chat;
    private Button Button_send;
    private DatabaseReference myRef;

    LinearLayout toolbar_context;   //툴바를 감싸고 있는 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar_context = findViewById(R.id.toolbar_context);

        Button_send = findViewById(R.id.Button_send);
        EditText_Chat = findViewById(R.id.EditText_Chat);

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = EditText_Chat.getText().toString(); //msg

                if(msg != null) {
                    ChatDto chat = new ChatDto();
                    chat.setPhotoUrl(photo_url);
                    chat.setName(nickname);
                    chat.setMsg(msg);
                    myRef.push().setValue(chat);
                    EditText_Chat.getText().clear();
                    mRecyclerView.scrollToPosition(chatList.size()-1);

                }

            }
        });
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, nickname  );
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //mRecyclerView.scrollToPosition(chatList.size() - 1);
        //mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
        //mRecyclerView.scrollToPosition(mAdapter.getItemCount() -1);

        // Write a message to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();



        //주의사항!!

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatDto chat = snapshot.getValue(ChatDto.class);
                ((ChatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // 뒤로가기 버튼 클릭 시
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
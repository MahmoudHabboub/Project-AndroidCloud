package com.example.coronavirusproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ListMessagesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText edittext_chatbox;
    Button button_chatbox_send;
    MessageListAdapter messageListAdapter;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference parentReference;
    MessageSender messageSender;
    User cu_user;
    User re_user;
    ArrayList<Message> AllMessageList;
    ArrayList<Message>recentMessage;
    String Id_room;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messages);
        toolbar=findViewById(R.id.toolbar_mes);
        setSupportActionBar(toolbar);
        edittext_chatbox = findViewById(R.id.edittext_chatbox);
        recyclerView = findViewById(R.id.reyclerview_message_list);
        button_chatbox_send = findViewById(R.id.button_chatbox_send);
        AllMessageList = new ArrayList<>();
        recentMessage=new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        parentReference = firebaseDatabase.getReference();
        re_user = (User) getIntent().getSerializableExtra("User");
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userName = user.getEmail();
            String userUid = user.getUid();
            cu_user = new User(userName, userUid);
        }
        getSupportActionBar().setTitle(re_user.getEmail());
        Id_room=getIdRoom(cu_user.getUid(),re_user.getUid());
        messageSender = new MessageSender(parentReference.child("chats").child(Id_room));
        messageListAdapter = new MessageListAdapter(getApplicationContext(),cu_user, re_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        button_chatbox_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale( "ar" , "SA" );
                DateFormat df = new SimpleDateFormat("h:mm a",locale);
                String date = df.format(Calendar.getInstance().getTime());
                String msgsender = edittext_chatbox.getText().toString().trim();
                Message message = new Message();
                message.setSender(cu_user.getUid());
                message.setReceiver(re_user.getUid());
                message.setText(msgsender);
                message.setCreatedAt(date);
                message.setRoomId(Id_room);
                messageSender.sendMessage(message);
                edittext_chatbox.setText("");
            }
        });
        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Message>recentMessage) {
                if(AllMessageList.isEmpty()){
                    messageListAdapter.addData(null,recentMessage);
                }else{
                    ArrayList<Message> union = new ArrayList<Message>(AllMessageList);
                    union.addAll(recentMessage);
                    ArrayList<Message> intersection = new ArrayList<Message>(AllMessageList);
                    intersection.retainAll(recentMessage);
                    union.removeAll(intersection);
                    messageListAdapter.addData(union,AllMessageList);
                    AllMessageList.addAll(union);
                }
            }
        });

    }

    public interface MyCallback {
        void onCallback(ArrayList<Message>recentMessage);
    }
    public void readData(final MyCallback myCallback) {
        parentReference.child("chats").child(Id_room).limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recentMessage.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message e = snapshot.getValue(Message.class);
                    recentMessage.add(e);
                }
                myCallback.onCallback(recentMessage);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public static String getIdRoom(String ID_1,String ID_2) {
        int ID=0;
        byte[] ID1 = ID_1.getBytes(Charset.forName("UTF-8"));
        byte[] ID2 = ID_2.getBytes(Charset.forName("UTF-8"));
        for (int i = 0; i < ID1.length; i++) {
            ID+=ID1[i];
        }
        for (int i = 0; i < ID2.length; i++) {
            ID+=ID2[i];
        }
        byte[] data = Integer.toString(ID).getBytes(Charset.forName("UTF-8"));
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
        return base64;
    }
}

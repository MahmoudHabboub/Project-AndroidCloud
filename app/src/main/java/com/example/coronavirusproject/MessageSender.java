package com.example.coronavirusproject;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class MessageSender {
    DatabaseReference chatsReference;

    public MessageSender(DatabaseReference chatsReference) {

        this.chatsReference = chatsReference;
    }

    public boolean sendMessage (Message message) {
        boolean status = false;
            Task task = chatsReference.push().setValue(message);
            if (task.isSuccessful()) {
                status = true;
            }
        return status;
    }
}

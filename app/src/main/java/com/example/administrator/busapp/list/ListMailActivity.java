package com.example.administrator.busapp.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.busapp.R;
import com.example.administrator.busapp.adapter.MailsAdapter;
import com.example.administrator.busapp.datamodels.Mails;
import com.example.administrator.busapp.detail.DetailMailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.mail.Message;

public class ListMailActivity extends AppCompatActivity {

    public static final String MAIL_REC = "com.example.administrator.busapp.mailrec";
    public static final String MAIL_SUB = "com.example.administrator.busapp.mailsub";
    public static final String MAIL_CONTENT = "com.example.administrator.busapp.mailcontent";
    public static final String MAIL_TIME = "com.example.administrator.busapp.mailtime";

    private ImageView imageViewClose;
    private ArrayList<Mails> arrMail;
    private MailsAdapter mailsAdapter;
    private ListView listViewMail;
    private DatabaseReference dbMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mail);

        dbMail = FirebaseDatabase.getInstance().getReference("mails");
        arrMail = new ArrayList<Mails>();

        imageViewClose = (ImageView) findViewById(R.id.imageViewClose);
        listViewMail = (ListView) findViewById(R.id.listViewMails);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListMailActivity.this.onBackPressed();
            }
        });

        listViewMail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListMailActivity.this, DetailMailActivity.class);
                intent.putExtra(MAIL_REC, arrMail.get(position).getRecipident());
                intent.putExtra(MAIL_SUB, arrMail.get(position).getSubject());
                intent.putExtra(MAIL_CONTENT, arrMail.get(position).getContent());
                intent.putExtra(MAIL_TIME, arrMail.get(position).getTime());
                startActivity(intent);
            }
        });
        registerForContextMenu(listViewMail);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menuDelete:
                deleteMail(menuInfo.position);
                break;
        }
        return true;
    }

    private void deleteMail(int position) {
        DatabaseReference dbDel = FirebaseDatabase.getInstance().getReference("mails").child(arrMail.get(position).getId());
        dbDel.removeValue();
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbMail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrMail.clear();

                for (DataSnapshot mailSnap:dataSnapshot.getChildren()) {
                    Mails mails = mailSnap.getValue(Mails.class);

                    arrMail.add(mails);
                }

                mailsAdapter = new MailsAdapter(ListMailActivity.this, R.layout.layout_item_listview_message, arrMail);
                listViewMail.setAdapter(mailsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.InboxViewModel;

public class InboxActivity extends AppCompatActivity {
    private InboxViewModel inboxViewModel;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        this.listView = findViewById(R.id.inbox);
        this.inboxViewModel = ViewModelProviders.of(this).get(InboxViewModel.class);

        this.inboxViewModel.getLiveListEmail().observe(
                this,
                new Observer<List<Email>>() {
                    @Override
                    public void onChanged(List<Email> list_email) {
                        updateInbox();
                    }
                }
        );

        new AsyncTaskInbox().execute();
    }

    private void updateInbox() {
        String[] from = {
                "sender",
                "subject",
                "content_preview"
        };

        int[] to = {
                R.id.sender,
                R.id.subject_email,
                R.id.content_preview
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                this.inboxViewModel.getListEmailInListHash(),
                R.layout.list_email,
                from,
                to
        );

        this.listView.setAdapter(simpleAdapter);
    }

    private class AsyncTaskInbox extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            inboxViewModel.getInboxFromWebService();
            return null;
        }
    }
}

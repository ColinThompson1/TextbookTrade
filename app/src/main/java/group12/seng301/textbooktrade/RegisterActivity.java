package group12.seng301.textbooktrade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Required input text fields
     */
    private TextView cfmPwdView;
    private TextView nameView;
    private AutoCompleteTextView majorView;
    private View registerForm, progressBar;
    private RegisterTask registerTask = null;
    protected boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        // Put back but on action bar.
   //     ActionBar actionBar = this.getActionBar();
   //     actionBar.setHomeButtonEnabled(true);


        // Set up Autocomplete Text View
        majorView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_major);
        String[] majors = new String[Major.values().length];

        int i = 0; // Counter
        for (Major major : Major.values()) {
            majors[i] = major.alais;
            i++;
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, majors);

        majorView.setAdapter(adapter);


        // Get the email and password of user from previous activity.
        Intent intent = getIntent();
        final String pwd = intent.getStringExtra("group12.seng301.LoginActivity.PASSWORD");
        final String email = intent.getStringExtra("group12.seng301.LoginActivity.EMAIL");

        // Initialize views
        cfmPwdView = (EditText) findViewById(R.id.confirm_pwd);
        nameView = (EditText) findViewById(R.id.name);
        registerForm = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);

        // Attempt to register user when button pressed.
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister(pwd, email);
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Get current input and check the validity of it. If the attempt meets basic criteria
     * a Register Task will be made, otherwise the user must try again.
     * @param password
     */
    protected void attemptRegister(String password, String email) {

        if (registerTask != null)
            return;

        success = true;
        View focusview = null;

        // Get the input at time of submission
        final String cfmPwd = cfmPwdView.getText().toString();
        final String name = nameView.getText().toString();
        final String major = majorView.getText().toString();

        // Check if user's major exists.
        if (!majorValid(major)) {
            majorView.setError(major + " is not a valid major");
            focusview = majorView;
            success = false;
        }

        // Check if user's name is valid.
        if (!nameValid(name)) {
            nameView.setError("Name not valid");
            focusview = nameView;
            success = false;
        }

        // Check if password matches.
        if (!cfmPwd.equalsIgnoreCase(password)) {
            cfmPwdView.setError("Password does not match");
            focusview = cfmPwdView;
            success = false;
        }

        // Check if everything was successful.
        if (success) {
            loadingScreen(true);
            registerTask = new RegisterTask(email, cfmPwd, name, major);
            registerTask.execute((Void) null);

        } else {
            // Some error was detected and the user should be prompt to fix the issue.
            focusview.requestFocus();
        }

    }

    /**
     * Check name for validity
     * @param nameToCheck
     */
    private boolean nameValid(String nameToCheck) {
        // Maybe put some criteria here??
        return true;
    }

    /**
     * Changes the view to show the user they are waiting for the app to register
     * their account.
     * @param visable True if the loadingScreen is visable
     */
    private void loadingScreen(final boolean visable) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerForm.setVisibility(visable ? View.GONE : View.VISIBLE);
            registerForm.animate().setDuration(shortAnimTime).alpha(
                    visable ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerForm.setVisibility(visable ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(visable ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    visable ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(visable ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(visable ? View.VISIBLE : View.GONE);
            registerForm.setVisibility(visable ? View.GONE : View.VISIBLE);

        }
    }

    /**
     * Async task to register the user.
     */
    private class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        private String email, pwd, name, major;

        public RegisterTask(String email, String pwd, String name, String major) {
            this.email = email;
            this.pwd = pwd;
            this.name = name;
            this.major = major;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: Register in database


            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            registerTask = null;
            loadingScreen(false);

            if (success) {
                finish();
                // TODO: Launch new Activity
            } else {
                loadingScreen(false);
                // TODO: Popup explaining error registering user.
            }

        }
    }

    /**
     * Check to see if major is legit.
     * @param majorToCheck
     * @return
     */
    protected boolean majorValid(String majorToCheck) {
        for (Major major : Major.values()) {
            if (major.alais.equalsIgnoreCase(majorToCheck))
                return true;
        }
        return false;
    }


   public enum Major {


        COMPUTER_SCIENCE ("Computer Science");



        private final String alais;

        Major(String alais) {
            this.alais = alais;
        }

    }


}

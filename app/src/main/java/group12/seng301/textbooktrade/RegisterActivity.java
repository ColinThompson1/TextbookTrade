package group12.seng301.textbooktrade;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Required input text fields
     */
    private TextView cfmPwdView;
    private TextView nameView;
    private TextView majorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up Autocomplete Text View
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_major);
        // TODO: Finish with autocomplete stuff


        // Get the email and password of user from previous activity.
        Intent intent = getIntent();
        String pwd = intent.getStringExtra("group12.seng301.LoginActivity.PASSWORD");
        String email = intent.getStringExtra("group12.seng301.LoginActivity.EMAIL");


    }

    /**
     * Get current input and check the validity of it.
     * @param password
     */
    private void attemptRegister(String password) {

        boolean success = true;
        View focusview = null;

        // Get the input at time of submission
        final String cfmPwd = cfmPwdView.getText().toString();
        final String name = nameView.getText().toString();
        final String major = majorView.getText().toString();

        if (!cfmPwd.equalsIgnoreCase(password)) {
            cfmPwdView.setError("Password does not match");
            focusview = cfmPwdView;
            success = false;
        }


        // TODO: Finish
    }

    /**
     * Check name for validity
     * @param nameToCheck
     */
    private boolean checkName(String nameToCheck) {
        // Maybe put some criteria here??
        return true;
    }

    // TODO: Method to check validity of majors

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

            // TODO: Finish this


            return null;
        }
    }

    public enum Major {


        ANCIENT_AND_MEDIEVAL_HISTORY ("Ancient and Medieval History"),
        ANTHROPOLOGY_SOCIAL_AND_CULTURAL ("Anthropology, Social and Cultural"),
        ANTHROPOLOGY ("Anthropology"),
        ARCHAEOLOGY ("Archaeology"),
        CANADIAN_STUDIES ("Canadian Studies"),
        COMMUNICATION_AND_CULTURE ("Communication and Culture"),
        COMMUNICATION_AND_MEDIA_STUDIES ("Communication and Media Studies"),
        DEVELOPMENT_STUDIES ("Development Studies"),
        EARTH_SCIENCE ("Earth Science"),
        EAST_ASIAN_LANGUAGE_STUDIES ("East Asian Language Studies"),
        EAST_ASIAN_STUDIES ("East Asian Studies"),
        ECONOMICS ("Economics"),
        ENGLISH ("English"),
        FILM_STUDIES ("Film Studies"),
        FRENCH ("French"),
        GEOGRAPHY ("Geography"),
        GERMAN ("German"),
        GREEK_AND_ROMAN_STUDIES ("Greek and Roman Studies"),
        HISTORY ("History"),
        HISTORY_AND_PHILOSOPHY_OF_SCIENCE ("History and Philosophy of Science"),
        INTERNATIONAL_INDIGENOUS_STUDIES ("International Indigenous Studies"),
        INTERNATIONAL_RELATIONS ("International Relations"),
        ITALIAN_STUDIES ("Italian Studies"),
        LATIN_AMERICAN_STUDIES ("Latin American Studies"),
        LAW_AND_SOCIETY ("Law and Society"),
        LINGUISTICS ("Linguistics"),
        LINGUISTICS_AND_LANGUAGE ("Linguistics and Language"),
        PHILOSOPHY ("Philosophy"),
        POLITICAL_SCIENCE ("Political Science"),
        PSYCHOLOGY ("Psychology"),
        RELIGIOUS_STUDIES ("Religious Studies"),
        RELIGIOUS_STUDIES_AND_APPLIED_ETHICS ("Religious Studies and Applied Ethics"),
        RUSSIAN ("Russian"),
        SCIENCE_TECHNOLOGY_AND_SOCIETY ("Science, Technology and Society"),
        SOCIOLOGY ("Sociology"),
        SPANISH ("Spanish"),
        URBAN_STUDIES ("Urban Studies"),
        WOMENS_STUDIES ("Women’s Studies"),
        COMMUNITY_REHABILITATION ("Community Rehabilitation"),
        BIOINFORMATICS ("Bioinformatics"),
        BIOMEDICAL_SCIENCES ("Biomedical Sciences"),
        HEALTH_AND_SOCIETY ("Health and Society"),
        DOCTOR_OF_MEDICINE ("Doctor of Medicine"),
        ART_HISTORY ("Art History"),
        DANCE ("Dance"),
        DRAMA ("Drama"),
        VISUAL_STUDIES ("Visual Studies"),
        MUSIC ("Music"),
        COMMERCE ("Commerce"),
        HOTEL_AND_RESORT_MANAGEMENT ("Hotel and Resort Management"),
        ATHLETIC_THERAPY ("Athletic Therapy"),
        LEADERSHIP_IN_PEDAGOGY_AND_COACHING ("Leadership in Pedagogy and Coaching"),
        BIOMECHANICS ("Biomechanics"),
        EXERCISE_AND_HEALTH_PHYSIOLOGY ("Exercise and Health Physiology"),
        KINESIOLOGY ("Kinesiology"),
        MIND_SCIENCES_IN_KINESIOLOGY ("Mind Sciences in Kinesiology"),
        LAW ("Law"),
        NURSING ("Nursing"),
        CHEMICAL_ENGINEERING ("Chemical Engineering"),
        CIVIL_ENGINEERING ("Civil Engineering"),
        ELECTRICAL_ENGINEERING ("Electrical Engineering"),
        ENERGY_ENGINEERING ("Energy Engineering"),
        GEOMATICS_ENGINEERING ("Geomatics Engineering"),
        MECHANICAL_ENGINEERING ("Mechanical Engineering"),
        OIL_AND_GAS_ENGINEERING ("Oil and Gas Engineering"),
        SOFTWARE_ENGINEERING ("Software Engineering"),
        ACTUARIAL_SCIENCE ("Actuarial Science"),
        APPLIED_AND_ENVIRONMENTAL_GEOLOGY ("Applied and Environmental Geology"),
        APPLIED_CHEMISTRY ("Applied Chemistry"),
        APPLIED_MATHEMATICS ("Applied Mathematics"),
        ASTROPHYSICS ("Astrophysics"),
        BIOCHEMISTRY ("Biochemistry"),
        BIOLOGICAL_SCIENCES ("Biological Sciences"),
        CELLULAR_MOLECULAR_AND_MICROBIAL_BIOLOGY ("Cellular, Molecular and Microbial Biology"),
        CHEMICAL_PHYSICS ("Chemical Physics"),
        CHEMISTRY ("Chemistry"),
        COMPUTER_SCIENCE ("Computer Science"),
        ECOLOGY ("Ecology"),
        ENVIRONMENTAL_SCIENCE ("Environmental Science"),
        GENERAL_MATHEMATICS ("General Mathematics"),
        GEOLOGY ("Geology"),
        GEOPHYSICS ("Geophysics"),
        NATURAL_SCIENCES ("Natural Sciences"),
        NEUROSCIENCE ("Neuroscience"),
        PHYSICS ("Physics"),
        PLANT_BIOLOGY ("Plant Biology"),
        PURE_MATHEMATICS ("Pure Mathematics"),
        STATISTICS ("Statistics"),
        ZOOLOGY ("Zoology"),
        SOCIAL_WORK ("Social Work"),
        DOCTOR_OF_VETERINARY_MEDICINE ("Doctor of Veterinary Medicine"),
        EDUCATION ("Education");


        private final String alais;

        Major(String alais) {
            this.alais = alais;
        }

    }


}

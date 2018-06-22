package com.example.android.educationalapp;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int trophies = 1;
    int matches = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adjustMatches();
    }

    /**
     * Translate the z axis to an elevation of 8f in 300ms
     * Calls method to make all other CardViews have an elevation of 1f
     *
     * @param view to be translated
     */
    public void raise(View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationZ", 8f);
        anim.setDuration(300);
        anim.start();

        ViewGroup parent = (ViewGroup) view.getParent();
        normal(parent);
    }

    /**
     * Translate the z axis to an elevation of 1f in 150ms
     *
     * @param view to be translated
     */
    private void un_raise(View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationZ", 1f);
        anim.setDuration(150);
        anim.start();
    }

    /**
     * Get CardViews to translate to 1f
     *
     * @param parent ViewGroup to check for CardViews
     */
    private void normal(ViewGroup parent) {
        int count = parent.getChildCount();

        for (int i = 0; i < count; i++) {
            View v = parent.getChildAt(i);
            if (v instanceof CardView
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    && v.getTranslationZ() == 8) {
                un_raise(v);
            }
        }
    }

    /**
     * Check if all the answers provided are correct
     * Add visual cues to inform the user of their wrongs and rights
     *
     * @param view of the button that checks if the answers are correct
     */
    public void checkAnswers(View view) {
        int correctAnswers = 0;

        EditText response1 = findViewById(R.id.answer_1_txt);
        String answer1 = response1.getText().toString();
        RadioButton response3 = findViewById(R.id.radio_brazil);

        CheckBox belgium = findViewById(R.id.check_belgium);
        CheckBox mexico = findViewById(R.id.check_mexico);
        CheckBox sweden = findViewById(R.id.check_sweden);
        CheckBox russia = findViewById(R.id.check_russia);
        CheckBox portugal = findViewById(R.id.check_portugal);
        CheckBox spain = findViewById(R.id.check_spain);

        boolean response4 = !belgium.isChecked()
                && mexico.isChecked()
                && !sweden.isChecked()
                && russia.isChecked()
                && !portugal.isChecked()
                && spain.isChecked();

        TextView question_1 = findViewById(R.id.question_1);
        TextView question_2 = findViewById(R.id.question_2);
        TextView question_3 = findViewById(R.id.question_3);
        TextView question_4 = findViewById(R.id.question_4);
        TextView question_5 = findViewById(R.id.question_5);

        if (answer1.contentEquals("1930")) {
            correctAnswers++;
            question_1.setTextColor(Color.parseColor("#00C853"));
        } else {
            question_1.setTextColor(Color.parseColor("#D50000"));
        }

        if (trophies == 2) {
            correctAnswers++;
            question_2.setTextColor(Color.parseColor("#00C853"));
        } else {
            question_2.setTextColor(Color.parseColor("#D50000"));
        }

        if (response3.isChecked()) {
            correctAnswers++;
            question_3.setTextColor(Color.parseColor("#00C853"));
        } else {
            question_3.setTextColor(Color.parseColor("#D50000"));
        }

        if (response4) {
            correctAnswers++;
            question_4.setTextColor(Color.parseColor("#00C853"));
        } else {
            question_4.setTextColor(Color.parseColor("#D50000"));
        }


        if (matches == 64) {
            correctAnswers++;
            question_5.setTextColor(Color.parseColor("#00C853"));
        } else {
            question_5.setTextColor(Color.parseColor("#D50000"));
        }

        if (correctAnswers == 5) {
            show(getString(R.string.answers_correct), R.drawable.correct);
        } else {
            show(getString(R.string.answers_incorrect, correctAnswers, 5),
                    R.drawable.incorrect);
        }

    }

    /**
     * Increase the number of trophies
     *
     * @param view of the button that increases the number of trophies
     */
    public void addTrophies(View view) {
        if (this.trophies >= 5) {
            Toast.makeText(this, R.string.trophy_50,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        this.trophies +=1;

        ImageView trophy = new ImageView(this);
        trophy.setImageResource(R.drawable.trophy);

        LinearLayout trophies = findViewById(R.id.trophies);
        trophies.addView(trophy);
    }

    /**
     * Reduce the number of trophies
     *
     * @param view of the button that reduces the number of trophies
     */
    public void reduceTrophies(View view) {
        if (this.trophies <= 1) {
            Toast.makeText(this, R.string.trophy_1, Toast.LENGTH_SHORT).show();
            return;
        }
        this.trophies -= 1;

        LinearLayout trophies = findViewById(R.id.trophies);
        trophies.removeAllViewsInLayout();

        for(int i = 0; i < this.trophies; i++){
            ImageView trophy = new ImageView(this);
            trophy.setImageResource(R.drawable.trophy);

            trophies.addView(trophy);
        }
    }

    /**
     * Adjust the number of trophies shown
     */
    private void adjustMatches() {
        TextView matches = findViewById(R.id.matches);
        matches.setText(String.valueOf(this.matches));
    }

    /**
     * Display a custom toast
     *
     * @param textToDisplay of the text to be shown
     * @param image_id      of the image to be displayed
     */
    private void show(String textToDisplay, int image_id) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText(textToDisplay);

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(image_id);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    /**
     * Uncheck all the radio buttons in radio group 1
     *
     * @param view of the radio button that causes radio group 1 radio buttons to be unchecked
     */
    public void uncheckGroup1(View view) {
        RadioGroup group1 = findViewById(R.id.group_1);
        group1.clearCheck();
    }

    /**
     * Uncheck all the radio buttons in radio group 2
     *
     * @param view of the radio button that causes radio group 2 radio buttons to be unchecked
     */
    public void uncheckGroup2(View view) {
        RadioGroup group2 = findViewById(R.id.group_2);
        group2.clearCheck();
    }

    /**
     * Reduce the number of matches displayed
     *
     * @param view of the button that reduces the number of matches
     */
    public void reduceMatches(View view) {
        if (this.matches <= 60) {
            Toast.makeText(this, R.string.match_60, Toast.LENGTH_SHORT).show();
            return;
        }
        this.matches -= 1;
        adjustMatches();
    }

    /**
     * Increase the number of matches displayed
     *
     * @param view of the button that increases the number of matches
     */
    public void addMatches(View view) {
        if (this.matches >= 70) {
            Toast.makeText(this, R.string.match_70,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        this.matches += 1;
        adjustMatches();
    }
}

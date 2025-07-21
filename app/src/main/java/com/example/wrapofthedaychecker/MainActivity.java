package com.example.wrapofthedaychecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView currentDayTextView;
    private TextView currentWrapTextView;
    private LinearLayout scheduleContainer;
    
    // Map to store wrap information for each day
    private Map<String, List<String>> wrapSchedule;
    private String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        initializeWrapSchedule();
        displayCurrentDay();
        displayWeeklySchedule();
    }
    
    private void initializeViews() {
        currentDayTextView = findViewById(R.id.currentDay);
        currentWrapTextView = findViewById(R.id.currentWrap);
        scheduleContainer = findViewById(R.id.scheduleContainer);
    }
    
    private void initializeWrapSchedule() {
        wrapSchedule = new HashMap<>();
        
        // Monday
        List<String> mondayWraps = new ArrayList<>();
        mondayWraps.add("The Spicy Veggie One");
        mondayWraps.add("The Caesar & Bacon Chicken One");
        wrapSchedule.put("Monday", mondayWraps);
        
        // Tuesday
        List<String> tuesdayWraps = new ArrayList<>();
        tuesdayWraps.add("The BBQ & Bacon Chicken One");
        wrapSchedule.put("Tuesday", tuesdayWraps);
        
        // Wednesday
        List<String> wednesdayWraps = new ArrayList<>();
        wednesdayWraps.add("The Katsu Chicken One");
        wrapSchedule.put("Wednesday", wednesdayWraps);
        
        // Thursday
        List<String> thursdayWraps = new ArrayList<>();
        thursdayWraps.add("The Spicy Veggie One");
        thursdayWraps.add("The BBQ & Bacon Chicken One");
        wrapSchedule.put("Thursday", thursdayWraps);
        
        // Friday
        List<String> fridayWraps = new ArrayList<>();
        fridayWraps.add("The Sweet Chilli Chicken One");
        wrapSchedule.put("Friday", fridayWraps);
        
        // Saturday
        List<String> saturdayWraps = new ArrayList<>();
        saturdayWraps.add("The Caesar & Bacon Chicken One");
        wrapSchedule.put("Saturday", saturdayWraps);
        
        // Sunday
        List<String> sundayWraps = new ArrayList<>();
        sundayWraps.add("The Sweet Chilli Chicken One");
        wrapSchedule.put("Sunday", sundayWraps);
    }
    
    private void displayCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Calendar.SUNDAY = 1, so subtract 1
        String currentDay = daysOfWeek[dayOfWeek];
        
        currentDayTextView.setText(currentDay);
        
        List<String> todaysWraps = wrapSchedule.get(currentDay);
        if (todaysWraps != null && !todaysWraps.isEmpty()) {
            StringBuilder wrapText = new StringBuilder();
            for (int i = 0; i < todaysWraps.size(); i++) {
                if (i > 0) {
                    wrapText.append("\n\n");
                }
                if (todaysWraps.size() > 1) {
                    wrapText.append((i + 1)).append(". ");
                }
                wrapText.append(todaysWraps.get(i));
            }
            currentWrapTextView.setText(wrapText.toString());
        }
    }
    
    private void displayWeeklySchedule() {
        Calendar calendar = Calendar.getInstance();
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        
        for (int i = 0; i < daysOfWeek.length; i++) {
            String day = daysOfWeek[i];
            List<String> wraps = wrapSchedule.get(day);
            
            // Create a container for each day
            LinearLayout dayContainer = new LinearLayout(this);
            dayContainer.setOrientation(LinearLayout.VERTICAL);
            dayContainer.setPadding(0, 0, 0, 32);
            
            // Day header
            TextView dayHeader = new TextView(this);
            dayHeader.setText(day);
            dayHeader.setTextSize(16);
            dayHeader.setTextColor(ContextCompat.getColor(this, R.color.mcdonalds_red));
            
            // Highlight current day
            if (i == currentDayOfWeek) {
                dayHeader.setTextColor(ContextCompat.getColor(this, R.color.green));
                dayHeader.setText(day + " (Today)");
            }
            
            dayHeader.setTypeface(null, android.graphics.Typeface.BOLD);
            dayContainer.addView(dayHeader);
            
            // Add wraps for this day
            if (wraps != null) {
                for (int j = 0; j < wraps.size(); j++) {
                    TextView wrapText = new TextView(this);
                    String wrapName = wraps.get(j);
                    if (wraps.size() > 1) {
                        wrapName = (j + 1) + ". " + wrapName;
                    }
                    wrapText.setText(wrapName);
                    wrapText.setTextSize(14);
                    wrapText.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
                    wrapText.setPadding(16, 4, 0, 4);
                    dayContainer.addView(wrapText);
                }
            }
            
            // Add separator line (except for last item)
            if (i < daysOfWeek.length - 1) {
                View separator = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2);
                params.setMargins(0, 16, 0, 0);
                separator.setLayoutParams(params);
                separator.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray));
                dayContainer.addView(separator);
            }
            
            scheduleContainer.addView(dayContainer);
        }
    }
}
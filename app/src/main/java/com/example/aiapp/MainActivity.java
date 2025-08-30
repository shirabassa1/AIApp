package com.example.aiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    //I'm a starter/expert cooker. I wanna cook a ___ for _ people. Send me a recipe - Make it simple/as few ingredients as possible/fancy. Include the ingredients I need and the whole process.
    private String prompt;

    private String[] cookerOptions;
    private String[] recipeTypes;

    private Spinner spinnerCookerMode, spinnerRecipeType;
    private EditText etNumPeople, etRecipe;
    private Button btnGenerate;
    private TextView tvRecipe;

    ArrayAdapter<String> adpCookerOptions, adpRecipeTypes;

    private String cookerMode, recipeType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        cookerOptions = getResources().getStringArray(R.array.cooker_options);
        recipeTypes = getResources().getStringArray(R.array.recipe_types);

        spinnerCookerMode = findViewById(R.id.spinnerCookerMode);
        spinnerRecipeType = findViewById(R.id.spinnerRecipeType);
        etNumPeople = findViewById(R.id.etNumPeople);
        etRecipe = findViewById(R.id.etRecipe);
        btnGenerate = findViewById(R.id.btnGenerate);
        tvRecipe = findViewById(R.id.tvRecipe);

        createSpinners();
        createButton();
    }

    private void createSpinners()
    {
        spinnerCookerMode.setOnItemSelectedListener(this);
        spinnerRecipeType.setOnItemSelectedListener(this);

        adpCookerOptions = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cookerOptions);
        adpRecipeTypes = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, recipeTypes);

        spinnerCookerMode.setAdapter(adpCookerOptions);
        spinnerRecipeType.setAdapter(adpRecipeTypes);
    }

    private void createButton()
    {
        btnGenerate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                prompt = createPrompt();
                tvRecipe.setText(prompt);
                tvRecipe.setVisibility(View.VISIBLE);
            }
        });
    }

    private String createPrompt()
    {
        return "I'm a " + cookerMode + " cooker. " +
                "I wanna cook a " + etRecipe.getText().toString() + " " +
                "for " + etNumPeople.getText().toString() + " people. " +
                "Send me a recipe - " +
                "Make it " + recipeType + ". " +
                "Include the ingredients I need and the whole process.";
}

@Override
public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
{
if (adapterView == spinnerCookerMode)
{
    cookerMode = cookerOptions[i];
}
else if (adapterView == spinnerRecipeType)
{
    recipeType = recipeTypes[i];
}
}

@Override
public void onNothingSelected(AdapterView<?> adapterView) {}
}
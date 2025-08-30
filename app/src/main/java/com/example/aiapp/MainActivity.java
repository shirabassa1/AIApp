package com.example.aiapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    //I'm a _ cooker. I wanna make a _ for _ people. Send me a recipe - Make it _. Include the ingredients I need and the whole process.
    private String prompt;

    private String[] cookerOptions;
    private String[] recipeTypes;

    private Spinner spinnerCookerMode, spinnerRecipeType;
    private EditText etNumPeople, etRecipe;
    private Button btnGenerate;
    private TextView tvRecipe;

    ArrayAdapter<String> adpCookerOptions, adpRecipeTypes;

    private String cookerMode, recipeType;

    private GeminiManager geminiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        geminiManager = GeminiManager.getInstance();

        cookerOptions = getResources().getStringArray(R.array.cooker_options);
        recipeTypes = getResources().getStringArray(R.array.recipe_types);

        spinnerCookerMode = findViewById(R.id.spinnerCookerMode);
        spinnerRecipeType = findViewById(R.id.spinnerRecipeType);
        etNumPeople = findViewById(R.id.etNumPeople);
        etRecipe = findViewById(R.id.etRecipe);
        btnGenerate = findViewById(R.id.btnGenerate);
        tvRecipe = findViewById(R.id.tvRecipe);

        tvRecipe.setVisibility(View.GONE);
        tvRecipe.setMovementMethod(new android.text.method.ScrollingMovementMethod());
        tvRecipe.setVerticalScrollBarEnabled(true);

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

                ProgressDialog pd = new ProgressDialog(MainActivity.this);
                pd.setTitle("Sent Prompt");
                pd.setMessage("Waiting for response...");
                pd.setCancelable(false);
                pd.show();

                geminiManager.sendTextPrompt(MainActivity.this, prompt, new GeminiCallback()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        tvRecipe.setText(result);
                        pd.dismiss();
                        tvRecipe.scrollTo(0, 0);
                        tvRecipe.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Throwable error)
                    {
                        pd.dismiss();
                        btnGenerate.setError("Failed to prompt Gemini");
                    }
                });
            }
        });
    }

    private String createPrompt()
    {
        return "I'm a " + cookerMode + " cooker. " +
                "I wanna make a " + etRecipe.getText().toString() + " " +
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
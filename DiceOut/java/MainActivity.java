package com.example.diceout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView rollResult;

    Random rand;

    //Fields to hold the die value

    int die1;
    int die2;
    int die3;
    //ArrayList to hold die value

    ArrayList<Integer> dice;

    // ArrayList to hold all 3 dice combination
    ArrayList<ImageView> diceImageViews;

    //Field to hold score
    int score;

    // Field to hold the score text
    TextView scoreText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });
        Button resetButton = findViewById(R.id.resetButton);
       resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                scoreReset();
            }
        });

        //Set initial score
        score = 0;
        rollResult = findViewById(R.id.rollResult);

        scoreText = findViewById(R.id.scoreText);

        rand = new Random();

        //Create arraylist container
        dice = new ArrayList<>();

        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageViews = new ArrayList<>();
        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);

    }

    public void goHome(View v){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
    public void scoreReset(){
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        score = 0;

        for(int dieOfSet = 0; dieOfSet < 3; dieOfSet++){
            String imageName = "die_1.png";
            try{
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream, null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);

            } catch(IOException e){
                e.printStackTrace();
            }
        }
        String msg ="Let's start again!";
        rollResult.setText(msg);
        scoreText.setText("Score: " + score);

    }


    public void rollDice(View v){
        rollResult.setText("Clicked!");

        //roll dice
        die1 = rand.nextInt(6)+1;
        die2 = rand.nextInt(6)+1;
        die3 = rand.nextInt(6)+1;

        // Put die value into array
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for(int dieOfSet = 0; dieOfSet < 3; dieOfSet++){
            String imageName = "die_" + dice.get(dieOfSet)+ ".png";
            try{
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream, null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);

            } catch(IOException e){
                e.printStackTrace();
            }
        }

        //Build message with result
        String msg;

        if(die1 == die2 && die1 == die3){
            //Triples
            int scoreDelta = die1 *100;
            msg ="You rolled a triple " + die1 + "! You score " + scoreDelta + " points!";
            score += scoreDelta;
        }else if(die1 == die2 || die2 == die3 || die1 == die3){
            //Double
            msg = "Your rolled doubles for 50 points!";
            score += 50;
        }else{
            msg = "you didn't score this roll. Try again!";
        }

        //update the app to display the message

        rollResult.setText(msg);
        scoreText.setText("Score: " + score);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

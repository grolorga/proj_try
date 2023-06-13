package com.example.proj_try;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import model.Card;

public class GameActivity extends AppCompatActivity {
    private int clickCount1 = 0;
    private int clickCount2 = 0;
    private int clickCount3 = 0;
    private int clickCount12 = 0;
    private int clickCount22 = 0;
    private int clickCount32 = 0;
    private int clickCount1Second = 0;
    private int clickCount2Second = 0;
    private int clickCount3Second = 0;
    private int clickCount1Second2 = 0;
    private int clickCount2Second2 = 0;
    private int clickCount3Second2 = 0;
    private int countOnStart = 0;
    private int countOnNext = 0;
    private int RoundCounter = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_v2);
        Button startButton = findViewById(R.id.startMove);
        Button endButton = findViewById(R.id.endMove);
        Button exitButton = findViewById(R.id.exitButton);

        ArrayList<Button> FirstPlayerCard = new ArrayList<>();
        ArrayList<Card> FirstPlayerCardCard = new ArrayList<>();

        ArrayList<Button> FirstPlayerCardOnLayout2 = new ArrayList<>();
        ArrayList<Card> FirstPlayerCardCardOnLayout2 = new ArrayList<>();

        Random random = new Random();
        int randomNumber = random.nextInt(22);
        FirstPlayerCardCard.add( getRandJsonElem(randomNumber));
        int randomNumber2 = random.nextInt(22);
        FirstPlayerCardCard.add(getRandJsonElem(randomNumber2));
        int randomNumber3 = random.nextInt(22);
        FirstPlayerCardCard.add(getRandJsonElem(randomNumber3));

        //
        randomNumber = random.nextInt(22);
        FirstPlayerCardCard.add( getRandJsonElem(randomNumber));
        randomNumber = random.nextInt(22);
        FirstPlayerCardCard.add(getRandJsonElem(randomNumber));
        randomNumber = random.nextInt(22);
        FirstPlayerCardCard.add(getRandJsonElem(randomNumber));
        //

        FirstPlayerCard.add(findViewById(R.id.button_card1));
        FirstPlayerCard.add(findViewById(R.id.button_card2));
        FirstPlayerCard.add(findViewById(R.id.button_card3));

        //
        FirstPlayerCard.add(findViewById(R.id.button_card12));
        FirstPlayerCard.add(findViewById(R.id.button_card22));
        FirstPlayerCard.add(findViewById(R.id.button_card32));
        //

        ArrayList<Button> SecondPlayerCard = new ArrayList<>();
        ArrayList<Card> SecondPlayerCardCard = new ArrayList<>();

        ArrayList<Button> SecondPlayerCardOnLayout2 = new ArrayList<>();
        ArrayList<Card> SecondPlayerCardCardOnLayout2 = new ArrayList<>();

        int randomNumber4 = random.nextInt(22);
        SecondPlayerCardCard.add( getRandJsonElem(randomNumber4));
        int randomNumber5 = random.nextInt(22);
        SecondPlayerCardCard.add( getRandJsonElem(randomNumber5));
        int randomNumber6 = random.nextInt(22);
        SecondPlayerCardCard.add( getRandJsonElem(randomNumber6));

        //
        randomNumber = random.nextInt(22);
        SecondPlayerCardCard.add( getRandJsonElem(randomNumber));
        randomNumber = random.nextInt(22);
        SecondPlayerCardCard.add( getRandJsonElem(randomNumber));
        randomNumber = random.nextInt(22);
        SecondPlayerCardCard.add( getRandJsonElem(randomNumber));
        //

        SecondPlayerCard.add( findViewById(R.id.button_card1Second));
        SecondPlayerCard.add( findViewById(R.id.button_card2Second));
        SecondPlayerCard.add( findViewById(R.id.button_card3Second));

        //
        SecondPlayerCard.add( findViewById(R.id.button_card1Second2));
        SecondPlayerCard.add( findViewById(R.id.button_card2Second2));
        SecondPlayerCard.add( findViewById(R.id.button_card3Second2));
        //

        TextView roundCount = findViewById(R.id.RoundCount);
        roundCount.setText(String.valueOf(RoundCounter));
        TextView move = findViewById(R.id.Move);
        goUnActive(endButton);

        block_cards(FirstPlayerCard);
        block_cards(SecondPlayerCard);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countOnNext++;
                block_cards(FirstPlayerCard);
                block_cards(SecondPlayerCard);
                move.setText(R.string.Press_start);
                if(countOnNext%2 != 0){
                    resetImgForNextMove(FirstPlayerCard);
                }
                else{
                    LinearLayout desk = findViewById(R.id.Layout2);
                    desk.removeAllViewsInLayout();
                    desk.invalidate();
                    resetImgForNextMove(SecondPlayerCard);
                    int who;
                    who = checkWhoWins(findViewById(R.id.Layout2), FirstPlayerCardCardOnLayout2, SecondPlayerCardCardOnLayout2);
                    switch (who){
                        case 1:
                            deleteLive(findViewById(R.id.LiveLayout2));
                            break;
                        case 2:
                            deleteLive(findViewById(R.id.LiveLayout1));
                            break;
                        default:
                            deleteLive(findViewById(R.id.LiveLayout2));
                            deleteLive(findViewById(R.id.LiveLayout1));
                            break;
                    }
                    LinearLayout FirstPlayerLives = findViewById(R.id.LiveLayout1);
                    LinearLayout SecondPlayerLives = findViewById(R.id.LiveLayout2);
                    if(FirstPlayerLives.getChildCount()<1){
                        startMainActivityAfterWin("Победа второго игрока");
                    } else if (SecondPlayerLives.getChildCount() < 1) {
                        startMainActivityAfterWin("Победа первого игрока");
                    }
                    else if((FirstPlayerLives.getChildCount()<1)&&(SecondPlayerLives.getChildCount() < 1)){
                        startMainActivityAfterWin("Ничья");
                    }
                }
                goUnActive(endButton);
                goActive(startButton);
            }

        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countOnStart++;
                if(countOnStart %2 != 0)
                {
                    unblock_cards(FirstPlayerCard);
                    move.setText("Ход первого игрока ->");
                    setFirstPlayerCards(SecondPlayerCard,FirstPlayerCard,FirstPlayerCardCard);
                    RoundCounter++;
                    roundCount.setText(String.valueOf(RoundCounter));
                }
                else{
                    unblock_cards(SecondPlayerCard);
                    move.setText("<- Ход второго игрока");
                    setSecondPlayerCards(FirstPlayerCard,SecondPlayerCard,SecondPlayerCardCard);
                }
                goUnActive(endButton);
                goUnActive(startButton);
            }
        });


        SecondPlayerCard.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount1Second++;
                if (clickCount1Second == 1) {
                    clickCount2Second = 0;
                    clickCount3Second = 0;
                    clickCount1Second2 = 0;
                    clickCount2Second2 = 0;
                    clickCount3Second2 = 0;
                    animateAndResetExceptThis(SecondPlayerCard, 0);
                } else if (clickCount1Second == 2) {
                    resetAnimationAll(SecondPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1Second);
                    removeButtonFromDesk(SecondPlayerCard.get(0),parentLayout);
                    removeCardFromPlayerV2(SecondPlayerCard, SecondPlayerCardCard, SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, SecondPlayerCard.get(0));
                    SecondPlayerCard.trimToSize();
                    SecondPlayerCardCard.trimToSize();
                    addButtonOnDesk(SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, false);
                    clickCount1Second = 0; // Сбрасываем счетчик нажатий
                    block_cards(SecondPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });

        SecondPlayerCard.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount2Second++;
                if (clickCount2Second == 1) {
                    clickCount1Second = 0;
                    clickCount3Second = 0;
                    clickCount1Second2 = 0;
                    clickCount2Second2 = 0;
                    clickCount3Second2 = 0;
                    animateAndResetExceptThis(SecondPlayerCard, 1);
                } else if (clickCount2Second == 2) {
                    resetAnimationAll(SecondPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1Second);
                    removeButtonFromDesk(SecondPlayerCard.get(1), parentLayout);
                    removeCardFromPlayerV2(SecondPlayerCard, SecondPlayerCardCard, SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, SecondPlayerCard.get(1));
                    SecondPlayerCard.trimToSize();
                    SecondPlayerCardCard.trimToSize();
                    addButtonOnDesk(SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, false);
                    clickCount2Second = 0; // Сбрасываем счетчик нажатий
                    block_cards(SecondPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });

        SecondPlayerCard.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount3Second++;
                if (clickCount3Second == 1) {
                    clickCount2Second = 0;
                    clickCount1Second = 0;
                    clickCount1Second2 = 0;
                    clickCount2Second2 = 0;
                    clickCount3Second2 = 0;
                    animateAndResetExceptThis(SecondPlayerCard, 2);
                } else if (clickCount3Second == 2) {
                    resetAnimationAll(SecondPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1Second);
                    removeButtonFromDesk(SecondPlayerCard.get(2),parentLayout);
                    removeCardFromPlayerV2(SecondPlayerCard, SecondPlayerCardCard, SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, SecondPlayerCard.get(2));
                    SecondPlayerCard.trimToSize();
                    SecondPlayerCardCard.trimToSize();
                    addButtonOnDesk(SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, false);
                    clickCount3Second = 0; // Сбрасываем счетчик нажатий
                    block_cards(SecondPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });

        SecondPlayerCard.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount1Second2++;
                if (clickCount1Second2 == 1) {
                    clickCount2Second2 = 0;
                    clickCount3Second2 = 0;
                    clickCount3Second = 0;
                    clickCount2Second = 0;
                    clickCount1Second = 0;
                    animateAndResetExceptThis(SecondPlayerCard, 3);
                } else if (clickCount1Second2 == 2) {
                    resetAnimationAll(SecondPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1Second2);
                    removeButtonFromDesk(SecondPlayerCard.get(3),parentLayout);
                    removeCardFromPlayerV2(SecondPlayerCard, SecondPlayerCardCard, SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, SecondPlayerCard.get(3));
                    SecondPlayerCard.trimToSize();
                    SecondPlayerCardCard.trimToSize();
                    addButtonOnDesk(SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, false);
                    clickCount1Second2 = 0; // Сбрасываем счетчик нажатий
                    block_cards(SecondPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });
        SecondPlayerCard.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount2Second2++;
                if (clickCount2Second2 == 1) {
                    clickCount3Second2 = 0;
                    clickCount1Second2 = 0;
                    clickCount3Second = 0;
                    clickCount2Second = 0;
                    clickCount1Second = 0;
                    animateAndResetExceptThis(SecondPlayerCard, 4);
                } else if (clickCount2Second2 == 2) {
                    resetAnimationAll(SecondPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1Second2);
                    removeButtonFromDesk(SecondPlayerCard.get(4),parentLayout);
                    removeCardFromPlayerV2(SecondPlayerCard, SecondPlayerCardCard, SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, SecondPlayerCard.get(4));
                    SecondPlayerCard.trimToSize();
                    SecondPlayerCardCard.trimToSize();
                    addButtonOnDesk(SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, false);
                    clickCount2Second2 = 0; // Сбрасываем счетчик нажатий
                    block_cards(SecondPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });
        SecondPlayerCard.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount3Second2++;
                if (clickCount3Second2 == 1) {
                    clickCount2Second2 = 0;
                    clickCount1Second2 = 0;
                    clickCount3Second = 0;
                    clickCount2Second = 0;
                    clickCount1Second = 0;
                    animateAndResetExceptThis(SecondPlayerCard, 5);
                } else if (clickCount3Second2 == 2) {
                    resetAnimationAll(SecondPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1Second2);
                    removeButtonFromDesk(SecondPlayerCard.get(5),parentLayout);
                    removeCardFromPlayerV2(SecondPlayerCard, SecondPlayerCardCard, SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, SecondPlayerCard.get(5));
                    SecondPlayerCard.trimToSize();
                    SecondPlayerCardCard.trimToSize();
                    addButtonOnDesk(SecondPlayerCardOnLayout2, SecondPlayerCardCardOnLayout2, false);
                    clickCount3Second2 = 0; // Сбрасываем счетчик нажатий
                    block_cards(SecondPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });

        FirstPlayerCard.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount1++;
                if (clickCount1 == 1) {
                    clickCount2 = 0;
                    clickCount3 = 0;
                    clickCount12 = 0;
                    clickCount22 = 0;
                    clickCount32 = 0;
                    animateAndResetExceptThis(FirstPlayerCard, 0);
                } else if (clickCount1 == 2) {
                    resetAnimationAll(FirstPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1);
                    removeButtonFromDesk(FirstPlayerCard.get(0),parentLayout);
                    removeCardFromPlayerV2(FirstPlayerCard, FirstPlayerCardCard, FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, FirstPlayerCard.get(0));
                    FirstPlayerCard.trimToSize();
                    FirstPlayerCardCard.trimToSize();
                    addButtonOnDesk(FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, true);
                    clickCount1 = 0; // Сбрасываем счетчик нажатий
                    block_cards(FirstPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });

        FirstPlayerCard.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount2++;
                if (clickCount2 == 1) {
                    clickCount1 = 0;
                    clickCount3 = 0;
                    clickCount12 = 0;
                    clickCount22 = 0;
                    clickCount32 = 0;
                    animateAndResetExceptThis(FirstPlayerCard, 1);
                } else if (clickCount2 == 2) {
                    resetAnimationAll(FirstPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1);
                    removeButtonFromDesk(FirstPlayerCard.get(1), parentLayout);
                    removeCardFromPlayerV2(FirstPlayerCard, FirstPlayerCardCard, FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, FirstPlayerCard.get(1));
                    FirstPlayerCard.trimToSize();
                    FirstPlayerCardCard.trimToSize();
                    addButtonOnDesk(FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, true);
                    clickCount2 = 0; // Сбрасываем счетчик нажатий
                    block_cards(FirstPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });

        FirstPlayerCard.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount3++;
                if (clickCount3 == 1) {
                    clickCount2 = 0;
                    clickCount1 = 0;
                    clickCount12 = 0;
                    clickCount22 = 0;
                    clickCount32 = 0;
                    animateAndResetExceptThis(FirstPlayerCard, 2);
                } else if (clickCount3 == 2) {
                    resetAnimationAll(FirstPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout1);
                    removeButtonFromDesk(FirstPlayerCard.get(2),parentLayout);
                    removeCardFromPlayerV2(FirstPlayerCard, FirstPlayerCardCard, FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, FirstPlayerCard.get(2));
                    FirstPlayerCard.trimToSize();
                    FirstPlayerCardCard.trimToSize();
                    addButtonOnDesk(FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, true);
                    clickCount3 = 0; // Сбрасываем счетчик нажатий
                    block_cards(FirstPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });
        FirstPlayerCard.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount12++;
                if (clickCount12 == 1) {
                    clickCount22 = 0;
                    clickCount32 = 0;
                    clickCount1 = 0;
                    clickCount2 = 0;
                    clickCount3 = 0;
                    animateAndResetExceptThis(FirstPlayerCard, 3);
                } else if (clickCount12 == 2) {
                    resetAnimationAll(FirstPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout12);
                    removeButtonFromDesk(FirstPlayerCard.get(3),parentLayout);
                    removeCardFromPlayerV2(FirstPlayerCard, FirstPlayerCardCard, FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, FirstPlayerCard.get(3));
                    FirstPlayerCard.trimToSize();
                    FirstPlayerCardCard.trimToSize();
                    addButtonOnDesk(FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, true);
                    clickCount12 = 0; // Сбрасываем счетчик нажатий
                    block_cards(FirstPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });
        FirstPlayerCard.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount22++;
                if (clickCount22 == 1) {
                    clickCount32 = 0;
                    clickCount12 = 0;
                    clickCount1 = 0;
                    clickCount2 = 0;
                    clickCount3 = 0;
                    animateAndResetExceptThis(FirstPlayerCard, 4);
                } else if (clickCount22 == 2) {
                    resetAnimationAll(FirstPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout12);
                    removeButtonFromDesk(FirstPlayerCard.get(4),parentLayout);
                    removeCardFromPlayerV2(FirstPlayerCard, FirstPlayerCardCard, FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, FirstPlayerCard.get(4));
                    FirstPlayerCard.trimToSize();
                    FirstPlayerCardCard.trimToSize();
                    addButtonOnDesk(FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, true);
                    clickCount22 = 0; // Сбрасываем счетчик нажатий
                    block_cards(FirstPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });
        FirstPlayerCard.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount32++;
                if (clickCount32 == 1) {
                    clickCount22 = 0;
                    clickCount12 = 0;
                    clickCount1 = 0;
                    clickCount2 = 0;
                    clickCount3 = 0;
                    animateAndResetExceptThis(FirstPlayerCard, 5);
                } else if (clickCount32 == 2) {
                    resetAnimationAll(FirstPlayerCard);
                    LinearLayout parentLayout = findViewById(R.id.Layout12);
                    removeButtonFromDesk(FirstPlayerCard.get(5),parentLayout);
                    removeCardFromPlayerV2(FirstPlayerCard, FirstPlayerCardCard, FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, FirstPlayerCard.get(5));
                    FirstPlayerCard.trimToSize();
                    FirstPlayerCardCard.trimToSize();
                    addButtonOnDesk(FirstPlayerCardOnLayout2, FirstPlayerCardCardOnLayout2, true);
                    clickCount32 = 0; // Сбрасываем счетчик нажатий
                    block_cards(FirstPlayerCard);
                    move.setText("Нажмите закончить ход");
                    goActive(endButton);
                }
            }
        });
    }

    void startMainActivityAfterWin(String s){
        Intent i = new Intent(this, EndGame.class);
        i.putExtra("key",s);
        startActivity(i);
    }


    void deleteLive(LinearLayout livesLayout){
        int childCount = livesLayout.getChildCount()-1;
        livesLayout.removeViewAt(childCount);
    }

    int checkWhoWins(LinearLayout layout2, ArrayList<Card> first, ArrayList<Card> second){
        int lastFirstPower, lastSecondPower;
        if (first!=null) {
            lastFirstPower = first.get(first.size() - 1).getPower();
        }
        else{
            lastFirstPower = 0;
        }
        if (second!=null) {
            lastSecondPower = second.get(second.size() - 1).getPower();
        }
        else{
            lastSecondPower = 0;
        }
        if(lastFirstPower > lastSecondPower){
            return 1;
        }
        else if(lastFirstPower == lastSecondPower){
            return 0;
        }
        else { return 2;}
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private Animation createScaleAnimation(float startScale, float endScale) {
        Animation animation = new ScaleAnimation(
                startScale, endScale, startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(200);
        animation.setFillAfter(true);
        return animation;
    }

    private void resetAnimation(Button button) {
        button.clearAnimation();
        Animation animationScaleUp = createScaleAnimation(1.0f, 2.0f);
        Animation animationScaleDown = createScaleAnimation(2.0f, 1.0f);
        button.startAnimation(animationScaleDown);
    }
    void resetAnimationAll(ArrayList<Button> buttons){
        for(Button button: buttons)
        {
            resetAnimation(button);
        }
    }

    private void animateButton(Button button) {
        Animation animationScaleUp = createScaleAnimation(1.0f, 2.0f);
        Animation animationScaleDown = createScaleAnimation(2.0f, 1.0f);
        button.startAnimation(animationScaleUp);
    }

    void animateAndResetExceptThis(ArrayList<Button> buttons, int indexToAnime){
        for(int i = 0; i<buttons.size(); i++){
            if(i == indexToAnime){
                animateButton(buttons.get(i));
            }
            else{
                resetAnimation(buttons.get(i));
            }
        }
    }

    void removeButtonFromDesk(Button button, LinearLayout parentLayout){
        parentLayout.removeView(button);
    }

    void addButtonOnDesk(ArrayList<Button> button, ArrayList<Card> card,boolean needRemove){
        LinearLayout newLayout = findViewById(R.id.Layout2);
        if(needRemove)
            newLayout.removeAllViewsInLayout();
        Button nb = button.get(button.size()-1);
        //nb.setWeight(1.5);
        newLayout.addView(nb);
    }

    Card getRandJsonElem(int randomNumber){
        StringBuilder jsonString = null;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.cards);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();
            String json = jsonString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(String.valueOf(jsonString));

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonElement firstElement = jsonArray.get(randomNumber);//выбираем случайного персонажа
        Gson gson = new Gson();
        Card card = gson.fromJson(firstElement, Card.class);
        return card;
    }

    void removeCardFromPlayerV2(ArrayList<Button> buttons, ArrayList<Card> cards, ArrayList<Button> toAddButtons, ArrayList<Card> toAddCards, Button ButtonToRemove){
        int indexToRemove = buttons.indexOf(ButtonToRemove);
        toAddButtons.add(ButtonToRemove);
        toAddCards.add(cards.get(buttons.indexOf(ButtonToRemove)));
    }

    void setFirstPlayerCards(ArrayList<Button> secondPlayerCards,ArrayList<Button> FirstPlayerCard, ArrayList<Card> FirstPlayerCardCard){
        for (int i = 0; i< FirstPlayerCard.size(); i++) {
            setImg_v2(FirstPlayerCard.get(i), FirstPlayerCardCard.get(i));
        }
    }

    void setSecondPlayerCards(ArrayList<Button> firstPlayerCards,ArrayList<Button> secondPlayerCards, ArrayList<Card> secondPlayerCardCard){
        for (int i = 0; i< secondPlayerCards.size(); i++) {
            setImg_v2(secondPlayerCards.get(i), secondPlayerCardCard.get(i));
        }
    }

    private void setImg_v2(Button button1, Card card) {
        Button button = button1;
        String info = card.getName()+"\n"+String.valueOf(card.getPower());
        button.setText(info);//информация о персонаже
        String imagePath = "drawable/" + card.getImage();
        int resourceId = getResources().getIdentifier(imagePath, "drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(resourceId, getTheme());
        button.setBackground(drawable);

    }
    void goActive(Button button){
        button.setEnabled(true);
        button.setBackgroundResource(R.drawable.button_background);
    }
    void goUnActive(Button button){
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.disabled_button_background);
    }

    void block_cards(ArrayList <Button> buttons){
        for(Button button:buttons){
            button.setEnabled(false);
        }
    }
    void unblock_cards(ArrayList <Button> buttons){
        for(Button button:buttons){
            button.setEnabled(true);
        }
    }

    private void resetImg(Button button){
        String card_info_placeholder = getString(R.string.card_info_placeholder);
        button.setText(card_info_placeholder);
        button.setBackgroundResource(R.drawable.default_card_image);

    }
    void resetImgForNextMove(ArrayList<Button> buttons){
        for(Button button:buttons){
            resetImg(button);
        }
    }
}
package main.fourpicturesoneword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.IOException;

import model.FourPictures;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private FourPictures game;
    private String[] links = {"Paris1.jpg","Paris2.jpg","Paris3.jpg","Paris4.jpg"};

    private TextView answerRevealed;
    private ImageView image_1;
    private ImageView image_2;
    private ImageView image_3;
    private ImageView image_4;

    private GridLayout imageGrid;
    private GridLayout buttonGrid;
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageGrid = (GridLayout) findViewById(R.id.image_grid);
        imageGrid.setUseDefaultMargins(false);
        imageGrid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        imageGrid.setRowOrderPreserved(false);

        answerRevealed = (TextView) findViewById(R.id.revealed_answer);
        image_1 = (ImageView) findViewById(R.id.img_1);
        image_2 = (ImageView) findViewById(R.id.img_2);
        image_3 = (ImageView) findViewById(R.id.img_3);
        image_4 = (ImageView) findViewById(R.id.img_4);

        game = new FourPictures(links, "paris", "vprartis");
        answerRevealed.setText((CharSequence) game.toString());

        /* Set images for game round */
        image_1.setBackgroundResource(R.drawable.paris1);
        image_2.setBackgroundResource(R.drawable.paris2);
        image_3.setBackgroundResource(R.drawable.paris3);
        image_4.setBackgroundResource(R.drawable.paris4);

        /* Generate Buttons containing letter selection */
        buttonGrid = (GridLayout) findViewById(R.id.button_grid);
        buttons = new Button[8];
        generateButtons();
    }

    private void generateButtons(){
        for(int i = 0; i < buttons.length; i += 1){
            buttons[i] = new Button(MainActivity.this);
            buttons[i].setOnClickListener(this);
            buttons[i].setText(game.getSelection().toCharArray(), i, 1);
            buttonGrid.addView(buttons[i]);
        }
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        b.setEnabled(false);
        char text = b.getText().toString().toCharArray()[0];
        for(int i = 0; i < game.getAnswer().length(); i++){
            if(text == game.getAnswer().toCharArray()[i]){
                game.setRevealed(game.getAnswer().charAt(i), 2*i);
            }
        }
        answerRevealed.setText((CharSequence)game.toString());
        if (game.isGuessed()) {
            gameWon();
        }
    }

    private void gameWon(){
        //Disable all active buttons
        for(Button button : buttons){
            button.setEnabled(false);
        }

        //Set alert message to show when round is complete
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Close
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

        //Notification sound to accompany alert
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("round-complete.mp3");
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.prepare();
            player.start();
            Log.d("Round Won: ", "Sound reached");
        } catch (IOException e) {
            Log.d("Round Won: ", "No Sound");
        }
    }

    /* Menu Methods */
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

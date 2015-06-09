package main.fourpicturesoneword;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import model.FourPictures;


public class MainActivity extends ActionBarActivity {

    private FourPictures game;
    private String[] links = {"Paris1.jpg","Paris2.jpg","Paris3.jpg","Paris4.jpg"};

    private TextView answerRevealed;
    private GridLayout pictureGrid;
    private ImageView image_1;
    private ImageView image_2;
    private ImageView image_3;
    private ImageView image_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerRevealed = (TextView) findViewById(R.id.revealed_answer);
        pictureGrid = (GridLayout) findViewById(R.id.image_grid);
        image_1 = (ImageView) findViewById(R.id.img_1);
        image_2 = (ImageView) findViewById(R.id.img_2);
        image_3 = (ImageView) findViewById(R.id.img_3);
        image_4 = (ImageView) findViewById(R.id.img_4);

        game = new FourPictures(links, "paris", "prartis");
        answerRevealed.setText((CharSequence) game.toString());

        image_1.setBackgroundResource(R.drawable.paris1);
        image_2.setBackgroundResource(R.drawable.paris2);
        image_3.setBackgroundResource(R.drawable.paris3);
        image_4.setBackgroundResource(R.drawable.paris4);

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

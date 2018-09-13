package pp2.fullsailuniversity.secondbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameSetup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
    }


    public void playButtonHandler(View view) {
        Intent goToGame = new Intent(this,MainGameActivity.class);
        startActivity(goToGame);
    }
}


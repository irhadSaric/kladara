package com.example.user.kladionica;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder rez = new StringBuilder();
                try
                {
                    final Document document = Jsoup.connect("https://www.bhplay.ba/Odds/BySportEnum?sport=1").get();

                    for(Element row : document.select("td.hA"))
                    {
                        int brojac = 0;
                        rez.append(row.text()+" ");
                        for(Element row2 : document.select("td.o, td.oe")) {
                            rez.append(row2.text()+" ");
                            brojac++;
                            if(brojac == 6) {
                                rez.append("\n");
                                break;
                            }
                        }
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView text = (TextView) findViewById(R.id.tekst);
                        text.setText(rez);
                    }
                });
            }
        }).start();
    }
}

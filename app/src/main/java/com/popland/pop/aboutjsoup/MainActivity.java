package com.popland.pop.aboutjsoup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView TV;
    ImageView imageView;
    Button BTNandroid, BTNtech, BTNdigital;
ListView LV;
ArrayList<RSSitems> arrlRSS;
    Document document = null, doc;
    String title="", description="", anh="", pubDate="";
    Elements media = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TV = (TextView)findViewById(R.id.TV);
//        imageView = (ImageView)findViewById(R.id.imageView);
        BTNandroid = (Button)findViewById(R.id.BTNandroid);
        BTNtech = (Button)findViewById(R.id.BTNtech);
        BTNdigital = (Button)findViewById(R.id.BTNdigital);
        LV = (ListView)findViewById(R.id.LV);

        BTNandroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        anh="";
                        arrlRSS = null;
                        document = null;
                        arrlRSS = new ArrayList<RSSitems>();
                        new XMLParser().execute("http://www.androidheadlines.com/feed");
                    }
                });
            }
        });

        BTNtech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrlRSS = null;
                        document = null;
                        arrlRSS = new ArrayList<RSSitems>();
                        new XMLParser().execute("http://www.techtimes.com/rss/sections/google.xml");
                    }
                });
            }
        });

        BTNdigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrlRSS = null;
                        document = null;
                        arrlRSS = new ArrayList<RSSitems>();
                        new XMLParser().execute("http://www.digitaltrends.com/cool-tech/feed/");
                    }
                });
            }
        });
    }

    class XMLParser extends AsyncTask<String,String,Document> {

        @Override
        protected Document doInBackground(String... params) {

            try {
                document = Jsoup.connect(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            Elements items = document.select("item");
             for(int i=0;i<items.size();i++){
            Element item = items.get(i);
            Elements item1 = item.children();

             title = item1.select("title").first().text();
                 pubDate = item1.select("pubDate").first().text();

             description = item1.select("description").first().text();
             doc = Jsoup.parse(description);
            Elements p = doc.select("p");
            if (p.size() > 0) {
                description = p.first().text();
            } else {
                description = doc.body().text();
                if (description.contains("&nbsp;")) {
                    description = description.replaceAll("&nbsp;", " ");
                }
            }
                 media = item1.select("media|thumbnail");
            if (media.size() > 0) {
                anh = media.first().attr("url");
            }
             media = item1.select("media|content");
            if (media.size() > 0) {
                anh = media.first().attr("url");
            }
            media = item1.select("image");
            if (media.size() > 0) {
                anh = media.first().text();
            }
             media = item1.select("thumbnail");
            if (media.size() > 0) {
                anh = media.first().text();
            }

            arrlRSS.add(new RSSitems(title, description, pubDate, anh));
        }

        CustomBaseAdapter adapter = new CustomBaseAdapter(MainActivity.this, R.layout.cutom_layoutlv, arrlRSS);
        LV.setAdapter(adapter);
    }
    }

    }



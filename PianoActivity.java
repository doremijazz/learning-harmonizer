package com.example.learningharmonizer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.view.View.OnClickListener;


public class PianoActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    ArrayList<Integer> ids = new ArrayList<Integer>(){{add(R.id.p1); add(R.id.b1); add(R.id.p2); add(R.id.b2); add(R.id.p3); add(R.id.b3); add(R.id.p4);
        add(R.id.p5); add(R.id.b4); add(R.id.p6); add(R.id.b5); add(R.id.p7); add(R.id.p8); add(R.id.b6); add(R.id.p9); add(R.id.b7); add(R.id.p10);
        add(R.id.b8); add(R.id.p11);add(R.id.p12); add(R.id.b9); add(R.id.p13); add(R.id.b10); add(R.id.p14);add(R.id.p15); add(R.id.b11); add(R.id.p16);
        add(R.id.b12); add(R.id.p17);}};

    ArrayList<Integer> pitch = new ArrayList<Integer>(){{add(R.raw.f3); add(R.raw.f3black); add(R.raw.g3); add(R.raw.g3black); add(R.raw.a3); add(R.raw.a3black);add(R.raw.b3);
        add(R.raw.c4); add(R.raw.c4black); add(R.raw.d4); add(R.raw.d4black); add(R.raw.e4);add(R.raw.f4); add(R.raw.f4black); add(R.raw.g4); add(R.raw.g4black); add(R.raw.a4); add(R.raw.a4black);add(R.raw.b4);
        add(R.raw.c5); add(R.raw.c5black); add(R.raw.d5); add(R.raw.d5black); add(R.raw.e5);add(R.raw.f5); add(R.raw.f5black); add(R.raw.g5); add(R.raw.g5black); add(R.raw.a5); }};

    ArrayList<String> liste_notes = new ArrayList<String>(){{add("fa3"); add("fa#3"); add("sol3"); add("sol#3"); add("la3"); add("la#3"); add("si3");
    add("do4");add("do#4"); add("re4"); add("re#4"); add("mi4"); add("fa4"); add("fa#4"); add("sol4"); add("sol#4"); add("la4"); add("la#4"); add("si4");
    add("do5"); add("do#5"); add("re5"); add("re#5"); add("mi5"); add("fa5"); add("fa#5"); add("sol5"); add("sol#5"); add("la5"); }};

    ArrayList<Integer> id_grille = new ArrayList<Integer>(){{ add(R.id.image1_1); add(R.id.image2_1); add(R.id.image3_1);add(R.id.image1_2); add(R.id.image2_2); add(R.id.image3_2);add(R.id.image1_3); add(R.id.image2_3); add(R.id.image3_3);add(R.id.image1_4);
        add(R.id.image2_4); add(R.id.image3_4);add(R.id.image1_5); add(R.id.image2_5); add(R.id.image3_5);add(R.id.image1_6); add(R.id.image2_6); add(R.id.image3_6);
        add(R.id.image1_7); add(R.id.image2_7); add(R.id.image3_7);add(R.id.image1_8); add(R.id.image2_8); add(R.id.image3_8);add(R.id.image1_9); add(R.id.image2_9); add(R.id.image3_9);add(R.id.image1_10); add(R.id.image2_10); add(R.id.image3_10); }};

    /* Variable globale */
    MyDataStyle donnes = ALGORYTHME.Mozart();
    String[][][] possibilites = donnes.getSecond();
    ArrayList<String> accords = donnes.getFirst();
    String [] degres = donnes.getThird();
    /*ArrayList<ArrayList<ArrayList<String>>> possibilites = CSV_TO_JAVA.conversion();*/
    int indice_accords;
    int avancement;
    int indice_ligne;
    String new_accord;

    MediaPlayer mediaPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.time_signature, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        findViewById(R.id.rec).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);

        for (int i=0; i<29; i++){
            findViewById(ids.get(i)).setOnClickListener(this);
        }
    }

    int state = -1 ;
    int max_grille = 0;
    String note;

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.rec) {
            state = 0;
        }

        if (view.getId() == R.id.stop) {
            state = -1;
        }

        if (max_grille == 10 || max_grille==0) {
            for (int x = 0; x<30; x++){
                int a_effacer = id_grille.get(x);
                TextView t1 = findViewById(a_effacer);
                t1.setVisibility(View.INVISIBLE);
            }
            avancement = 0;
            max_grille = 0;
        }

        for (int j = 0; j < 29; j++) {
            if (view.getId() == ids.get(j)) {
                indice_ligne = (j+5) % 12;
                System.out.println("indice ligne vaut = " + indice_ligne);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), pitch.get(j));
                mediaPlayer.start();
                max_grille += 1;
                note = liste_notes.get(j);
                afficherHarmonie(note);
            }
        }
    }

    /*recherche dans le tableau 3D et retourne le nouvel accord*/
    public String recherche_accord_possible (){
        String[][] tab2  = possibilites[indice_ligne];
        int taille = tab2.length;
        System.out.println("taille_tab2"+ taille);
        if (state == 0){
            indice_accords = 0;
            state = 1;
        }
        String[] tab3 = tab2[indice_accords];
        int nbalea = (int) (Math.random() * tab3.length);
        new_accord = tab3[nbalea];
        indice_accords = indice(new_accord);
        return new_accord;

    }

    /* recherche de l'indice de l'accord */
    public int indice(String new_accord){
        for (int k = 0; k<25; k++){
            if (new_accord.equals(accords.get(k))){
                return (k);
            }
        }
        return -1;
    }

    /* affiche tous les resultats dans la grille*/
    public void afficherHarmonie(String note){
        System.out.println("l'ancien accord est : " + new_accord);
        new_accord = recherche_accord_possible();
        System.out.println("le nouvel accord est : " + new_accord);
        String degre_accord = degres[indice_accords];
        ArrayList<String> elements = new ArrayList<String>(){{add(note); add(new_accord); add(degre_accord);}};
        for (int grille = 0; grille<3; grille++){
            System.out.println("avancement"+ avancement);
            int position= (grille+avancement);
            int affichage = id_grille.get(position);
            TextView t1 = findViewById(affichage);
            t1.setText(elements.get(grille));
            t1.setVisibility(View.VISIBLE);
        }
        avancement += 3;
        System.out.println("avancement"+ avancement);
        System.out.println("max_grille"+max_grille);
    }

    /* .............. */

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }



}

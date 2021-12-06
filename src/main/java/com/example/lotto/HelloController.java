package com.example.lotto;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.*;

public class HelloController {

    @FXML
    public Button sorsolasGomb;
    @FXML
    public Label kisorsoltSzam;
    @FXML
    public Label elsoSzam;
    @FXML
    public Label masodikSzam;
    @FXML
    public Label harmadikSzam;
    @FXML
    public Label negyedikSzam;
    @FXML
    public Label otodikSzam;

    private Timer timer;
    private int sorsolasraSzantSzam;
    private int sorsolasokSzama;
    private boolean kellesorsolas= true;
    private boolean kellerendezes= true;
    private Random random=new Random();
    private ArrayList<Integer> sorsoltakLista = new ArrayList<>();


    public void sorsolOnCLick() {
        if (kellesorsolas){
            sorsolasMenete();
        }
        else {
            if (!kellerendezes){
                ujraindit();
            }
            else{
                rendezes();
            }
        }
    }

    public void sorsolasMenete() {
        Timer huzottAnimation = new Timer();
        TimerTask huzottAnimationTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> kisorsoltSzam.setText(Integer.toString(random.nextInt(90)+1)));
            }
        };
        huzottAnimation.scheduleAtFixedRate(huzottAnimationTask, 0, 1);
        Timer huzottAnimation2 = new Timer();
        TimerTask huzottAnimationTask2 = new TimerTask() {
            @Override
            public void run() {
                huzottAnimation.cancel();
                Platform.runLater(() -> {
                    while (sorsolasraSzantSzam==0 || sorsoltakLista.contains(sorsolasraSzantSzam)) {
                        sorsolasraSzantSzam = random.nextInt(90) + 1;
                    }
                    String kihuzottak = Integer.toString(sorsolasraSzantSzam);
                    kisorsoltSzam.setText(kihuzottak);
                    kiiras(sorsolasraSzantSzam);
                    if (sorsolasokSzama == 5) {
                        sorsolasGomb.setText("Rendez");
                        kellesorsolas = false;
                    }
                });
            }
        };
        huzottAnimation2.schedule(huzottAnimationTask2, 500);
    }

    public void rendezes(){
        sorsolasGomb.setText("Sorsol");
        sorsoltakLista.sort(Comparator.naturalOrder());
        for (int i = 0; i < 5; i++) {
            kiiratas(i, Integer.toString(sorsoltakLista.get(i)));
        }
        int listaIndex = random.nextInt(5);
        kisorsoltSzam.setText(String.valueOf(sorsoltakLista.get(listaIndex)));
        kellerendezes = false;
    }

    public void kiiratas(int labelekSzama, String kihuzottString) {
        switch (labelekSzama){
            case 0: elsoSzam.setText(kihuzottString); break;
            case 1: masodikSzam.setText(kihuzottString); break;
            case 2: harmadikSzam.setText(kihuzottString); break;
            case 3: negyedikSzam.setText(kihuzottString); break;
            case 4: otodikSzam.setText(kihuzottString); break;
        }
    }

    public void kiiras(int szam) {
        sorsoltakLista.add(szam);
        kiiratas(sorsolasokSzama, Integer.toString(szam));
        sorsolasokSzama++;
    }

    public void ujraindit() {
        sorsoltakLista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            kiiratas(i, "");
        }
        sorsolasokSzama = 0;
        sorsolasGomb.setText("Sorsol");
        kisorsoltSzam.setText("0");
        kellerendezes = true;
        kellesorsolas = true;
    }
}
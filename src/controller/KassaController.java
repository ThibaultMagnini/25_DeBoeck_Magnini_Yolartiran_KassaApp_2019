package controller;

import model.Artikel;
import model.Winkel;
import view.KassaView;

import java.util.List;
import java.util.Map;

public class KassaController {
    private KassaView view;
    private Winkel model;

    public KassaController(Winkel model) {
        setModel(model);
    }

    public Map<String, Artikel> getProducten () {
        return model.getArtikels();
    }

    public KassaView getView() {
        return view;
    }

    public void setView(KassaView view) {
        this.view = view;
    }

    public Winkel getModel() {
        return model;
    }

    private void setModel(Winkel model) {
        this.model = model;
    }

    public Artikel vindArtikel(String artikelId) {
        return model.vindArtikel(artikelId);
    }

    public void save (String filename, List<Artikel> cart) {
        model.save(filename, cart);
    }
}

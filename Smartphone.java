// Classe derivata Smartphone (ereditarietà)
class Smartphone extends Prodotto {
    private String sistemaOperativo;
    private int memoriaGB;

    // Costruttore principale
    public Smartphone(String nome, double prezzo, String sistemaOperativo, int memoriaGB) {
        super(nome, prezzo); // Chiamata al costruttore della classe padre
        this.sistemaOperativo = sistemaOperativo;
        this.memoriaGB = memoriaGB;
    }

    // Costruttore sovraccaricato (memoria di default)
    public Smartphone(String nome, double prezzo, String sistemaOperativo) {
        this(nome, prezzo, sistemaOperativo, 128); // 128GB di default
    }

    // Metodi getter (incapsulamento)
    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public int getMemoriaGB() {
        return memoriaGB;
    }

    // Sovrascrittura del metodo descrizione
    @Override
    public void descrizione() {
        System.out.printf("Smartphone: %s, Prezzo: EUR %.2f, OS: %s, Memoria: %dGB\n", 
                         getNome(), getPrezzo(), sistemaOperativo, memoriaGB);
    }

    // Sovraccaricamento del metodo descrizione
    public void descrizione(boolean dettagliExtra) {
        descrizione();
        if (dettagliExtra) {
            System.out.printf("Categoria: Mobile, Spazio disponibile stimato: %dGB\n", 
                             memoriaGB - 20); // -20GB per sistema operativo
        }
    }

    // Sovrascrittura del metodo toFileString
    @Override
    public String toFileString() {
        return String.format("Smartphone: %s, EUR %.2f, OS: %s, Memoria: %dGB", 
                           getNome(), getPrezzo(), sistemaOperativo, memoriaGB);
    }

    // Sovrascrittura del metodo sconto con logica specifica
    @Override
    public double calcolaPrezzioScontato(double percentuale) {
        // Gli smartphone hanno uno sconto massimo del 30%
        if (percentuale > 30) {
            percentuale = 30;
            System.out.println("Sconto limitato al 30% per gli smartphone");
        }
        return super.calcolaPrezzioScontato(percentuale);
    }
}
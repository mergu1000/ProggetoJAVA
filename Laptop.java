class Laptop extends Prodotto {
    private int ramGB;
    private String sistemaOperativo;
    private String processore;

    // Costruttore principale
    public Laptop(String nome, double prezzo, int ramGB, String sistemaOperativo, String processore) {
        super(nome, prezzo); // Chiamata al costruttore della classe padre
        if (ramGB <= 0) {
            throw new IllegalArgumentException("La RAM deve essere maggiore di zero");
        }
        this.ramGB = ramGB;
        this.sistemaOperativo = sistemaOperativo;
        this.processore = processore != null ? processore : "Intel i5"; // Default
    }

    // Costruttore sovraccaricato (processore di default)
    public Laptop(String nome, double prezzo, int ramGB, String sistemaOperativo) {
        this(nome, prezzo, ramGB, sistemaOperativo, "Intel i5");
    }

    // Metodi getter (incapsulamento)
    public int getRamGB() {
        return ramGB;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public String getProcessore() {
        return processore;
    }

    // Sovrascrittura del metodo descrizione
    @Override
    public void descrizione() {
        System.out.printf("Laptop: %s, Prezzo: EUR %.2f, RAM: %dGB, OS: %s, CPU: %s\n", 
                         getNome(), getPrezzo(), ramGB, sistemaOperativo, processore);
    }

    // Sovraccaricamento del metodo descrizione
    public void descrizione(boolean specificheTecniche) {
        descrizione();
        if (specificheTecniche) {
            String categoria = ramGB >= 16 ? "Gaming/Professionale" : ramGB >= 8 ? "Ufficio" : "Base";
            System.out.printf("Categoria: %s, Adatto per: %s\n", 
                             categoria, getUsoConsigliato());
        }
    }

    // Metodo privato di supporto
    private String getUsoConsigliato() {
        if (ramGB >= 16) return "Gaming, Video editing, Programmazione";
        if (ramGB >= 8) return "Ufficio, Navigazione, Studio";
        return "Attività base, Navigazione web";
    }

    // Sovrascrittura del metodo toFileString
    @Override
    public String toFileString() {
        return String.format("Laptop: %s, EUR %.2f, RAM: %dGB, OS: %s, CPU: %s", 
                           getNome(), getPrezzo(), ramGB, sistemaOperativo, processore);
    }

    // Sovrascrittura del metodo sconto con logica specifica
    @Override
    public double calcolaPrezzioScontato(double percentuale) {
        // I laptop professionali (RAM >= 16GB) hanno sconti limitati
        if (ramGB >= 16 && percentuale > 20) {
            percentuale = 20;
            System.out.println("Sconto limitato al 20% per laptop professionali");
        }
        return super.calcolaPrezzioScontato(percentuale);
    }
}
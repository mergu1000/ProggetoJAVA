import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Classe base concreta
class Prodotto {
    private String nome;
    private double prezzo;
    protected static int contatoreProdotti = 0;

    // Costruttore principale
    public Prodotto(String nome, double prezzo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del prodotto non può essere vuoto");
        }
        if (prezzo <= 0) {
            throw new IllegalArgumentException("Il prezzo deve essere maggiore di zero");
        }
        this.nome = nome;
        this.prezzo = prezzo;
        contatoreProdotti++;
    }

    // Costruttore sovraccaricato (solo nome, prezzo di default)
    public Prodotto(String nome) {
        this(nome, 0.0);
    }

    // Metodi getter (incapsulamento)
    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    // Setter con validazione (incapsulamento)
    public void setPrezzo(double prezzo) {
        if (prezzo <= 0) {
            throw new IllegalArgumentException("Il prezzo deve essere maggiore di zero");
        }
        this.prezzo = prezzo;
    }

    // Metodo statico
    public static int getContatoreProdotti() {
        return contatoreProdotti;
    }

    // Metodi concreti che possono essere sovrascritti
    public void descrizione() {
        System.out.printf("Prodotto: %s, Prezzo: EUR %.2f\n", nome, prezzo);
    }

    public String toFileString() {
        return String.format("Prodotto: %s, EUR %.2f", nome, prezzo);
    }
    
    // Metodo per calcolare sconti (può essere sovrascritto)
    public double calcolaPrezzioScontato(double percentuale) {
        if (percentuale < 0 || percentuale > 100) {
            throw new IllegalArgumentException("La percentuale deve essere tra 0 e 100");
        }
        return prezzo * (1 - percentuale / 100);
    }

    // Sovrascrittura di equals per confronto oggetti
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Prodotto prodotto = (Prodotto) obj;
        return nome.equals(prodotto.nome) && Double.compare(prodotto.prezzo, prezzo) == 0;
    }

    // Sovrascrittura di toString
    @Override
    public String toString() {
        return String.format("%s - EUR %.2f", nome, prezzo);
    }
}
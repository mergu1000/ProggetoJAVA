import java.io.*;
import java.util.*;

public class Negozio {
    // Costanti della classe
    private static final int MAX_PRODOTTI = 100;
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Array per contenere i prodotti
        Prodotto[] catalogo = new Prodotto[MAX_PRODOTTI];
        
        // Configurazione prodotti disponibili
        ConfigurazioneProdotti config = new ConfigurazioneProdotti();
        
        System.out.println("=== GESTIONE NEGOZIO ===");
        System.out.println("========================");
        
        // Ciclo principale del programma
        boolean esegui = true;
        while (esegui) {
            mostraMenu();
            String scelta = scanner.nextLine().trim();
            
            try {
                switch (scelta) {
                    case "1":
                        aggiungiProdotto(catalogo, config);
                        break;
                    case "2":
                        rimuoviProdotto(catalogo);
                        break;
                    case "3":
                        mostraInventario(catalogo);
                        break;
                    case "4":
                        applicaSconti(catalogo);
                        break;
                    case "5":
                        esportaInventario(catalogo);
                        break;
                    case "6":
                        importaInventario(catalogo);
                        break;
                    case "7":
                        mostraStatistiche(catalogo);
                        break;
                    case "8":
                        esegui = false;
                        System.out.println("Grazie per aver usato il sistema. Arrivederci!");
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.err.println("Errore imprevisto: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
    }
    
    // Mostra il menu principale
    private static void mostraMenu() {
        System.out.println("\n--- MENU PRINCIPALE ---");
        System.out.println("1. Aggiungi prodotto");
        System.out.println("2. Rimuovi prodotto");
        System.out.println("3. Lista inventario");
        System.out.println("4. Applica sconti");
        System.out.println("5. Esporta inventario");
        System.out.println("6. Importa inventario");
        System.out.println("7. Statistiche");
        System.out.println("8. Esci");
        System.out.print("Scegli un'opzione: ");
    }

    // Aggiunge un nuovo prodotto al catalogo
    private static void aggiungiProdotto(Prodotto[] catalogo, ConfigurazioneProdotti config) {
        int posizioneLibera = trovaPosizioneLibera(catalogo);
        if (posizioneLibera == -1) {
            System.out.println("Inventario pieno. Impossibile aggiungere altri prodotti.");
            return;
        }

        int tipoScelto = scegliTipoProdotto();
        double prezzo = inserisciPrezzo();

        try {
            if (tipoScelto == 1) {
                catalogo[posizioneLibera] = creaSmartphone(config, prezzo);
                System.out.println("Smartphone aggiunto all'inventario.");
            } else {
                catalogo[posizioneLibera] = creaLaptop(config, prezzo);
                System.out.println("Laptop aggiunto all'inventario.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Errore nella creazione del prodotto: " + e.getMessage());
        }
    }

    // Trova la prima posizione libera nell'array
    private static int trovaPosizioneLibera(Prodotto[] catalogo) {
        for (int i = 0; i < catalogo.length; i++) {
            if (catalogo[i] == null) {
                return i;
            }
        }
        return -1; // Array pieno
    }

    // Permette di scegliere il tipo di prodotto
    private static int scegliTipoProdotto() {
        while (true) {
            System.out.println("\nScegli il tipo di prodotto:");
            System.out.println("1. Smartphone");
            System.out.println("2. Laptop");
            System.out.print("Scelta: ");
            
            try {
                int scelta = Integer.parseInt(scanner.nextLine().trim());
                if (scelta == 1 || scelta == 2) {
                    return scelta;
                }
                System.out.println("Inserisci 1 o 2.");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido (1 o 2).");
            }
        }
    }

    // Input per il prezzo con validazione
    private static double inserisciPrezzo() {
        while (true) {
            System.out.print("Prezzo (EUR): ");
            try {
                double prezzo = Double.parseDouble(scanner.nextLine().trim());
                if (prezzo > 0) {
                    return prezzo;
                }
                System.out.println("Il prezzo deve essere maggiore di 0.");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    // Crea un oggetto Smartphone
    private static Smartphone creaSmartphone(ConfigurazioneProdotti config, double prezzo) {
        int marcaIndex = selezionaIndice(config.getSmartphoneMarche(), "marca smartphone");
        int modelloIndex = selezionaIndice(config.getSmartphoneModelli()[marcaIndex], "modello");
        String os = selezionaSistemaOperativo(new String[]{"Android", "iOS"});
        
        System.out.print("Memoria (GB) [default: 128]: ");
        String memoriaInput = scanner.nextLine().trim();
        int memoria = memoriaInput.isEmpty() ? 128 : Integer.parseInt(memoriaInput);
        
        return new Smartphone(config.getSmartphoneModelli()[marcaIndex][modelloIndex], 
                            prezzo, os, memoria);
    }

    // Crea un oggetto Laptop
    private static Laptop creaLaptop(ConfigurazioneProdotti config, double prezzo) {
        int marcaIndex = selezionaIndice(config.getLaptopMarche(), "marca laptop");
        int modelloIndex = selezionaIndice(config.getLaptopModelli()[marcaIndex], "modello");
        String os = selezionaSistemaOperativo(new String[]{"Windows", "macOS", "Linux"});
        
        int ram = inserisciRAM();
        
        System.out.print("Processore [default: Intel i5]: ");
        String processore = scanner.nextLine().trim();
        
        // Uso di costruttori sovraccaricati
        if (processore.isEmpty()) {
            return new Laptop(config.getLaptopModelli()[marcaIndex][modelloIndex], 
                            prezzo, ram, os);
        } else {
            return new Laptop(config.getLaptopModelli()[marcaIndex][modelloIndex], 
                            prezzo, ram, os, processore);
        }
    }

    // Input per la RAM con validazione
    private static int inserisciRAM() {
        while (true) {
            System.out.print("RAM (GB): ");
            try {
                int ram = Integer.parseInt(scanner.nextLine().trim());
                if (ram > 0) {
                    return ram;
                }
                System.out.println("La RAM deve essere maggiore di 0.");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    // Seleziona un indice da un array di opzioni
    private static int selezionaIndice(String[] opzioni, String tipo) {
        System.out.println("\nSeleziona " + tipo + ":");
        for (int i = 0; i < opzioni.length; i++) {
            System.out.println((i + 1) + ". " + opzioni[i]);
        }
        
        while (true) {
            System.out.print("Scelta: ");
            try {
                int scelta = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (scelta >= 0 && scelta < opzioni.length) {
                    return scelta;
                }
                System.out.println("Indice non valido.");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    // Seleziona il sistema operativo
    private static String selezionaSistemaOperativo(String[] opzioni) {
        System.out.println("\nSistema operativo:");
        for (int i = 0; i < opzioni.length; i++) {
            System.out.println((i + 1) + ". " + opzioni[i]);
        }
        
        while (true) {
            System.out.print("Scelta: ");
            try {
                int scelta = Integer.parseInt(scanner.nextLine().trim());
                if (scelta >= 1 && scelta <= opzioni.length) {
                    return opzioni[scelta - 1];
                }
                System.out.println("Scelta non valida.");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    // Rimuove un prodotto dal catalogo
    private static void rimuoviProdotto(Prodotto[] catalogo) {
        if (inventarioVuoto(catalogo)) {
            System.out.println("Inventario vuoto, nessun prodotto da rimuovere.");
            return;
        }
        
        mostraInventario(catalogo);
        System.out.print("\nInserisci il numero del prodotto da rimuovere: ");
        
        try {
            int indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (indice >= 0 && indice < catalogo.length && catalogo[indice] != null) {
                System.out.println("Rimosso: " + catalogo[indice].getNome());
                catalogo[indice] = null;
            } else {
                System.out.println("Indice non valido o prodotto già rimosso.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido.");
        }
    }

    // Mostra l'inventario completo (POLIMORFISMO IMPORTANTE)
    private static void mostraInventario(Prodotto[] catalogo) {
        System.out.println("\n--- INVENTARIO ---");
        if (inventarioVuoto(catalogo)) {
            System.out.println("Nessun prodotto presente nell'inventario.");
            return;
        }
        
        for (int i = 0; i < catalogo.length; i++) {
            if (catalogo[i] != null) {
                System.out.print((i + 1) + ") ");
                // POLIMORFISMO: il metodo descrizione() viene chiamato
                // sulla classe corretta automaticamente
                if (catalogo[i] instanceof Smartphone) {
                    ((Smartphone) catalogo[i]).descrizione(false);
                } else if (catalogo[i] instanceof Laptop) {
                    ((Laptop) catalogo[i]).descrizione(false);
                } else {
                    catalogo[i].descrizione();
                }
            }
        }
    }

    // Applica sconti a tutti i prodotti
    private static void applicaSconti(Prodotto[] catalogo) {
        if (inventarioVuoto(catalogo)) {
            System.out.println("Inventario vuoto, nessun prodotto su cui applicare sconti.");
            return;
        }
        
        System.out.print("Inserisci percentuale di sconto (0-100): ");
        try {
            double sconto = Double.parseDouble(scanner.nextLine().trim());
            
            System.out.println("\n--- PREZZI SCONTATI ---");
            // POLIMORFISMO: ogni classe ha la sua logica di sconto
            for (Prodotto p : catalogo) {
                if (p != null) {
                    double prezzoScontato = p.calcolaPrezzioScontato(sconto);
                    System.out.printf("%s: EUR %.2f → EUR %.2f (%.1f%% sconto)\n",
                                    p.getNome(), p.getPrezzo(), prezzoScontato, sconto);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido per lo sconto.");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // Esporta l'inventario su file
    private static void esportaInventario(Prodotto[] catalogo) {
        String nomeFile = "inventario_" + System.currentTimeMillis() + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile))) {
            writer.println("=== INVENTARIO NEGOZIO ===");
            writer.println("Data esportazione: " + new Date());
            writer.println("Totale prodotti: " + Prodotto.getContatoreProdotti());
            writer.println("=============================\n");
            
            int contatore = 0;
            // POLIMORFISMO: ogni classe ha il suo formato di esportazione
            for (Prodotto p : catalogo) {
                if (p != null) {
                    writer.println((++contatore) + ". " + p.toFileString());
                }
            }
            
            if (contatore == 0) {
                writer.println("Nessun prodotto presente nell'inventario.");
            }
            
            System.out.println("Inventario esportato con successo in: " + nomeFile);
            
        } catch (IOException e) {
            System.err.println("Errore durante l'esportazione: " + e.getMessage());
        }
    }

    // Importa inventario da file (versione semplificata)
    private static void importaInventario(Prodotto[] catalogo) {
        System.out.print("Nome del file da importare: ");
        String nomeFile = scanner.nextLine().trim();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            int importati = 0;
            
            while ((linea = reader.readLine()) != null) {
                // Logica semplificata per l'esempio
                if (linea.contains("Smartphone:") || linea.contains("Laptop:")) {
                    System.out.println("Trovato: " + linea);
                    importati++;
                }
            }
            
            System.out.println("Importazione completata. Prodotti trovati: " + importati);
            
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + nomeFile);
        } catch (IOException e) {
            System.err.println("Errore durante l'importazione: " + e.getMessage());
        }
    }

    // Mostra statistiche dell'inventario
    private static void mostraStatistiche(Prodotto[] catalogo) {
        System.out.println("\n--- STATISTICHE INVENTARIO ---");
        
        int smartphone = 0, laptop = 0;
        double valoreTotale = 0;
        double prezzoMin = Double.MAX_VALUE, prezzoMax = 0;
        
        // Analisi dei prodotti usando instanceof (POLIMORFISMO)
        for (Prodotto p : catalogo) {
            if (p != null) {
                if (p instanceof Smartphone) smartphone++;
                else if (p instanceof Laptop) laptop++;
                
                valoreTotale += p.getPrezzo();
                prezzoMin = Math.min(prezzoMin, p.getPrezzo());
                prezzoMax = Math.max(prezzoMax, p.getPrezzo());
            }
        }
        
        int totale = smartphone + laptop;
        if (totale == 0) {
            System.out.println("Nessun prodotto presente per generare statistiche.");
            return;
        }
        
        System.out.println("Smartphone: " + smartphone);
        System.out.println("Laptop: " + laptop);
        System.out.println("Totale prodotti: " + totale);
        System.out.printf("Valore totale: EUR %.2f\n", valoreTotale);
        System.out.printf("Prezzo medio: EUR %.2f\n", valoreTotale / totale);
        System.out.printf("Prezzo minimo: EUR %.2f\n", prezzoMin);
        System.out.printf("Prezzo massimo: EUR %.2f\n", prezzoMax);
        System.out.println("Prodotti creati in totale: " + Prodotto.getContatoreProdotti());
    }

    // Verifica se l'inventario è vuoto
    private static boolean inventarioVuoto(Prodotto[] catalogo) {
        for (Prodotto p : catalogo) {
            if (p != null) return false;
        }
        return true;
    }
}

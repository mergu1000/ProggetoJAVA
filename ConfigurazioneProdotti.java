// Classe di supporto per organizzare i dati dei prodotti
class ConfigurazioneProdotti {
    // Array bidimensionali per i modelli
    private final String[][] smartphoneModelli = {
        {"Samsung Galaxy S25", "Samsung Galaxy A70", "Samsung Galaxy Z Flip"},
        {"Apple iPhone 16", "Apple iPhone 15", "Apple iPhone SE"},
        {"Xiaomi Mi 11", "Xiaomi Redmi Note 12", "Xiaomi Poco F5"}
    };
    
    private final String[][] laptopModelli = {
        {"Dell XPS 13", "Dell Inspiron 15", "Dell Latitude"},
        {"HP Pavilion", "HP Envy", "HP Omen"},
        {"Lenovo ThinkPad", "Lenovo IdeaPad", "Lenovo Legion"}
    };
    
    // Array delle marche
    private final String[] smartphoneMarche = {"Samsung", "Apple", "Xiaomi"};
    private final String[] laptopMarche = {"Dell", "HP", "Lenovo"};

    // Metodi getter per accedere ai dati (INCAPSULAMENTO)
    public String[][] getSmartphoneModelli() { return smartphoneModelli; }
    public String[][] getLaptopModelli() { return laptopModelli; }
    public String[] getSmartphoneMarche() { return smartphoneMarche; }
    public String[] getLaptopMarche() { return laptopMarche; }
}
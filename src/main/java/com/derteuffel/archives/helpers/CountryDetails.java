package com.derteuffel.archives.helpers;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryDetails {



    public CountryDetails() {
    }

    private ArrayList<String> divisions = new ArrayList<>(Arrays.asList("Informatique","Resources Humaine","Finances","Exploitation","Production","Maintenance","Logistique","Administration"));
    private ArrayList<String> regions = new ArrayList<>(Arrays.asList(
            "Adamaoua","Centre","Est","Extreme nord","Littoral","Nord","Nord-Ouest","Ouest","Sud","Sud-Ouest"
    ));
    private ArrayList<String> villes = new ArrayList<>(Arrays.asList("Abandikiti",
            "Bélabo", "Foumbot", "Abong Mbang", "Bélel", "Fumban", "Akom II", "Belo", "Fundong", "Akono", "Bertoua",
            "Garoua", "Akonolinga", "Bétaré Oya", "Garoua Boulaï", "Akwabana Island", "Bobia Island", "Guidder", "Ambam",
            "Bogo", "Hyoma Nawétia", "Ambas Island", "Bonabéri", "Idenao", "Babanki", "Bota", "Ile Anna", "Bafang",
            "Buéa", "Île Bouhangué", "Bafia", "Campton Island", "Île Dibongo", "Bafoussam", "Diang", "Ile Djébalé",
            "Bali", "Dibombari", "Île Kwélé-Kwélé", "Bamenda", "Dimako", "Île Malimba", "Bamendjou", "Dizangué",
            "Ile Manoka", "Bamkim", "Djohong", "Ile Miandjou", "Bamusso", "Douala", "Ile Ndonga", "Bana", "Doumé",
            "Île Nicol", "Bandjoun", "Dschang", "Île Pongo", "Bangangté", "Ébolowa", "Ile Wouri",
            "Bansoa", "Édéa", "Ile Yatou", "Banyo", "Eséka", "Inikoi Island", "Baténa", "Essé", "Jakiri",
            "Batibo","Me","Nkongsamba","Yaoundé","Meiganda","Nkoteng","Yokadouma","Melong","Ntui",
            "Évodoula","Mbégé","Nguti","Wum","Mbengwi","Njinikom","Yabassi","Mbouda","Nkoloboko","Yagoua",
            "Kabanha","Mbang","Ngomedzap","Tiko","Mbanga","Ngorro","Tiko Island","Mbankomo","Ngou","Tonga",
            "Batouri","Ndom","Tcholliré","Mbalmayo","Nganbé","Tibati","Mbandjok","Ngaundere","Tignère",
            "Ewuru Island","Schiess Island","Maroua","Ndikiniméki","Soden Island","Masonjo Island",
            "Kaélé"," Nanga Eboko","Saa","Mamfe","Ndeba","Sangmélima","Manjo","Ndélélé",
            "Bazou","Mutengene","Pon","Lucke Island","Mvangué","Rey Bouba","Makary",
            "Fiari Island","Limbé","Mouyouka","Pitoa","Lolodorf","Mundenba","Poli","Loum",
            "Katéla","Mondoleh Island","Kumbo","Mora","Penja","Lagdo","Mouanko","Piparla",
            "Bekondo","Koza","Minta","Okola","Kribi","Mokolo","Ombessa","Kumba",
            "Fontem","Kontcha","Mindif","Obala","Kousséri","Minjame","Okoa","Otérou"
    ));


    public ArrayList<String> getVilles() {
        return villes;
    }

    public void setVilles(ArrayList<String> villes) {
        this.villes = villes;
    }

    public ArrayList<String> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<String> regions) {
        this.regions = regions;
    }

    public ArrayList<String> getDivisions() {
        return divisions;
    }

    public void setDivisions(ArrayList<String> divisions) {
        this.divisions = divisions;
    }
}

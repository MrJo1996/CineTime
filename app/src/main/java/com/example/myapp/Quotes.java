package com.example.myapp;

import java.util.Random;

public class Quotes {
    int min = 0;
    int max = 40;
    String quotes[] = new String[41];
    String film[] = new String[41];
    Random r;
    int pos;

    public Quotes() {
        r = new Random();
        pos = r.nextInt(max - min + 1) + min;

        quotes[0] = "Mamma diceva sempre: la vita è uguale a una scatola di cioccolatini, non sai mai quello che ti capita! ";
        film[0] = "Forrest Gump";

        quotes[1] = "Giuro davanti a Dio, e Dio m'è testimonio, che i Nordisti non mi batteranno! Supererò questo momento e quando sarà passato non soffrirò mai più la fame, né io né la mia famiglia, dovessi mentire, truffare, rubare o uccidere. Lo giuro davanti a Dio: non soffrirò mai più la fame!";
        film[1] = "Via col vento";

        quotes[2] = "Gli farò un'offerta che non potrà rifiutare. ";
        film[2] = "Il Padrino";

        quotes[3] = "Ehi, Luke! Che la Forza sia con te. ";
        film[3] = "Ehi, Luke! Che la Forza sia con te. ";

        quotes[4] = "Ma dici a me? ";
        film[4] = "Taxi Driver";

        quotes[5] = "Mi piace l'odore del napalm al mattino.";
        film[5] = "Apocalypse Now";

        quotes[6] = "La materia di cui sono fatti i sogni.";
        film[6] = "Il mistero del falco";

        quotes[7] = "E.T. Telefono... casa... ";
        film[7] = "E.T. l'extra-terrestre";

        quotes[8] = "Ce l'ho fatta, Ma'. Sono in cima al mondo!";
        film[8] = "La furia umana";

        quotes[9] = "Sono incazzato nero e tutto questo non lo accetterò più! ";
        film[9] = "Quinto potere";

        quotes[10] = "Uno che faceva un censimento una volta tentò di interrogarmi. Mi mangiai il suo fegato con un bel piatto di fave e un buon Chianti.";
        film[10] = "Il silenzio degli innocenti";

        quotes[11] = "Coprimi di soldi! ";
        film[11] = "Jerry Maguire";

        quotes[12] = "Perché non vieni su qualche volta e mi guardi? ";
        film[12] = "Lady Lou - La donna fatale";

        quotes[13] = "Quello che ha preso la signorina. \n";
        film[13] = "Harry ti presento Sally";

        quotes[14] = "Serve una barca più grande. ";
        film[14] = "Lo Squalo";

        quotes[15] = "Vedo la gente morta! ";
        film[15] = " Il sesto senso";

        quotes[16] = "Guardate, si sta muovendo! Si sta muovendo! Guardate! È... è vivo! È vivo! È vivo! È vivo! È vivo! È vivo! È vivo! È vivo! È vivo!";
        film[16] = "Frankenstein";

        quotes[17] = "È soltanto nelle misteriose equazioni dell’amore che si può trovare ogni ragione logica. Io sono qui grazie a te. Tu sei la ragione per cui io esisto. Tu sei tutte le mie ragioni.";
        film[17] = " A Beautiful Mind";

        quotes[18] = "“Amore” significa non dover mai dire “mi dispiace”.";
        film[18] = " Love Story";

        quotes[19] = "Alcune volte perdere il tuo equilibrio per amore è necessario per vivere una vita equilibrata.";
        film[19] = "Mangia, prega, ama";

        quotes[20] = "La gente spesso definisce impossibili cose che semplicemente non ha mai visto.";
        film[20] = "Al di là della vita";

        quotes[21] = "Sono salito sulla cattedra per ricordare a me stesso che dobbiamo sempre guardare le cose da angolazioni diverse. E il mondo appare diverso da quassù. Non vi ho convinti? Venite a vedere voi stessi. Coraggio! È proprio quando credete di sapere qualcosa che dovete guardarla da un’altra prospettiva.";
        film[21] = "L’attimo fuggente";

        quotes[22] = "Ogni minuto che passa è un’occasione per rivoluzionare tutto completamente.";
        film[22] = "Vanilla Sky";

        quotes[23] = "Non permettere mai a nessuno di dirti che non sai fare qualcosa. Se hai un sogno tu lo devi proteggere. Quando le persone non sanno fare qualcosa lo dicono a te che non la sai fare. Se vuoi qualcosa, vai e inseguila. Punto.";
        film[23] = "La ricerca della felicità";

        quotes[24] = "Quando non sei in grado di combattere abbraccia il tuo nemico. Se ha le braccia intorno a te non può puntarti contro il fucile.";
        film[24] = "Sette anni in Tibet";

        quotes[25] = "Sono le scelte che facciamo… che dimostrano quel che siamo veramente, molto più delle nostre capacità.";
        film[25] = "Harry Potter e la camera dei segreti";

        quotes[26] = "Prima o poi capirai, come ho fatto anch’io, che una cosa è conoscere il sentiero giusto, un’altra è imboccarlo.";
        film[26] = "The Matrix";

        quotes[27] = "Ogni persona è un abisso, vengono le vertigini a guardarci dentro.";
        film[27] = " La tigre e la neve";

        quotes[28] = "Prima di cambiare il mondo, devi capire che ne fai parte anche tu: non puoi restare ai margini e guardare dentro.";
        film[28] = "The Dreamers";

        quotes[29] = "Non può piovere per sempre!";
        film[29] = "Il corvo";

        quotes[30] = "L’amore più bello è quello che risveglia l’anima e che ci fa desiderare di arrivare più in alto, è quello che incendia il nostro cuore e che porta la pace nella nostra mente.";
        film[30] = "Le pagine della nostra vita";

        quotes[31] = "Se vuoi qualcosa nella vita…datti da fare e prendila!";
        film[31] = "Into the wild";

        quotes[32] = "Esattamente questo quello che voglio, nessuna pietà… da dove viene, che cosa ha fatto… io me ne frego.";
        film[32] = "Quasi amici";

        quotes[33] = "Il cuore di una donna è un profondo oceano di segreti.";
        film[33] = "Titanic";


        quotes[34] = "Chi salva una vita , salva il mondo intero.";
        film[34] = "Schindler’s List";

        quotes[35] = "L’importante non è quello che trovi alla fine di una corsa ma quello che provi mentre stai correndo.";
        film[35] = "Notte prima degli esami";

        quotes[36] = "Non ho mai pensato a come sarei morta, ma morire al posto di qualcuno che amo credo sia il miglior modo per andarsene…";
        film[36] = "Twilight";

        quotes[37] = "Nella vita le cose non cambiano mai, siamo noi a cambiare.";
        film[37] = "Un altro mondo";

        quotes[38] = "La nostra vita è fatta di occasioni, anche di quelle che abbiamo perso.";
        film[38] = "Il curioso caso di Benjamin Button";

        quotes[39] = "Svegliati. Ti ho osservato a lungo. Sono qui, vieni. Più vicino. 28 giorni, 6 ore, 42 minuti, 12 secondi. Ecco quando il mondo finirà.";
        film[39] = "Donnie Darko";

        quotes[40] = "Mi chiamo Jordan Belfort. L'anno in cui ho compiuto 26 anni ho guadagnato 49 milioni di dollari, il che mi ha fatto molto incazzare perché con altri 3 arrivavo a un milione a settimana.";
        film[40] = "The Wolf of Wall Street";
    }

    public String getQuote() {
        return this.quotes[pos].trim();
    }

    public String getFilmName() {
        return this.film[pos].trim();
    }

}

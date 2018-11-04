# CryptoChat
Una chat un po' sicura

# Stefano Carpi

## Obbiettivo:
Il mio obbiettivo era quello di dimostrare la facilità con la quale si possono carpire dati da connessioni non protette e/o non cifrate. Questo progetto infatti mostra come un semplice software di chat ha la necessità di implementare un sistema di sicurezza per proteggere i messaggi scambiati tra due o più utenti o anche solo tra server e utente.

## Svolgimento:
Ho realizzato due semplici software di scambio di testo che sfruttano i thread per poter restare in ascolto in modo continuo e poter rispondere alle eventuali richieste; di cui uno munito di un sistema di crittografia a chiave simmetrica che rende più sicura la comunicazione tra i due interlocutori e/o tra gli interlocutori e il server.

## Funzionamento:
Ognuno dei due software è composto a sua volta da due applicativi: uno che gestisce le connessioni dei client (detto server) e uno che appunto figura come client (cioè l’utente che scrive). I due applicativi sono entrambi indispensabili e, devono condividere alcune configurazioni quali: chiave di crittazione, porta su cui scambiare i dati e l’indirizzo IP del server. Dopo aver avviato le due istanze, il software Server avrà già abbinato un codice all’istanza Client e ne genererà uno casuale per ogni utente che si connetterà ad esso. Con questo codice verranno identificati gli utenti nella chat. Ogni volta che un utente invierà un messaggio, a tale messaggio verrà aggiunto il suo “id univoco” e poi crittografato con una chiave a 128 bit AES utilizzando un vettore inizializzato con dei valori casuali per verificare la correttezza delle due chiavi mediante un algoritmo. Se tutto va a buon fine il messaggio viene inviato al server che, dopo averlo ricevuto lo decripterà e lo mostrerà a schermo sotto forma di “log”. Ciò che ogni utente ha scritto nella chat resta trascritto anche sul server con anche il nome in codice (numero casuale generato durante la creazione della connessione TCP cioè il suo “id univoco”) di chi lo ha scritto. Una volta fatto ciò recapita il pacchetto cifrato a tutti gli altri client che a loro volta decifreranno perché in possesso della chiave. Per la digitazione dei messaggi in chat, si ha a disposizione una GUI molto minimale che per lo scopo del progetto non è stata sviluppata ulteriormente.

## Strumenti a architettura:
Per la realizzazione ho utilizzato il linguaggio Java, i Thread nel medesimo linguaggio, un algoritmo per la verifica delle chiavi, un algoritmo per la cifratura, i socket per l’invio di informazioni grazie al protocollo TCP/IP e una porta, la 5555.

## Problemi riscontrati e fonti:
Durante la realizzazione del progetto ho avuto alcuni problemi con la creazione della chiave che deve essere di 128 bit (calcolabile al seguente link se si vuole effettuare una personalizzazione del codice: http://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx) in coppia con una chiave per l’inizializzazione del vettore che viene utilizzato per la verifica della simmetria delle chiavi che invece deve essere di 16 byte (calcolabile qui, sempre per una personalizzazione del codice, https://mothereff.in/byte-counter). Questi due valori determinano la sicurezza dei pacchetti inviati e andranno modificati in corrispondenza dei commenti nel codice Java. Ho riscontrato inoltre problemi significativi per quello che riguarda la codifica in Base64 per questo ringrazio questo Thread di Stack Overflow: https://stackoverflow.com/questions/14413169/which-java-library-provides-base64-encoding-decoding mentre per la corretta configurazione delle chiavi mi sono affidato sempre a un Thread di Stack Overflow al seguente link: https://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example un’altra guida molto utile (per me) è stata questa: https://www.tech-recipes.com/rx/30018/how-to-compile-java-programs-through-windows-cmd/ che riporta come eseguire i file .java da cmd su Windows, e come ultima ma non per importanza inserisco la guida da cui ho preso il codice dell’applicazione di chat vera e propria: http://www.ejbtutorial.com/distributed-systems/using-sockets-to-create-a-group-chat-system-with-a-graphical-interface che ricordo includere anche una GUI molto minimale.

## Tempo impiegato:
Per la realizzazione complessiva del progetto ho impiegato all’incirca 5 ore di lavoro non intenso.

## Piccola demo:
YouTube: GitHub: 

## Bug:
Se un utente chiude la GUI la connessione TCP non viene terminata e viene generata un’eccezione che però, venendo gestita, non termina l’esecuzione. Non si può quindi terminare l’appiattivo in altro modo se non forzando l’interruzione chiudendo il terminale.

## Cosa si potrebbe migliorare:
La GUI penso sia uno dei principali obbiettivi di miglioramento del progetto e perchè no, anche la crittografia!

## Cosa ho appreso:
In queste 5 ore ho inevitabilmente acquisito una ulteriore capacità nell’uso di Java e delle sue componenti quali funzioni, classi, oggetti e metodi. Ho inoltre imparato come proteggere i dati inviati tramite connessioni TCP.

## Come si usa:
Eccoci al più bello, per poter utilizzare l’applicativo è necessario aver a disposizione un computer (NON è rilevante l’edizione o il tipo di sistema operativo) con installati gli SDK di Java (qui il link: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) dopo di che, seguendo qualche comoda guida come quella segnalata sopra, sfruttare il prompt dei comandi (o terminale) per compilare i file .java trasformandoli magicamente in .class, ora hai quasi terminato! Esegui PRIMA il file Server.class sulla macchina che hai deciso effettuerà questo compito e POI il file startclient.class, si esatto, il file mychat non verrà avviato direttamente da noi ma “lanciato” da startclient ecco perché quando andrai a compilarlo dovrai compilare con “javac” SOLO questo file. Dopo di che, il gioco è fatto, inserisci quanti client vuoi e buon divertimento!

## Prima di Compilare!
Ricordo che prima di compilare i vari file .java è consigliabile scegliere una propria coppia di chiavi e di variare opportunamente la coppia ServerIP-Porta nel file mychat.java.

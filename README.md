# Jingle Bells für Gitarre

Kleine Übungs-App im Browser: Noten und TAB laufen an einem Spielstrich vorbei, ein Griffbrett zeigt die Fingerposition.

**Zur App:** https://sagebiej.github.io/jingle-bells-gitarre/

- Melodie in C-Dur (Saiten 1 bis 3) oder G-Dur (Saiten 1 bis 4), erste Lage
- Akkorde wie bei Simply Guitar: farbige Akkordspur mit Anschlagpfeilen (1×, 2×, 4× pro Takt oder ab, ab-auf, ab, ab-auf), Griffbild, Akkordklang und Akkorderkennung per Mikro
- Akkorde in C-Dur: C, Dm, Em, G, D; in G-Dur: G, C, D, Em
- Tempo 40 bis 140, Einzähler, Metronom, Schlagzeugbegleitung
- Strophe, Refrain oder alles, auf Wunsch in Schleife
- Übungsmodus mit Mikrofon: Die App wartet, bis der richtige Ton gespielt wird

Alles steckt in einer einzigen Datei, `index.html`, ohne Build-Schritt. Lokal öffnen genügt; das Mikrofon funktioniert über `https://` oder `file://`.

Melodie: James Lord Pierpont (1857), gemeinfrei.

## Noten lernen

`noten.html` ist ein Übungsquiz zu den 9 Tönen der Melodie (wahlweise 13 mit tiefem C, D, E, F auf A- und D-Saite): Notenname, Note im Notensystem und Tab. Leicht: 2 Angaben stehen da, die dritte wird ergänzt. Schwer: nur eine steht da. Falsche Aufgaben kommen am Ende nochmal. Link oben in der Mitspiel-Seite.

## Tonleiter

`tonleiter.html` übt die Reihenfolge der Notennamen: Aufsagen (C aufwärts, abwärts, ab jedem Ton, auch rückwärts), Lücken füllen und „Was kommt vor/nach …?“. Richtige Töne erklingen und erscheinen im Notensystem; die Halbtonschritte E–F und H–C sind markiert.

## Abschreiben

`abschreiben.html` zeigt die Melodie Takt für Takt als Vorlage (Noten, Tab, Text). Darunter steht ein leeres Notensystem mit Tab. Man tippt die Noten ins System, im Tab die Saite und darunter die Bundzahl, und prüft den Takt. Bei „Schwer“ wählt man auch die Notenwerte. Der Fortschritt bleibt im Browser gespeichert.

## Fire TV

`firetv/` baut eine kleine Android-App für den Fire TV (Cube), die `index.html` mitbringt und im Fernbedienungsmodus (`index.html#tv`) öffnet: Pfeiltasten wählen, OK drückt, Play/Pause startet und stoppt. Bauen mit den Ubuntu-Paketen des Android SDK:

```bash
firetv/build.sh            # baut firetv/jinglebells.apk
CUBE=192.168.1.20:5555 firetv/build.sh install
```

Der Signierschlüssel liegt außerhalb des Repos (`~/.local/share/jinglebells-tv/`).

## Android-Handy

`handy/` baut eine App für Android-Handys ohne Browser, etwa mit Kinderkonto. Sie lädt die Seite von GitHub Pages, ist also immer aktuell, und nimmt ohne Internet die mitgebrachte `index.html`. Das Mikrofon wird beim ersten Üben abgefragt.

```bash
handy/build.sh             # baut handy/jinglebells-handy.apk
handy/build.sh install     # installiert per USB (adb, USB-Debugging an)
```

# Jingle Bells für Gitarre

Kleine Übungs-App im Browser: Noten und TAB laufen an einem Spielstrich vorbei, ein Griffbrett zeigt die Fingerposition.

**Zur App:** https://sagebiej.github.io/jingle-bells-gitarre/

- Melodie in C-Dur (Saiten 1 bis 3) oder G-Dur (Saiten 1 bis 4), erste Lage
- Akkorde C, Dm, Em, G und D mit Griffbildern, Übungsmodus „Akkorde“ und Akkordklang
- Tempo 40 bis 140, Einzähler, Metronom, Schlagzeugbegleitung
- Strophe, Refrain oder alles, auf Wunsch in Schleife
- Übungsmodus mit Mikrofon: Die App wartet, bis der richtige Ton gespielt wird

Alles steckt in einer einzigen Datei, `index.html`, ohne Build-Schritt. Lokal öffnen genügt; das Mikrofon funktioniert über `https://` oder `file://`.

Melodie: James Lord Pierpont (1857), gemeinfrei.

## Fire TV

`firetv/` baut eine kleine Android-App für den Fire TV (Cube), die `index.html` mitbringt und im Fernbedienungsmodus (`index.html#tv`) öffnet: Pfeiltasten wählen, OK drückt, Play/Pause startet und stoppt. Bauen mit den Ubuntu-Paketen des Android SDK:

```bash
firetv/build.sh            # baut firetv/jinglebells.apk
CUBE=192.168.1.20:5555 firetv/build.sh install
```

Der Signierschlüssel liegt außerhalb des Repos (`~/.local/share/jinglebells-tv/`).

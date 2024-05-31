# ČLOVĚČE NEZLOB SE
## Popis hry
Hra Člověče, nezlob se je desková hra pro 2 až 4 hráče. 
Hraje se na hrací desce s 56 políčky, která je rozdělena do 4 barevných sektorů. 
Hráči postupují podle hodů kostkou a snaží se dostat všemi svými figurkami do cíle.
Hra je vhodná pro děti od 4 let.
Odkaz na hru na GITHUB: https://github.com/sberan1/ludo


## Spuštění hry
1. Spustte server prikazem java -jar out/artifacts/server_jar/server.jar (server.jar je v tomto repozitari v adresari out/artifacts/server_jar)
2. Spusste klienta prikazem java -jar out/artifacts/client_jar/client.jar (client.jar je v tomto repozitari v adresari out/artifacts/client_jar) (Momentalne nefunguje, nelze spustit JAR)
3. Zadejte jmeno pro login a potvrdte tlacitkem OK
4. Pokud chcete vytvorit lobby, zadejte jeji nazev vpravo dole a kliknete na tlacitko Vytvorit lobby
5. Zvolte si barvu pres RadioButton a kliknete na Zvolit barvu
6. Vyckejte na pripojeni druheho klienta a spustte hru
7. Druhy klient si musi fyzicky zvolit lobby z listu a pripojit se do ni. Barva je take povinna.
8. Hru muze spustit kdokoliv v lobby.
9. Po nastartovani se v chatu hry ukaze, kdo je prvni na tahu. Kazdy hrac se strida po jednom hozeni kostkou a moznem posunu figurky.
10. Pokud hrac hodi 6 a ma figurku na startujici pozici, muze figurku nasadit. Pokud chce posunout figurkou v poli, muze tak ucinit pouze jednou, protoze kazdy hrac za svuj tah hazi pouze jednou.
11. Vitezi ten hrac, ktery dostane vsechny figurky do domecku.
# Workshop feladat:

Írj egy programot, ami a `dvdrental` adatbázisból ország, illetve név alapján lehet lekérdezni a vásárlók adatait.
A vásárlók adatai az alábbi oszlopokból szárazik (lásd: `customer_list VIEW`):

```id
name
address
zip code
phone
city
country
notes
sid
```

Az országot vagy nevet a program paraméterekből veszi, az adatbázis kapcsolat adatait olvassátok be Properties fájlból.

Szintén meg program paraméternek meg lehet adni opcionálisan egy kimeneti fájl nevet.
Ha ez meg van adva, ebbe a fájlba írja ki az eredményt CSV formátumban, ha nincs, akkor írja ki a standard outputra (consol-ra).

Az argumentumok feldolgozásához használhattok könyvtárat, pl.: https://argparse4j.github.io/usage.html#examples

Példa hívások valahogy így nézhetnek ki (itt a paraméterek a lényeg, elég most, ha az IDE-ben megadjátok az argumentumokat):
```
java -jar App.jar --help
java -jar App.jar --country Japan
java -jar App.jar --name Anna
java -jar App.jar --name 'A%'
java -jar App.jar --name 'A%' --country Japan --out export.csv
```
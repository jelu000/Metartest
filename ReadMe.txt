.:Så funkar MetarData:.

Flygplatserna har väderobservationer som mäts varje halvtimme i Sverige. Dessa observationer lagras i något som heter metardata och som används av flyget. Varje flygplats har en egen kod som kallas ICAO kod. Denna kod kan användas för att söka vilket väder de är på en viss flygplats. Detta program hämtar väderdata från: https://tgftp.nws.noaa.gov/data/observations/metar/stations/ som metardata i vanligt textformat för att sedan tolka datan och plocka ut temperatur, vind och vindrikting vid en viss flygplats.

För att pröva programet kör du bara kommandot:
java -jar MetarTest.jar
i roten där MetarTest.jar filen ligger. Då ska vädret vid Östersunds flygplats visas.

Om man istället vill visa vädret för en annan flygplats exempel Malmös flygplats väljer man denna flygplats ICAO kod som är och lägger till de i slutet på kommandot

Då skriver dus så här:
java -jar MetarTest.jar ESMS

Om du vill använda andra ICAO koder så finns de några här:
https://sv.wikipedia.org/wiki/Lista_%C3%B6ver_flygplatser_i_Sverige


Givetvis kan du använda classen MetarData.java och dess metoder för att bygga in i ett eget Javaprogram om du vill och de står dig fritt. 









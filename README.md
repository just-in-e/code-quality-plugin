### code-quality-plugin

## JAVA rules:
- var usage for local variables

## GROOVY rules:
- def usage for local variables

## Possible rules

1. kai naudojami keli advisor, gerai būtų sudėlioti @Order anotacijas, nes yra buvę, kad bendrinis advisor pirmas suveikdavo prieš v2 (https://gitlab.ignitis.lt/eims/microservices/nordpool-integration-service/-/merge_requests/25)
2. date pattern į Constants (https://gitlab.ignitis.lt/eims/microservices/nordpool-integration-service/-/merge_requests/25)
3. Use Objects.isNull(throwable) (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169641)
4. @EnableEimsObjectMapper (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169646)
5. Enforcinau '@Builder' naudojimą klasei. (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169650)
6. Prie kintamuju truksta private (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169651)
7. Vienur palieki viena tarpa kitur du, reiktu palikinet viena (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169653)
8. O tikrai reikai papildomai  setinti X_B3_TRACE_ID i MDC? (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169657)
9. Exception reiktu isloginti, bus paprasciau ieskant problemu. (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169658)
10. Testuose esame sutare vietoj konkreciu klasiu pavadinimu kur galima naudoti def (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169660)
11. Gal galima po >> panaudoti new ClassName().tap {... vietoj tavo parasymo, taip turetu gautis maziau kodo (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169665)
12. gal vietoj "200" bustu galima naudoti HttpStatus.BAD_REQUEST.value().toString (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169666)
13. Ar reikia palikti static apenderyje nenaudojamus metodus? (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169669)
14. kabletaskis nereikalingas (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169671)
15. public static final String sutavrkyk pagal groovy reikalavimus. Tieisog laikytis tu paciu taisykliu kaip jau anksciau kazkieno buvo pradeta, visur nurodoma def, o pas tave String. (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169672)
16. Palikti nenaudojami import (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169673)
17. Yra sutarta naudoti naujausia starter versija, tavoji set('starterVersion', "2024.06.21-273461") (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169679)
18. Kodel panaikinai @Data? (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169689)
19. Private konstruktoriu pakeisk anotacija @NoArgsConstructor(access = AccessLevel.PRIVATE) (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169916)
20. Praziurek dar sonar warning situose testuose (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169917)
21. Yra pasikartojancio teksto, pasikartojantis tekstas turi buti kaip konstanta (https://gitlab.ignitis.lt/eims/microservices/dt-energy-integration-service/-/merge_requests/58#note_169926)
22. Edit var rule - for not initialised variables. Ex. String request = null;

## MR checks:
### Check in web

- visual check for slashes, commas or other studd missing
- code formatted
- imports with asterisks
- local types: var, def
- private final for autowired instance fields
- access modifiers for constant: set private or protected if needed
- check log - are those necessary?
- Lib for pulsar test is used?

### Check in properites

- no primitive types egsists
- validatation NotEmpty for strings, lists, NotNull for  other types set.
- Swwagger @Schema is futureproof for OpenApi 3
- no new properties on enabler branch.
- class renames in enabler branch + DEVELOPMENT.

### Check in local env

- unused imports
- Run tests locally
- Run coverage on new files
- check codestyle, sonarlink warnings

### Ansible

- only secret info should be placed here

### Check in code

- starterio versija, kitu naudojamu lib'u versija
- update README
- lint java code
- format code

### Additional
- NotImplemented annotation. Should be always true in controller.
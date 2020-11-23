# Application - PGR301

Del av eksamen i PGR301 - DevOps i skyen og demonstrere bruk av Terraform, Docker og Travis-CI

## Fremgangsmåte

1. Opprett en service account for container registry og gi den rollen: "Google storage admin"
2. Last ned nøkkel for service brukeren på json format og legg den i root mappen til prosjektet med navnet "gkey.json". Krypter filen med `travis encrypt-file --pro gkey.json --add`
3. Forventer følgende miljøvariabler for lokal kjøring (disse blir satt av infrastrukturprosjektet ved kjøring på cloud platform):
    - LOGZ_TOKEN (logz-io token)
    - DB_USERNAME (brukernavn for h2 db)
    - DB_PASSWORD (passord for h2 db)
    - auth0_issuer (auth0 domene)
    - auth0_audience (auth0 api identifier)

## Auth0

Bruker Auth0 som settes opp i infrastruktur prosjektet:


Apiet krever autentisering ved hjelp av Auth0. For å teste applikasjonen kan man få jwt token ved å logge inn på Auth0 gå inn på "APIs" > "Cloud run API" (laget ved hjelp av terraform) > "Test"-fanen. Tokenen må legges ved i "Authorization" headeren og prefixes med "Bearer".

Tanken er at man feks skal ha en separat single-page-app som lar brukeren logge inn og hente tokenen fra auth0, for så å bruke den til å autorisere kallene mot APIet. Opprettes også client for Auth0 SPA-client i terraform-prosjektet.
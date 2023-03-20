# GTR Genomic Study Applicaiton
Providing NCBI Genetic Testing Registry as FHIR resources, mainly the FHIR R5 Genomic Study resource and some supporting resources

## Running application locally

- Install Postgres and pgAdmin
- Create database name ncbi-gtr
- Take note of port number, user name, and password
- Change them on the application.properties file
- Download data and unzip the following files:
		- https://ftp.ncbi.nlm.nih.gov/pub/GTR/data/test_version.gz
		- https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/disease_names.txt
- Copy file paths to "src/main/resources/schema.sql" and replace current values, i.e., "C:\gtr_data\test_version" and "C:\gtr_data\disease_names.txt" with your downloaded and unzipped file paths

- Run the application from the IDE.

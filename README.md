# GTR Genomic Study Applicaiton
Providing NCBI Genetic Testing Registry as FHIR resources, mainly the FHIR R5 Genomic Study resource.

## Running application locally

- Install Postgres and pgAdmin
- Create database and name it ncbi-gtr
- Take note of port number, user name, and password
- Change them on the application.properties file
- Download data and unzip the following files:
		- https://ftp.ncbi.nlm.nih.gov/pub/GTR/data/test_version.gz
		- https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/disease_names.txt
- Copy file paths to "src/main/resources/schema.sql" and replace current values, i.e., "C:\gtr_data\test_version" and "C:\gtr_data\disease_names.txt" with your downloaded and unzipped file paths

- Run the application from the IDE.
- Check the following ling for the Swagger page: http://localhost:8080/swagger-ui/index.html
- Check the following link: http://localhost:8080/gtr_fhir/disease/C4518821
- Check the file [gtr_genomic_study.http](https://github.com/khalifa-aly/gtr_genomic_study/blob/main/gtr_genomic_study.http) for more testing http requests


### Note: the GenomicStudy.status is a code. However it is represented as a codeable concept as per the use library method signature genomicStudy.setStatus(). An issue was submitted to HAPI FHIR about this.

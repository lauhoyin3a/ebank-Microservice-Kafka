Overview:
The application consists of two parts: a Security application that generates JWT Tokens and authorizes API calls, and an Ebank application that performs monthly transaction records for specific customers. The Security application uses PostgreSQL as its database, while the Ebank application uses MongoDB. 


Security Application:

The Security application is responsible for generating JWT Tokens and authorizing API calls. It uses PostgreSQL as its database and interacts with Ebank application to produce and consume transaction messages.

Ebank Application:
The Ebank application performs monthly transaction records for specific customers and uses MongoDB as its database. It interacts with Kafka to produce and consume transaction messages , it  call an ExchangeRate API to retrieve the exchange rate everyday and output the total debit and total credit for the transactions record that month/ 


Confluent Platform for Kafka:
Confluent Platform is used to simplify the development and deploymentof Kafka streaming and provide logging and monitoring on messages. It allows for easy deployment of multi-broker and cluster configurations, making it suitable for handling large amounts of data.

Containerization:
Kompose is used to convert the Docker Compose file to a Kubernetes YAML file that can be deployed to a local Kubernetes cluster using Docker Desktop.

Testing and Automation:
The application is tested using JUnit, and the automated CI/CD process is managed by CircleCI. This includes running automated tests, building and pushing Docker images to a container registry, and deploying the application to a Kubernetes cluster.


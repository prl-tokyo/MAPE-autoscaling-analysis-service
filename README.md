# MAPE Autoscaling Analysis Service

This is a REST web service for autoscaling analysis, meant to be used as the A in a MAPE-K loop.

It takes a deployment model in JSON, analyses it, determines whether autoscaling is required, and 
by how many vCPUs. At the moment, the analysis thresholds are hard-coded, but that will change in 
the next version.

## Requirements

You will need Java 8 to run the application, and Maven for dependencies.

## Running

This is a Spring boot application, built using Maven. Starting a local version is straightforward:

	mvn spring-boot:run
	
Or, if you compiled it as a jar:

	java -jar path/to/application.jar

## Usage

To create a new deployment for analysis, send a `POST` request to `/deployment`, with a deployment 
in JSON. The deployment ID will be in the response.

A deployment is a list of virtual machines, each of which has an ID, a number of CPUs, and values 
for Load1, Load5, and Load10. The virtual machines IDs are Strings, but they need to be unique for 
a deployment.

To get the analysis result for a deployment, send a `GET` request to 
`/deployment/{deployment-id}/analyse/`. The analysis result will be returned in JSON.

## Example

We assume that the web service runs on `localhost`, and listens to port `8080`.

### No adaptation required

We create a deployment with two virtual machines:

	curl -H "Content-Type: application/json" -X POST -d '{"vms":[{"id":"3","load1":5.0,"load5":4.76,"load10":2.67,"cpus":8},{"id":"4","load1":9.0,"load5":7.76,"load10":8.67,"cpus":32}]}' http://localhost:8080/deployment -v
	
The deployment ID returned is `1`. We can check the analysis result:

	curl http://localhost:8080/deployment/1/analysis
	> {"adapt":false,"scaleUp":false,"cpuCount":0}
	
No adaptation is necessary here (`adapt` is `false`).

### Scaling up required

We create another deployment, with higher load on the virtual machines:

	curl -H "Content-Type: application/json" -X POST -d '{"vms":[{"id":"3","load1":16.0,"load5":16.76,"load10":16.67,"cpus":8},{"id":"4","load1":64.0,"load5":66.76,"load10":70.67,"cpus":32}]}' http://localhost:8080/deployment -v
	
The deployment ID returned is `2`. We can check the analysis result:

	curl http://localhost:8080/deployment/2/analysis
	> {"adapt":true,"scaleUp":true,"cpuCount":2}
	
Adaptation is required (`adapt` is true). The number of instances must be scaled up (`scaleUp` 
is true), and 2 additional vCPUs a required (`cpuCount` value).

Scaling down is similar, but `scaleUp` will be false.
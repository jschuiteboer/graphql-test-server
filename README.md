# GraphQL Test Server

A java server powered by spring data providing a GraphQL endpoint.

Also see the [compantion client project]

[compantion client project]: https://github.com/jschuiteboer/graphql-test-client

## How to Run

To start the server use  `mvn spring-boot:run`, the server will run on <http://localhost:8080>. A GraphiQL servlet will
run on <http://localhost:8080/graphiql>.

## Testing
`mvn verify` to run all tests available.  
`mvn test` if you only want to run the unit tests.  
`mvn failsafe:integration-test` if you only want to run the integration tests  

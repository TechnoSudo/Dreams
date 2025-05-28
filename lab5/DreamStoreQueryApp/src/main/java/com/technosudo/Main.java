package com.technosudo;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String GRAPHDB_SERVER = "http://localhost:7200";
    private static final String REPOSITORY_ID = "dreams";
    private static final String NAMESPACE = "http://dreamsellers.org/dreams#";

    public static void main(String[] args) {
        Repository repo = new HTTPRepository(GRAPHDB_SERVER, REPOSITORY_ID);

        try (RepositoryConnection conn = repo.getConnection()) {
            executeAndPrint(conn,
                    "SELECT ?offer ?name ?price ?rating WHERE { " +
                            "  ?offer a :DreamOffer ; " +
                            "         :name ?name ; " +
                            "         :price ?price ; " +
                            "         :rating ?rating . " +
                            "  FILTER (?price > 300 && ?rating >= 4.5) " +
                            "} ORDER BY DESC(?rating) LIMIT 5");

            executeAndPrint(conn,
                    "SELECT (COUNT(?offer) as ?count) WHERE { " +
                            "  ?offer a :DreamOffer ; " +
                            "         :price ?price . " +
                            "  FILTER (?price >= 100 && ?price <= 300) " +
                            "}");
        }
    }

    private static void executeAndPrint(RepositoryConnection conn, String queryBody) {
        String fullQuery = "PREFIX : <" + NAMESPACE + "> " + queryBody;

        System.out.println("Executing query:\n" + fullQuery + "\n");

        TupleQuery query = conn.prepareTupleQuery(fullQuery);
        try (TupleQueryResult result = query.evaluate()) {
            // Print column headers
            System.out.println(String.join(" | ", result.getBindingNames()));

            // Print results
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                System.out.println(
                        bindingSet.getValue("offer") + " | " +
                                bindingSet.getValue("name") + " | " +
                                bindingSet.getValue("price") + " | " +
                                bindingSet.getValue("rating")
                );
            }
        }
        System.out.println("\n----------------------------\n");
    }
}
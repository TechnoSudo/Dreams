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
    private static final String PREFIX = """
            PREFIX : <http://dreamsellers.org/dreams#>
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            """;

    public static void main(String[] args) {
        Repository repo = new HTTPRepository(GRAPHDB_SERVER, REPOSITORY_ID);

        try (RepositoryConnection conn = repo.getConnection()) {
            executeAndPrint(conn, """
                    SELECT ?offer ?name ?price ?rating WHERE {
                        ?offer a :DreamOffer ;
                        :name ?name ;
                        :price ?price ;
                        :rating ?rating .
                        FILTER (?price > 300 && ?rating >= 4.5)
                    }
                    ORDER BY DESC(?rating)
                    LIMIT 5
            """);
            executeAndPrint(conn, """
                    SELECT ?offer ?name ?price ?rating ?description ?vendorName
                    WHERE {
                        ?offer a :DreamOffer ;
                               :name ?name ;
                               :price ?price ;
                               :rating ?rating .
                        OPTIONAL { ?offer :description ?description . }
                        OPTIONAL {
                            ?offer :offeredBy ?vendor .
                            ?vendor :vendorName ?vendorName .
                        }
                    }
                    ORDER BY ?name
                    LIMIT 10
            """);
            executeAndPrint(conn, """
                    SELECT ?offer ?name ?price ?rating
                    WHERE {
                        ?offer a :DreamOffer ;
                        :name ?name ;
                        :price ?price ;
                        :rating ?rating .
                        FILTER (?price > 300 && ?rating >= 4.5)
                    }
                    ORDER BY DESC(?rating)
                    LIMIT 5
            """);
            executeAndPrint(conn, """
                    SELECT ?offer ?name ?price ?rating
                    WHERE { {
                            ?offer a :DreamOffer ;
                            :name ?name ;
                            :price ?price ;
                            :rating ?rating .
                        FILTER (?price >= 100 && ?price <= 500)
                       } UNION {
                            ?offer a :DreamOffer ;
                            :name ?name ;
                            :price ?price ;
                            :rating ?rating .
                       FILTER (?rating >= 4.8)
                    } }
                    ORDER BY DESC(?rating) ?price
                    LIMIT 10
            """);
            executeAndPrint2(conn, """
                    SELECT ?vendorName (AVG(?rating) AS ?averageRating) (SUM(?quantity) AS ?totalQuantity)
                    WHERE {
                        ?offer a :DreamOffer ;
                        :offeredBy ?vendor ;
                        :rating ?rating ;
                        :quantity ?quantity .
                        ?vendor :vendorName ?vendorName . }
                    GROUP BY ?vendorName
                    HAVING (SUM(?quantity) > 2)
                    ORDER BY DESC(?averageRating)
            """);
            executeAndPrint(conn, """
                    SELECT ?offer ?name ?price ?currency ?rating ?vendorName
                    WHERE {
                            ?offer a :DreamOffer ;
                            :name ?name ;
                            :price ?price ;
                            :currency ?currency ;
                            :rating ?rating ;
                            :offeredBy ?vendor .
                            ?vendor :vendorName ?vendorName .
                        FILTER (?currency = "USD" && ?rating >= 4.0 && ?vendorName != "Magic Dreams Inc.") }
                    ORDER BY DESC(?rating) ?name
                    LIMIT 10
            """);
        }
    }

    private static void executeAndPrint(RepositoryConnection conn, String queryBody) {
        String fullQuery = PREFIX + queryBody;

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

    private static void executeAndPrint2(RepositoryConnection conn, String queryBody) {
        String fullQuery = PREFIX + queryBody;

        System.out.println("Executing query:\n" + fullQuery + "\n");

        TupleQuery query = conn.prepareTupleQuery(fullQuery);
        try (TupleQueryResult result = query.evaluate()) {
            // Print column headers
            System.out.println(String.join(" | ", result.getBindingNames()));

            // Print results
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                System.out.println(
                        bindingSet.getValue("vendorName") + " | " +
                                bindingSet.getValue("averageRating") + " | " +
                                bindingSet.getValue("totalQuantity")
                );
            }
        }
        System.out.println("\n----------------------------\n");
    }
}
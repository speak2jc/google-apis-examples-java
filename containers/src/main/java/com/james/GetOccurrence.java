package com.james;

import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.container.v1.ClusterManagerClient;
import com.google.cloud.devtools.containeranalysis.v1.ContainerAnalysisClient;
//import com.google.cloud.devtools.
import com.google.container.v1.Cluster;
import com.google.container.v1.ListClustersResponse;
import io.grafeas.v1.GrafeasClient;
import io.grafeas.v1.Occurrence;
import io.grafeas.v1.OccurrenceName;
import java.io.IOException;
import java.lang.InterruptedException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetOccurrence {
    // Retrieves and prints a specified Occurrence from the server
    public static Occurrence getOccurrence(String occurrenceId, String projectId)
            throws IOException, InterruptedException {
        // String occurrenceId = "123-456-789";
        // String projectId = "my-project-id";
        final OccurrenceName occurrenceName = OccurrenceName.of(projectId, occurrenceId);

        // Initialize client that will be used to send requests. After completing all of your requests,
        // call the "close" method on the client to safely clean up any remaining background resources.
        GrafeasClient client = ContainerAnalysisClient.create().getGrafeasClient();
        Occurrence occ = client.getOccurrence(occurrenceName);
        System.out.println(occ);
        return occ;
    }

    public static Occurrence getClusters(String projectId)
            throws IOException, InterruptedException {

        //final OccurrenceName occurrenceName = OccurrenceName.of(projectId, occurrenceId);

        // Initialize client that will be used to send requests. After completing all of your requests,
        // call the "close" method on the client to safely clean up any remaining background resources.
        ClusterManagerClient client = ClusterManagerClient.create();
        ListClustersResponse clusters = client.listClusters("projects/explorer-273804/location/-");

        if (clusters.getClustersCount() > 0) {
            List<Cluster> clustersList = clusters.getClustersList();
            Map m = clustersList.stream().collect(Collectors.toMap(Cluster::getName, Cluster::getEndpoint));


            Cluster c = clusters.getClusters(0);
            String ep = c.getEndpoint();
        }

        Cluster c1 = null;
        String clusterName = "cluster-1";
        try {
            client.getCluster("explorer-273804", "us-central1-c", clusterName);
        } catch (NotFoundException nfe) {
            System.out.println("Cluster not found " + clusterName);
        }
        return null;
    }

    public static void main(String[] args) {
        GetOccurrence getOccurrence = new GetOccurrence();

        try {
            //GetOccurrence.getOccurrence("abc", "explorer-273804");

            GetOccurrence.getClusters("explorer-273804");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
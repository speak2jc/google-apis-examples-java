package com.james;

import com.google.cloud.container.v1.ClusterManagerClient;
import com.google.container.v1.Cluster;
import com.google.container.v1.ListClustersResponse;
import io.grafeas.v1.Occurrence;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Clusters {

    public static Occurrence getClusters(String projectId)
            throws IOException {

        ClusterManagerClient client = ClusterManagerClient.create();
        ListClustersResponse clusters = client.listClusters("projects/" + projectId + "/location/-");

        Map<String, String> clusterNameToIp = new HashMap<>();
        if (clusters.getClustersCount() > 0) {
            List<Cluster> clustersList = clusters.getClustersList();
            clusterNameToIp = clustersList.stream().collect(Collectors.toMap(Cluster::getName, Cluster::getEndpoint));
//            Cluster c = clusters.getClusters(0);
//            String ep = c.getEndpoint();
        }

        clusterNameToIp.entrySet().forEach(e -> System.out.println("Name: " + e.getKey() + ", IP: " + e.getValue()));

        // Not so useful to look for a specific cluster as need to know its zone

//        Cluster c1 = null;
//        String clusterName = "cluster-1";
//        try {
//            client.getCluster("explorer-273804", "us-central1-c", clusterName);
//        } catch (NotFoundException nfe) {
//            System.out.println("Cluster not found " + clusterName);
//        }

        client.close();
        return null;
    }

    public static void main(String[] args) {

        // Set this env var before running
        // GOOGLE_APPLICATION_CREDENTIALS=/Users/jamez/code/go/src/github.com/speak2jc/container-analysis/explorer-273804-b151dd684c86.json

        Clusters clusters = new Clusters();

        try {
            //Clusters.clusters("abc", "explorer-273804");

            Clusters.getClusters("explorer-273804");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
package com.james;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.container.Container;
import com.google.api.services.container.model.Cluster;
import com.google.api.services.container.model.ListClustersResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContainerExample {
    public static void main(String args[]) throws IOException, GeneralSecurityException {
        // Deprecated. The Google Developers Console [project ID or project
        // number](https://support.google.com/cloud/answer/6158840).
        // This field has been deprecated and replaced by the parent field.
        String projectId = "explorer-273804"; // TODO: Update placeholder value.
        String clusterID = "cluster-abc";

        // Deprecated. The name of the Google Compute Engine
        // [zone](/compute/docs/zones#available) in which the cluster
        // resides, or "-" for all zones.
        // This field has been deprecated and replaced by the parent field.
        String zone = "us-central1-c";

        Container containerService = createContainerService();
        Container.Projects.Zones.Clusters.List request =
                containerService.projects().zones().clusters().list(projectId, "-");

        ListClustersResponse response = request.execute();

        // TODO: Change code below to process the `response` object:

        List<Cluster> clusters = response.getClusters();

        Map<String, String> map = clusters.stream().collect(Collectors.toMap(Cluster::getName, Cluster::getEndpoint));
        System.out.println(map.get(clusterID));
    }

    public static Container createContainerService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleCredential credential = GoogleCredential.getApplicationDefault();
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }

        return new Container.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Google-ContainerSample/0.1")
                .build();
    }


}

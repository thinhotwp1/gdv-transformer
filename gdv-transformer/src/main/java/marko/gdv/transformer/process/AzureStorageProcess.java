package marko.gdv.transformer.process;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobItem;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Component
@Log4j2
public class AzureStorageProcess {

    @Value("${azure.storage.connection.string}")
    private String connectString;
    @Value("${azure.storage.container.name}")
    private String containerName;
    @Value("${azure.storage.container.backup.name}")
    private String backupContainerName;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Scheduled(fixedRate = 10000) // Schedule to run every 10 seconds
    public void scheduleBlobProcessing() {
        log.info("Starting scheduled Azure Storage Process");
        processBlobs();
    }

    public void processBlobs() {

        try {
            producerTemplate.start();
            processFilesAzure();
            producerTemplate.stop();
        } catch (Exception e) {
            log.error("Error during blob processing", e);
        }
    }

    private void processFilesAzure() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectString).buildClient();
        BlobContainerClient sourceContainerClient = blobServiceClient.getBlobContainerClient(containerName);

        for (BlobItem blobItem : sourceContainerClient.listBlobs()) {
            String blobName = blobItem.getName();
            BlobClient blobClient = sourceContainerClient.getBlobClient(blobName);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blobClient.download(outputStream);

            String fileContent = outputStream.toString(StandardCharsets.ISO_8859_1);
            log.info("Processing Blob: " + blobName);

            // Add try/catch for file error
            String processedContent = producerTemplate.requestBody("direct:processJson", fileContent, String.class);
            // Convert to entity, import to database,...
            log.info("Processed Content: " + processedContent);

            // Backup file
            BlobContainerClient backupContainerClient = blobServiceClient.getBlobContainerClient(backupContainerName);
            BlobClient backupBlobClient = backupContainerClient.getBlobClient(blobName);
            backupBlobClient.beginCopy(blobClient.getBlobUrl(), null);
//            blobClient.delete();
        }
    }
}

package org.waikato.cloud.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.fileupload.FileItem;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;

public class UploadFile 
{
	private static Logger logger = Logger.getLogger(UploadFile.class.getName()); 
	
	public static void uploadFile( String bucketName, String keyName, List<FileItem> fileData ) throws Exception
	{
		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        try {

        	logger.log(Level.ALL, "Entered...");
            S3Object s3Object = new S3Object();

            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(fileData.get(0).getContentType());
            
            logger.log(Level.ALL, "File ContentType = " + fileData.get(0).getContentType());
            
            omd.setContentLength(fileData.get(0).getSize());
            omd.setHeader("filename", keyName);
            
            ByteArrayInputStream bis = new ByteArrayInputStream(fileData.get(0).get());

            s3Object.setObjectContent(bis);
            s3Client.putObject(new PutObjectRequest(bucketName, keyName, bis, omd));
            s3Object.close();
            
            s3Client.setObjectAcl(bucketName, keyName, CannedAccessControlList.PublicRead);

        } catch (AmazonServiceException ase) {
           System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was "
                + "rejected with an error response for some reason.");

           System.out.println("Error Message:    " + ase.getMessage());
           System.out.println("HTTP Status Code: " + ase.getStatusCode());
           System.out.println("AWS Error Code:   " + ase.getErrorCode());
           System.out.println("Error Type:       " + ase.getErrorType());
           System.out.println("Request ID:       " + ase.getRequestId());
           throw ase;
        } catch (AmazonClientException ace) {
           System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while "
                + "trying to communicate with S3, such as not being able to access the network.");
           throw ace;
        }catch (Exception e)
        {
        	 throw e;
        }
	}
	
	public static void uploadFile( String bucketName, File file, String fileType )
	{
        long contentLength = file.length();
        String keyName = file.getName();

        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());        

        // Create a list of UploadPartResponse objects. You get one of these
        // for each part upload.
        List<PartETag> partETags = new ArrayList<PartETag>();

        // Step 1: Initialize.
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(fileType);
        metadata.setContentLength(contentLength);
        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, keyName, metadata);
        InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);
        
        System.out.println("Intialized to upload..");
        
        long partSize = 5242880; // Set part size to 5 MB.

        try {
            // Step 2: Upload parts.
            long filePosition = 0;
            for (int i = 1; filePosition < contentLength; i++) {
                // Last part can be less than 5 MB. Adjust part size.
            	partSize = Math.min(partSize, (contentLength - filePosition));
            	
                // Create request to upload a part.
                UploadPartRequest uploadRequest = new UploadPartRequest()
                    .withBucketName(bucketName).withKey(keyName)
                    .withUploadId(initResponse.getUploadId()).withPartNumber(i)
                    .withFileOffset(filePosition)
                    .withFile(file)
                    .withPartSize(partSize);

                // Upload part and add response to our list.
                partETags.add(
                		s3Client.uploadPart(uploadRequest).getPartETag());

                filePosition += partSize;
                
                System.out.println("Uploading... " + filePosition);
            }

            // Step 3: Complete.
            CompleteMultipartUploadRequest compRequest = new 
                         CompleteMultipartUploadRequest(
                        		 bucketName, 
                                    keyName, 
                                    initResponse.getUploadId(), 
                                    partETags);
            s3Client.completeMultipartUpload(compRequest);
            s3Client.setObjectAcl(bucketName, keyName, CannedAccessControlList.PublicRead);
            
            System.out.println("Upload completed " + compRequest.toString());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
}
